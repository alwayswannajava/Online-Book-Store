package com.spring.onlinebookstore.service.user;

import com.spring.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.spring.onlinebookstore.dto.user.UserResponse;
import com.spring.onlinebookstore.exception.RegistrationException;
import com.spring.onlinebookstore.mapper.UserMapper;
import com.spring.onlinebookstore.model.Role;
import com.spring.onlinebookstore.model.RoleName;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.repository.role.RoleRepository;
import com.spring.onlinebookstore.repository.user.UserRepository;
import com.spring.onlinebookstore.service.cart.ShoppingCartService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponse register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.email())) {
            throw new RegistrationException("Can't register user, duplicate email");
        }
        User user = userMapper.toUser(userRegistrationRequestDto);
        Role userRole = roleRepository.findByRole(RoleName.ROLE_USER);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        shoppingCartService.addShoppingCart(user);
        return userMapper.toUserResponse(user);
    }
}
