package com.hanaro.finmall.account;

import com.hanaro.finmall.common.BaseEntity;
import com.hanaro.finmall.product.Product;
import com.hanaro.finmall.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Account")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Product product;

    @Column(nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    private Long principal;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Long accruedInterest;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal appliedRate;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer totalInstallmentCount;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer currentInstallmentCount;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer paymentDay;

    private LocalDateTime lastPaidAt;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime maturityAt;

    private LocalDateTime terminatedAt;
}