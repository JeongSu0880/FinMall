package com.hanaro.finmall.user;

import com.hanaro.finmall.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);
}