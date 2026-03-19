package com.hanaro.finmall.user.dto;

import com.hanaro.finmall.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private UserRole role;
    private LocalDate birthDate;
    private boolean enabled;
}