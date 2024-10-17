package com.spring.onlinebookstore.dto.cart;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity
) {
}
