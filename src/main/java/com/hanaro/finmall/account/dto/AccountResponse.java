package com.hanaro.finmall.account.dto;

import com.hanaro.finmall.account.AccountStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AccountResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private String accountNumber;
    private AccountStatus status;
    private Long principal;
    private Long accruedInterest;
    private BigDecimal appliedRate;
    private Integer currentInstallmentCount;
    private Integer paymentDay;
    private LocalDateTime lastPaidAt;
    private LocalDateTime startedAt;
    private LocalDateTime maturityAt;
    private LocalDateTime terminatedAt;
}