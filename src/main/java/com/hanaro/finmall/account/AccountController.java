package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.*;
import com.hanaro.finmall.common.security.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAccounts(@AuthenticationPrincipal UserAuthDTO user) {
        List<AccountResponseDTO> response = accountService.getAccounts(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public Long create(@PathVariable Long userId,
                       @RequestBody AccountCreateRequestDTO req) {
        return accountService.create(userId, req);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public List<AccountResponseDTO> searchAccounts(
            @PathVariable Long userId,
            AccountSearchDTO search
    ) {
        return accountService.search(userId, search);
    }

    @PatchMapping("/{accountId}/status")
    public Long updateStatus(@PathVariable Long accountId,
                             @RequestBody AccountStatusUpdateDTO req,
                             Authentication authentication) {

        return accountService.updateStatus(accountId, req, authentication);
    }

    @PostMapping("/{accountId}/deposit")
    public Long deposit(@PathVariable Long accountId,
                        @RequestBody AccountDepositRequestDTO req,
                        @AuthenticationPrincipal UserAuthDTO user) {
        return accountService.deposit(accountId, req, user);
    }
}