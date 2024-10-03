package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponse toUserResponse(User user);

    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);
}
