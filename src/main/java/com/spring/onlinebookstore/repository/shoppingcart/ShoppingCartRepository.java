package com.spring.onlinebookstore.repository.shoppingcart;

import com.spring.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc "
            + " LEFT JOIN FETCH sc.user u "
            + " LEFT JOIN FETCH sc.items ci "
            + " LEFT JOIN FETCH ci.book "
            + " WHERE sc.user.id = :userId ")
    Optional<ShoppingCart> findByIdFetchUserAndCartItems(@Param("userId") Long userId);
}
