package com.spring.onlinebookstore.service.cart;

import com.spring.onlinebookstore.dto.cart.CreateCartItemDto;
import com.spring.onlinebookstore.dto.cart.UpdateCartItemDtoRequest;
import com.spring.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.CartItemMapper;
import com.spring.onlinebookstore.mapper.ShoppingCartMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.model.CartItem;
import com.spring.onlinebookstore.model.ShoppingCart;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.repository.book.BookRepository;
import com.spring.onlinebookstore.repository.cartitem.CartItemRepository;
import com.spring.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
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
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addItemToShoppingCart(CreateCartItemDto createCartItemDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId
                ));
        Book book = bookRepository.findById(createCartItemDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by book id: " + createCartItemDto.bookId()
                ));
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(item.getQuantity()
                                + createCartItemDto.quantity()),
                        () -> addCartItemToCart(createCartItemDto, book, shoppingCart));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItem(Long cartItemId,
                                          UpdateCartItemDtoRequest updateCartItemRequest,
                                          Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId
                ));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .map(item -> {
                    item.setQuantity(updateCartItemRequest.quantity());
                    return item;
                })
                .orElseThrow(() -> new EntityNotFoundException("Cart item "
                        + "with id: " + cartItemId + " was not found by user id: " + userId));
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void removeItemFromShoppingCart(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId
                ));
        cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart item "
                        + "with id: " + cartItemId + " was not found by user id: " + userId));
        cartItemRepository.deleteById(cartItemId);
    }

    private void addCartItemToCart(CreateCartItemDto createCartItemDto,
                                   Book book,
                                   ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(createCartItemDto);
        cartItem.setBook(book);
        shoppingCart.addItem(cartItem);
    }
}
