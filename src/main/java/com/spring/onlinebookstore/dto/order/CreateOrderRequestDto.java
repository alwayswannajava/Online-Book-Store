package com.spring.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(
        @Size(min = 8, max = 255, message = "must be between 8 and 255 characters")
        @NotBlank
        String shippingAddress
) {
}
