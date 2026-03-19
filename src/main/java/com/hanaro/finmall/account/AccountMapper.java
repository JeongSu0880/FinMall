package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.AccountCreateRequestDTO;
import com.hanaro.finmall.account.dto.AccountResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    AccountResponseDTO toResponse(Account account);

    Account toEntity(AccountCreateRequestDTO req);
}