package com.hanaro.finmall.transaction;

import com.hanaro.finmall.account.Account;
import com.hanaro.finmall.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Transaction")
@Builder
@AllArgsConstructor
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false, columnDefinition = "INT UNSIGNED")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long balanceAfter;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(length = 255)
    private String description;

    public Transaction(Account account,
                       TransactionType transactionType,
                       Long amount,
                       Long balance,
                       TransactionStatus transactionStatus,
                       String description,
                       LocalDateTime now) {
        super();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setOccuredAt() {
        this.occurredAt = LocalDateTime.now();
    }
}