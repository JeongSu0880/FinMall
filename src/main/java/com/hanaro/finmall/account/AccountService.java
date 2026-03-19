package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.*;
import com.hanaro.finmall.common.config.AppConfig;
import com.hanaro.finmall.common.security.UserAuthDTO;
import com.hanaro.finmall.product.Product;
import com.hanaro.finmall.product.ProductRepository;
import com.hanaro.finmall.transaction.TransactionService;
import com.hanaro.finmall.transaction.TransactionType;
import com.hanaro.finmall.transaction.dto.TransactionRequestDTO;
import com.hanaro.finmall.user.User;
import com.hanaro.finmall.user.UserRepository;
import com.hanaro.finmall.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AppConfig appProperties;
    private final TransactionService transactionService;

    public List<AccountResponseDTO> getAccounts(UserAuthDTO user) {

        return accountRepository.findByUserId(user.getId()).stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Long create(Long userId, AccountCreateRequestDTO req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        String accountNumber = generateAccountNumber(req.getAccountNumber());

        Account account = accountMapper.toEntity(req);

        account.setUser(user);
        account.setProduct(product);
        account.setAccountNumber(accountNumber);

        accountRepository.save(account);

        return account.getId();
    }

    private String generateAccountNumber(String input) {

        String number;

        if (input == null || input.isBlank()) {
            number = generateRandomNumber();
        } else {
            number = input.replaceAll("[^0-9]", "");
        }

        if (number.length() != 11) {
            throw new IllegalArgumentException("계좌번호는 11자리 숫자여야 합니다.");
        }

        if (input != null && !input.isBlank()) {
            if (accountRepository.existsByAccountNumber(number)) {
                throw new IllegalArgumentException("이미 존재하는 계좌번호입니다.");
            }
            return number;
        }

        int retry = 0;
        while (accountRepository.existsByAccountNumber(number)) {
            number = generateRandomNumber();

            if (++retry > 10) {
                throw new RuntimeException("계좌번호 생성에 실패했습니다. 다시 시도해주세요.");
            }
        }

        return number;
    }

    private String generateRandomNumber() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            sb.append((int) (Math.random() * 10));
        }

        return sb.toString();
    }

    private String formatAccountNumber(String number) {
        return number.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
    }

    public Long updateStatus(Long accountId,
                             AccountStatusUpdateDTO req,
                             Authentication authentication) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow();

        UserAuthDTO user = (UserAuthDTO) authentication.getPrincipal();

        if (!user.getRole().equals(UserRole.ADMIN) &&
                !account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한 없음");
        }

        account.changeStatus(req.getStatus());

        if (req.getStatus() == AccountStatus.TERMINATED) {
            TransactionRequestDTO dto = new TransactionRequestDTO(
                    account.getId(),
                    appProperties.getDefaultProductId(),
                    TransactionType.MATURITY,
                    account.getBalance()
            );
            transactionService.transfer(accountId, dto, user.getId());
        }
        if (req.getStatus() == AccountStatus.MATURED) {

        }

        return account.getId();
    }

    public List<AccountResponseDTO> search(Long userId, AccountSearchDTO search) {

        return accountRepository.search(userId, search).stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    public Account createDefaultAccount(User user) {

        Account account = new Account();

        account.setUser(user);
        account.setAccountNumber(generateAccountNumber(null));
        account.setStatus(AccountStatus.ACTIVE);
        account.setPrincipal(0L);

        Product product = productRepository.findById(appProperties.getDefaultProductId())
                .orElseThrow(() -> new RuntimeException("기본 상품 없음"));
        account.setProduct(product);

        account.setStartedAt(LocalDateTime.now());

        return accountRepository.save(account);
    }

    @Transactional
    public Long deposit(Long accountId,
                        AccountDepositRequestDTO req,
                        UserAuthDTO user) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("계좌 없음"));

        if (!user.getRole().equals(UserRole.ADMIN) &&
                !account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한 없음");
        }

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("활성 계좌만 납입 가능");
        }

        if (req.getAmount() <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 함");
        }

        account.deposit(req.getAmount());

        account.increaseInstallmentCount();

        account.updateLastPaidAt();

        transactionService.recordDeposit(account, req.getAmount());

        return account.getId();
    }
}