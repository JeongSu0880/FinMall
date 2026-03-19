package com.hanaro.finmall.user;

import com.hanaro.finmall.auth.dto.SignupRequestDTO;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toEntity(SignupRequestDTO dto);
}