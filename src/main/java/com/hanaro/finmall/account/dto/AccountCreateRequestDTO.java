package com.hanaro.finmall.account.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequestDTO {

    private Long productId;

    @Pattern(regexp = "^[0-9\\-]*$", message = "숫자와 -만 입력 가능합니다")
    private String accountNumber;
    private Integer paymentDay;
}