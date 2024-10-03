package com.spring.onlinebookstore.security;

import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.exception.RegistrationException;

public interface AuthenticationService {
    UserResponse register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
