package com.hanaro.finmall.product.dto;

import com.hanaro.finmall.product.InterestType;
import com.hanaro.finmall.product.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private ProductType productType;
    private InterestType interestType;
    private BigDecimal baseRate;
    private Integer totalPeriodDays;
}