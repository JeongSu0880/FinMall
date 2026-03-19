package com.hanaro.finmall.transaction.dto;

import com.hanaro.finmall.transaction.Transaction;
import com.hanaro.finmall.transaction.TransactionStatus;
import com.hanaro.finmall.transaction.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionResponseDTO {

    private Long id;

    private Long accountId;

    private TransactionType transactionType;

    private Integer amount;

    private Long balanceAfter;

    private LocalDateTime occurredAt;

    private TransactionStatus status;

    private String description;

    public static TransactionResponseDTO from(Transaction withdrawTx) {
        return TransactionResponseDTO.builder()
                .id(withdrawTx.getId())
                .accountId(withdrawTx.getAccount().getId())
                .transactionType(withdrawTx.getTransactionType())
                .amount(withdrawTx.getAmount())
                .balanceAfter(withdrawTx.getBalanceAfter())
                .occurredAt(withdrawTx.getOccurredAt())
                .status(withdrawTx.getStatus())
                .description(withdrawTx.getDescription())
                .build();
    }
}