package com.spring.onlinebookstore.dto.shoppingcart;

import com.spring.onlinebookstore.dto.cart.CartItemDto;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
