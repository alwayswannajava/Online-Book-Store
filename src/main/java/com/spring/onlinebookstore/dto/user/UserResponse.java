package com.spring.onlinebookstore.dto.user;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String shippingAddress
) {
}
