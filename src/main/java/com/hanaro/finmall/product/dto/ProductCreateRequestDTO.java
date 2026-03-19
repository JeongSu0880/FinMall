package com.hanaro.finmall.product.dto;

import com.hanaro.finmall.product.InterestType;
import com.hanaro.finmall.product.ProductType;

import java.math.BigDecimal;

public class ProductCreateRequestDTO {

    private String name;
    private String bankId;
    private ProductType productType;
    private InterestType interestType;
    private BigDecimal baseRate;
    private Integer paymentCycle;
    private Integer minPaymentAmount;
    private Integer maxPaymentAmount;
    private BigDecimal earlyWithrawalRate;
    private Integer minAge;
    private Integer maxAge;
    private Integer depositProtectionLimit;
    private Integer totalPeriodDays;
}