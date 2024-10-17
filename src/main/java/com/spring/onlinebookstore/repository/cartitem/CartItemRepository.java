package com.spring.onlinebookstore.repository.cartitem;

import com.spring.onlinebookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
