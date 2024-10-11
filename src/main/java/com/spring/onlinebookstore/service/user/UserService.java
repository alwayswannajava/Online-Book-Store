package com.spring.onlinebookstore.service.user;

import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponse register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
