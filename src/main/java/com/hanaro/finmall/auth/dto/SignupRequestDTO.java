package com.hanaro.finmall.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupRequestDTO {

    private String username;

    private String password;

    private LocalDate birthDate;
}