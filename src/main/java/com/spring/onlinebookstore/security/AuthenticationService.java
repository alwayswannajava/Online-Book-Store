package com.spring.onlinebookstore.security;

import com.spring.onlinebookstore.dto.user.UserLoginRequestDto;
import com.spring.onlinebookstore.dto.user.UserLoginResponse;

public interface AuthenticationService {
    UserLoginResponse authenticate(UserLoginRequestDto userLoginRequestDto);
}
