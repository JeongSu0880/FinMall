package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.AccountResponse;
import com.hanaro.finmall.common.security.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    public List<AccountResponse> getAccounts(UserAuthDTO user) {

        return accountRepository.findByUserId(user.getId()).stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }
}