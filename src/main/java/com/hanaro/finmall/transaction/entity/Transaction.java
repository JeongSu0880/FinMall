package com.hanaro.finmall.transaction.entity;

import com.hanaro.finmall.account.entity.Account;
import com.hanaro.finmall.common.BaseEntity;
import com.hanaro.finmall.transaction.TransactionStatus;
import com.hanaro.finmall.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Transaction")
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
    private Integer amount;

    @Column(nullable = false)
    private Long balanceAfter;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(length = 255)
    private String description;

    @Builder
    public Transaction(Account account, TransactionType transactionType, Integer amount, Long balanceAfter, LocalDateTime occurredAt, TransactionStatus status, String description) {
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.occurredAt = occurredAt;
        this.status = status;
        this.description = description;
    }
}