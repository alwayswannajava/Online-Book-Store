package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.user.UserLoginRequestDto;
import com.spring.onlinebookstore.dto.user.UserLoginResponse;
import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.exception.RegistrationException;
import com.spring.onlinebookstore.security.AuthenticationService;
import com.spring.onlinebookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registration(@RequestBody
                                         @Valid
                                         UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        return userService.register(userRegistrationRequestDto);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }

}
