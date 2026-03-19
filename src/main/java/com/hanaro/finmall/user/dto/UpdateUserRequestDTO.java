package com.hanaro.finmall.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequestDTO {
    private String username;
    private LocalDate birthDate;
}