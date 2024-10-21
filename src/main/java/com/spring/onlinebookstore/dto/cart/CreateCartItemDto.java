package com.spring.onlinebookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemDto(
        @NotNull
        @Positive
        Long bookId,
        @NotNull
        @Positive
        int quantity
) {
}
