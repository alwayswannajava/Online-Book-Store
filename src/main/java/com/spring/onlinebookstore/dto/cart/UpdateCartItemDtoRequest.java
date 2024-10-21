package com.spring.onlinebookstore.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDtoRequest {
    @Positive
    private int quantity;
}
