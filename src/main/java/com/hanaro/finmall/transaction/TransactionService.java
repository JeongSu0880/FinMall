package com.hanaro.finmall.transaction;

import com.hanaro.finmall.account.Account;
import com.hanaro.finmall.account.AccountRepository;
import com.hanaro.finmall.transaction.dto.TransactionCreateDTO;
import com.hanaro.finmall.transaction.dto.TransactionRequestDTO;
import com.hanaro.finmall.transaction.dto.TransactionResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    public void recordDeposit(Account account, Integer amount) {
        TransactionCreateDTO dto = new TransactionCreateDTO();
        dto.setTransactionType(TransactionType.DEPOSIT);
        dto.setAmount(amount);
        dto.setBalanceAfter(account.getPrincipal());
        dto.setStatus(TransactionStatus.COMPLETED);
        dto.setDescription("계좌 납입");

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setOccuredAt();
        transaction.setAccount(account);

        transactionRepository.save(transaction);
    }

    @Transactional
    public TransactionResponseDTO transfer(Long accountId, TransactionRequestDTO request, Long userId) {

        Account fromAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("출금 계좌 없음"));

        if (!fromAccount.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인 계좌만 거래 가능");
        }

        if (!accountId.equals(request.getFromAccount())) {
            throw new RuntimeException("계좌 불일치");
        }

        if (request.getFromAccount().equals(request.getToAccount())) {
            throw new RuntimeException("같은 계좌 거래 불가");
        }

        Account toAccount = accountRepository.findById(request.getToAccount())
                .orElseThrow(() -> new IllegalArgumentException("입금 계좌 없음"));

        if (fromAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("잔액 부족");
        }

        fromAccount.withdraw(request.getAmount());

        toAccount.deposit(request.getAmount());

        Transaction withdrawTx = new Transaction(
                fromAccount,
                TransactionType.WITHDRAW,
                request.getAmount(),
                fromAccount.getBalance(),
                TransactionStatus.COMPLETED,
                "이체 출금",
                LocalDateTime.now()
        );

        Transaction depositTx = new Transaction(
                toAccount,
                TransactionType.DEPOSIT,
                request.getAmount(),
                toAccount.getBalance(),
                TransactionStatus.COMPLETED,
                "이체 입금",
                LocalDateTime.now()
        );

        transactionRepository.save(withdrawTx);
        transactionRepository.save(depositTx);

        return TransactionResponseDTO.from(withdrawTx);
    }
}