package com.hanaro.finmall.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateRequestDTO {

    private String name;
    private Long minPaymentAmount;
    private Long maxPaymentAmount;
    private BigDecimal baseRate;
    private BigDecimal earlyWithrawalRate;
    private Integer minAge;
    private Integer maxAge;
    private Long depositProtectionLimit;
}