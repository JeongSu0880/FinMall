package com.hanaro.finmall.transaction.dto;

import com.hanaro.finmall.transaction.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @NotNull(message = "fromAccount는 필수입니다.")
    private Long fromAccount;

    @NotNull(message = "toAccount는 필수입니다.")
    private Long toAccount;

    @NotNull(message = "거래 타입은 필수입니다.")
    private TransactionType type;

    @NotNull(message = "금액은 필수입니다.")
    @Min(value = 1, message = "금액은 1원 이상이어야 합니다.")
    private Long amount;
}