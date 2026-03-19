package com.hanaro.finmall;

import com.hanaro.finmall.account.Account;
import com.hanaro.finmall.account.AccountRepository;
import com.hanaro.finmall.account.AccountStatus;
import com.hanaro.finmall.product.Product;
import com.hanaro.finmall.product.ProductRepository;
import com.hanaro.finmall.transaction.Transaction;
import com.hanaro.finmall.transaction.TransactionRepository;
import com.hanaro.finmall.transaction.TransactionStatus;
import com.hanaro.finmall.transaction.TransactionType;
import com.hanaro.finmall.user.User;
import com.hanaro.finmall.user.UserRepository;
import com.hanaro.finmall.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.findByUsername("hanaro").isPresent()) {
            return;
        }

        User admin = User.builder()
                .username("hanaro")
                .password(passwordEncoder.encode("12345678"))
                .role(UserRole.ADMIN)
                .birthDate(LocalDate.of(1990, 1, 1))
                .enabled(true)
                .build();

        User user1 = User.builder()
                .username("user1@test.com")
                .password(passwordEncoder.encode("12345678"))
                .role(UserRole.USER)
                .birthDate(LocalDate.of(1995, 5, 10))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .username("user2@test.com")
                .password(passwordEncoder.encode("12345678"))
                .role(UserRole.USER)
                .birthDate(LocalDate.of(2000, 8, 20))
                .enabled(true)
                .build();

        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);

        Product product1 = productRepository.findById(1L).orElseThrow();
        Product product2 = productRepository.findById(2L).orElseThrow();


        Account account1 = Account.builder()
                .user(user1)
                .product(product2)
                .accountNumber("11122223333")
                .status(AccountStatus.ACTIVE)
                .principal(100000L)
                .accruedInterest(500L)
                .appliedRate(new BigDecimal(4.00))
                .currentInstallmentCount(2)
                .paymentDay(10)
                .lastPaidAt(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .maturityAt(LocalDateTime.now().plusDays(365))
                .terminatedAt(null)
                .isDefault(false)
                .build();

        Account account2 = Account.builder()
                .user(user2)
                .product(product1)
                .accountNumber("22233334444")
                .status(AccountStatus.ACTIVE)
                .principal(5000000L)
                .accruedInterest(0L)
                .appliedRate(new BigDecimal(3.50))
                .currentInstallmentCount(1)
                .paymentDay(null)
                .lastPaidAt(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .maturityAt(LocalDateTime.now().plusDays(365))
                .terminatedAt(null)
                .isDefault(false)
                .build();

        accountRepository.save(account1);
        accountRepository.save(account2);


        Transaction tx1 = Transaction.builder()
                .account(account1)
                .transactionType(TransactionType.DEPOSIT)
                .amount(50000L)
                .balanceAfter(50000L)
                .occurredAt(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .description("첫 납입")
                .build();

        Transaction tx2 = Transaction.builder()
                .account(account1)
                .transactionType(TransactionType.DEPOSIT)
                .amount(50000L)
                .balanceAfter(100000L)
                .occurredAt(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .description("두 번째 납입")
                .build();

        Transaction tx3 = Transaction.builder()
                .account(account2)
                .transactionType(TransactionType.DEPOSIT)
                .amount(5000000L)
                .balanceAfter(5000000L)
                .occurredAt(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .description("예금 가입")
                .build();

        transactionRepository.save(tx1);
        transactionRepository.save(tx2);
        transactionRepository.save(tx3);
    }
}