package com.spring.onlinebookstore.service.order;

import com.spring.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.spring.onlinebookstore.dto.order.OrderDto;
import com.spring.onlinebookstore.dto.order.OrderItemDto;
import com.spring.onlinebookstore.dto.order.UpdateOrderRequestDto;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequestDto createOrderRequestDto, Long userId);

    Page<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    OrderDto updateOrder(UpdateOrderRequestDto updateOrderRequestDto, Long orderId, Long userId);

    Set<OrderItemDto> findAllOrderItemsByOrderId(Long orderId, Long userId);

    OrderItemDto findOrderItemByOrderItemIdAndOrderId(Long orderItemId, Long orderId, Long userId);

}
