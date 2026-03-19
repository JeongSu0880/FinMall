package com.hanaro.finmall.product;

import com.hanaro.finmall.bank.Bank;
import com.hanaro.finmall.common.BaseEntity;
import com.hanaro.finmall.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankId", nullable = false, columnDefinition = "INT UNSIGNED")
    private Bank bank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestType interestType;

    @Column
    private Integer paymentCycle;

    @Column(nullable = false)
    private Integer totalPeriodDays;

    @Column(nullable = false)
    private Long minPaymentAmount;

    @Column(nullable = false)
    private Long maxPaymentAmount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal baseRate;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal earlyWithdrawalRate;

    @Column
    private Integer minAge;

    @Column
    private Integer maxAge;

    @Column(nullable = false)
    private Long depositProtectionLimit;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    void changeName(String name) {
        this.name = name;
    }

    void changeBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    void changeEarlyWithdrawalRate(BigDecimal earlyWithdrawalRate) {
        this.earlyWithdrawalRate = earlyWithdrawalRate;
    }

    void changeDepositProtectionLimit(Long depositProtectionLimit) {
        this.depositProtectionLimit = depositProtectionLimit;
    }

    void changeMaxPaymentAmount(Long maxPaymentAmount) {
        this.maxPaymentAmount = maxPaymentAmount;
    }

    void changeMinPaymentAmount(Long minPaymentAmount) {
        this.minPaymentAmount = minPaymentAmount;
    }

    void changeMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    void changeMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

}