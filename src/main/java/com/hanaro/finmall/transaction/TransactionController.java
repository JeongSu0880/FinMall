package com.hanaro.finmall.transaction;

import com.hanaro.finmall.common.security.UserAuthDTO;
import com.hanaro.finmall.transaction.dto.TransactionRequestDTO;
import com.hanaro.finmall.transaction.dto.TransactionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/{accountId}")
    public TransactionResponseDTO transfer(
            @PathVariable Long accountId,
            @Valid @RequestBody TransactionRequestDTO request,
            @AuthenticationPrincipal UserAuthDTO user
    ) {
        return transactionService.transfer(accountId, request, user.getId());
    }
}