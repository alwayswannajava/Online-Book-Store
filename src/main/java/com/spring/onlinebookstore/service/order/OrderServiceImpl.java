package com.spring.onlinebookstore.service.order;

import com.spring.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.spring.onlinebookstore.dto.order.OrderDto;
import com.spring.onlinebookstore.dto.order.OrderItemDto;
import com.spring.onlinebookstore.dto.order.UpdateOrderRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.OrderItemMapper;
import com.spring.onlinebookstore.mapper.OrderMapper;
import com.spring.onlinebookstore.model.CartItem;
import com.spring.onlinebookstore.model.Order;
import com.spring.onlinebookstore.model.OrderItem;
import com.spring.onlinebookstore.model.ShoppingCart;
import com.spring.onlinebookstore.model.Status;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.repository.order.OrderRepository;
import com.spring.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.spring.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.spring.onlinebookstore.repository.user.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequestDto createOrderRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart with id " + userId + " not found")
        );
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(loadOrderItems(shoppingCart));
        setOrderToOrderItems(order.getOrderItems(), order);
        order.setTotal(new BigDecimal(calculateTotal(shoppingCart)));
        order.setShippingAddress(createOrderRequestDto.shippingAddress());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderDto> getOrderHistory(Long userId, Pageable pageable) {
        userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User's orders with id " + userId + " not found")
        );
        return orderRepository.findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderDto updateOrder(UpdateOrderRequestDto updateOrderRequestDto,
                                Long orderId,
                                Long userId) {

        userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User's order with id " + userId + " not found")
        );
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + orderId + " not found")
        );
        order.setStatus(updateOrderRequestDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderItemDto> findAllOrderItemsByOrderId(Long orderId, Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User's order with id " + userId + " not found")
        );
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Order with id " + orderId + " not found")
                );
        return orderItemMapper.toOrderItemDtoSet(order.getOrderItems());
    }

    @Override
    public OrderItemDto findOrderItemByOrderItemIdAndOrderId(Long orderItemId,
                                                             Long orderId,
                                                             Long userId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderIdAndOrderUserId(
                orderItemId, orderId, userId).orElseThrow(
                        () -> new EntityNotFoundException("OrderItem with id "
                        + orderItemId
                        + " not found")
        );
        return orderItemMapper.toDto(orderItem);
    }

    private int calculateTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(CartItem::getQuantity)
                .mapToInt(quantity -> quantity)
                .sum();
    }

    private Set<OrderItem> loadOrderItems(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(orderItemMapper::toOrderItem)
                .collect(Collectors.toSet());
    }

    private void setOrderToOrderItems(Set<OrderItem> orderItems, Order order) {
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
    }
}
