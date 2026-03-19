package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.AccountResponse;
import com.hanaro.finmall.common.security.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(@AuthenticationPrincipal UserAuthDTO user) {
        List<AccountResponse> response = accountService.getAccounts(user);
        return ResponseEntity.ok(response);
    }
}