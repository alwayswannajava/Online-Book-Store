package com.spring.onlinebookstore.repository.orderitem;

import com.spring.onlinebookstore.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(Long orderItemId,
                                                    Long orderId,
                                                    Long userId);
}
