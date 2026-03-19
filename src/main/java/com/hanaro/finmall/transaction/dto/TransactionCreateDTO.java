package com.hanaro.finmall.transaction.dto;

import com.hanaro.finmall.transaction.TransactionStatus;
import com.hanaro.finmall.transaction.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCreateDTO {

    private TransactionType transactionType;
    private Long amount;
    private Long balanceAfter;
    private TransactionStatus status;
    private String description;

}