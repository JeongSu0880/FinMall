package com.hanaro.finmall.auth;

import com.hanaro.finmall.auth.dto.LoginRequestDTO;
import com.hanaro.finmall.auth.dto.SignupRequestDTO;
import com.hanaro.finmall.common.security.JwtUtil;
import com.hanaro.finmall.user.User;
import com.hanaro.finmall.user.UserMapper;
import com.hanaro.finmall.user.UserRepository;
import com.hanaro.finmall.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDTO req) {
        User user = userMapper.toEntity(req);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);

        userRepository.save(user);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(LoginRequestDTO loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            return ResponseEntity.ok(jwtUtil.authenticationToClaims(authenticate));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid!");
        }
    }
}