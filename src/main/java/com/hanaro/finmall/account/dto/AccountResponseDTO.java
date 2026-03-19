package com.hanaro.finmall.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hanaro.finmall.account.AccountStatus;
import com.hanaro.finmall.common.AccountNumberSerializer;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AccountResponseDTO {
    private Long id;
    private Long userId;
    private Long productId;

    @JsonSerialize(using = AccountNumberSerializer.class)
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