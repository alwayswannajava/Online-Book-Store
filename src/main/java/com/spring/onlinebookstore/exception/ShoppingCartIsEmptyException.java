package com.spring.onlinebookstore.exception;

public class ShoppingCartIsEmptyException extends Exception {
    public ShoppingCartIsEmptyException(String message) {
        super(message);
    }
}
