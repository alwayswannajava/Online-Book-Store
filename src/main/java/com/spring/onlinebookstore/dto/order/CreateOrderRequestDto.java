package com.spring.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(
        @Size(min = 8, message = "is too short")
        @Size(max = 255, message = "is too long")
        @NotNull
        String shippingAddress
) {
}
