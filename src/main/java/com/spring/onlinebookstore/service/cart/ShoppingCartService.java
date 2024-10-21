package com.spring.onlinebookstore.service.cart;

import com.spring.onlinebookstore.dto.cart.CreateCartItemDto;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoRequest;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoResponse;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    void createShoppingCart(User user);

    ShoppingCartDto addItemToShoppingCart(CreateCartItemDto createCartItemDto, Long userId);

    UpdateCartItemDtoResponse updateCartItem(UpdateCartItemDtoRequest updateCartItemDtoRequest,
                                             Long cartItemId);

    void removeItemFromShoppingCart(Long cartItemId);
}
