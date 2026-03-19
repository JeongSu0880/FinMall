package com.hanaro.finmall.common.security;

import com.hanaro.finmall.user.UserRepository;
import com.hanaro.finmall.user.dto.UserDTO;
import com.hanaro.finmall.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .birthDate(user.getBirthDate())
                .enabled(user.isEnabled())
                .build();
    }
}