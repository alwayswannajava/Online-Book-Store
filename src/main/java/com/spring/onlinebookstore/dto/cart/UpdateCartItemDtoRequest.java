package com.spring.onlinebookstore.dto.cart;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemDtoRequest(
        @Positive
        int quantity
) {
}
