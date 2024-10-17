package com.spring.onlinebookstore.service.cart;

import com.spring.onlinebookstore.dto.cart.CreateCartItemDto;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoRequest;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoResponse;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.CartItemMapper;
import com.spring.onlinebookstore.mapper.ShoppingCartMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.model.CartItem;
import com.spring.onlinebookstore.model.ShoppingCart;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.repository.book.BookRepository;
import com.spring.onlinebookstore.repository.cart.ShoppingCartRepository;
import com.spring.onlinebookstore.repository.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId
                ));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void addShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto addItemToShoppingCart(CreateCartItemDto createCartItemDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId
                ));
        Book book = bookRepository.findById(createCartItemDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by book id: " + createCartItemDto.getBookId()
                ));
        CartItem cartItem = cartItemMapper.toModel(createCartItemDto);
        cartItem.setBook(book);
        shoppingCart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public UpdateCartItemDtoResponse updateCartItem(UpdateCartItemDtoRequest updateCartItemRequest,
                                                    Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by item id: " + cartItemId
                ));
        cartItemMapper.toModel(updateCartItemRequest, cartItem);
        return cartItemMapper.toResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public void removeItemFromShoppingCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
