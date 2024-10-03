package com.spring.onlinebookstore.service;

import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.exception.RegistrationException;
import com.spring.onlinebookstore.mapper.UserMapper;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.email())) {
            throw new RegistrationException("Can't register user, duplicate email");
        }
        User user = userMapper.toUser(userRegistrationRequestDto);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
