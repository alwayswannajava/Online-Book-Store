package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.cart.CreateCartItemDto;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoRequest;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoResponse;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get user's shopping cart", description = "Get shopping cart")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        return shoppingCartService.getShoppingCart(getUserId(authentication));
    }

    @PostMapping
    @Tag(name = "post", description = "POST methods of Book APIs")
    @Operation(summary = "Add book to shopping cart",
            description = "Add book to user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addItemToShoppingCart(Authentication authentication,
                                                 @RequestBody @Valid
                                                 CreateCartItemDto createCartItemDto) {
        return shoppingCartService.addItemToShoppingCart(createCartItemDto,
                getUserId(authentication));
    }

    @PutMapping("/items/{id}")
    @Tag(name = "update", description = "UPDATE methods of Book APIs")
    @Operation(summary = "Update book in shopping cart",
            description = "Update book in user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    public UpdateCartItemDtoResponse updateCartItem(@RequestBody @Valid
                                                        UpdateCartItemDtoRequest
                                                                updateCartItemRequest,
                                                    @PathVariable Long id) {
        return shoppingCartService.updateCartItem(updateCartItemRequest, id);
    }

    @DeleteMapping("/items/{id}")
    @Tag(name = "delete", description = "DELETE methods of Book APIs")
    @Operation(summary = "Delete book from shopping cart",
            description = "Delete book from user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.removeItemFromShoppingCart(id);
    }

    private Long getUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
