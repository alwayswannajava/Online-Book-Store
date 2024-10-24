package com.spring.onlinebookstore.dto.order;

import com.spring.onlinebookstore.model.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequestDto(
        @NotNull
        Status status
) {
}
