package com.hanaro.finmall.product;

import com.hanaro.finmall.bank.Bank;
import com.hanaro.finmall.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Bank bank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestType interestType;

    private Integer paymentCycle;

    @Column(nullable = false)
    private Boolean isFixed;

    @Column(nullable = false)
    private Integer totalPeriodDays;

    @Column(nullable = false)
    private Integer minPaymentAmount;

    @Column(nullable = false)
    private Integer maxPaymentAmount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal baseRate;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal earlyWithdrawalRate;

    private Integer minAge;

    private Integer maxAge;

    @Column(nullable = false)
    private Integer depositProtectionLimit;
}