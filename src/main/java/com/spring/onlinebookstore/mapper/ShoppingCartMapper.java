package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.cart.CartItemDto;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.model.CartItem;
import com.spring.onlinebookstore.model.ShoppingCart;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {UserMapper.class, CartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);

    @AfterMapping
    default void setCartItems(@MappingTarget ShoppingCartDto shoppingCartDto,
                              ShoppingCart shoppingCart) {
        Set<CartItemDto> shoppingCartDtoItems = new HashSet<>();
        for (CartItem cartItem : shoppingCart.getItems()) {
            CartItemDto cartItemDto = new CartItemDto(cartItem.getId(), cartItem.getBook().getId(),
                    cartItem.getBook().getTitle(), cartItem.getQuantity());
            shoppingCartDtoItems.add(cartItemDto);
        }
        shoppingCartDto.setCartItems(shoppingCartDtoItems);
    }
}
