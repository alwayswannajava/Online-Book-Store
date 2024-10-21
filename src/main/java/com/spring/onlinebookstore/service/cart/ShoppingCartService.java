package com.spring.onlinebookstore.service.cart;

import com.spring.onlinebookstore.dto.cart.CreateCartItemDto;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoRequest;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    void createShoppingCart(User user);

    ShoppingCartDto addItemToShoppingCart(CreateCartItemDto createCartItemDto, Long userId);

    ShoppingCartDto updateCartItem(Long cartItemId,
                                   UpdateCartItemDtoRequest updateCartItemRequest,
                                   Long userId);

    void removeItemFromShoppingCart(Long cartItemId, Long userId);
}
