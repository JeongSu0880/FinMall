package com.hanaro.finmall.account.dto;

import com.hanaro.finmall.account.AccountStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStatusUpdateDTO {
    private AccountStatus status;
}