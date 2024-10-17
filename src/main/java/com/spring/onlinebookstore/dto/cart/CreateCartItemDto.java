package com.spring.onlinebookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemDto {
    @NotNull
    @Positive
    private Long bookId;
    @NotNull
    @Positive
    private int quantity;
}
