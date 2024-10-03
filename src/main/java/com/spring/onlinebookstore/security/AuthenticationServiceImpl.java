package com.spring.onlinebookstore.security;

import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.mapper.UserMapper;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userMapper.toUser(userRegistrationRequestDto);
        return userMapper.toUserResponse(userService.save(user));
    }
}
