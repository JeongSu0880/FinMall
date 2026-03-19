package com.hanaro.finmall.auth;

import com.hanaro.finmall.account.Account;
import com.hanaro.finmall.account.AccountService;
import com.hanaro.finmall.auth.dto.SignupRequestDTO;
import com.hanaro.finmall.user.User;
import com.hanaro.finmall.user.UserMapper;
import com.hanaro.finmall.user.UserRepository;
import com.hanaro.finmall.user.UserRole;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;


    @Transactional
    public UserResponseDTO signup(SignupRequestDTO req) {

        User user = userMapper.toEntity(req);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);

        Account account = accountService.createDefaultAccount(user);
        return userMapper.toResponse(user);
    }
}