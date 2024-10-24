package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.spring.onlinebookstore.dto.order.OrderDto;
import com.spring.onlinebookstore.dto.order.OrderItemDto;
import com.spring.onlinebookstore.dto.order.UpdateOrderRequestDto;
import com.spring.onlinebookstore.exception.ShoppingCartIsEmptyException;
import com.spring.onlinebookstore.model.User;
import com.spring.onlinebookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders management", description = "Endpoints for managing user's orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "post", description = "POST methods of Book APIs")
    @Operation(summary = "Create order", description = "Create order")
    @PreAuthorize("hasRole('USER')")
    public OrderDto createOrder(@RequestBody @Valid CreateOrderRequestDto createOrderRequestDto,
                                Authentication authentication)
            throws ShoppingCartIsEmptyException {
        return orderService.createOrder(createOrderRequestDto, getUserById(authentication));
    }

    @GetMapping
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get user's orders history", description = "Get user's orders history")
    @PreAuthorize("hasRole('USER')")
    public Page<OrderDto> getOrder(Authentication authentication, Pageable pageable) {
        return orderService.getOrderHistory(getUserById(authentication), pageable);
    }

    @PatchMapping("/{id}")
    @Tag(name = "patch", description = "PATCH methods of Book APIs")
    @Operation(summary = "Update user's orders status", description = "Update user's orders status")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto updateOrderStatus(@RequestBody @Valid
                                          UpdateOrderRequestDto updateOrderRequestDto,
                                      @PathVariable Long id,
                                      Authentication authentication) {
        return orderService.updateOrder(updateOrderRequestDto, id, getUserById(authentication));
    }

    @GetMapping("/{id}/items")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get user's order items by order id",
            description = "Get user's order items by order id")
    @PreAuthorize("hasRole('USER')")
    public Set<OrderItemDto> findAllOrderItemsByOrderId(@PathVariable Long id,
                                                        Authentication authentication) {
        return orderService.findAllOrderItemsByOrderId(id, getUserById(authentication));
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get specific order item by order item id and order id",
            description = "Get specific order item by order item id and order id")
    @PreAuthorize("hasRole('USER')")
    public OrderItemDto findOrderItemByOrderItemIdAndOrderId(@PathVariable(name = "orderId")
                                                                 Long id,
                                                             @PathVariable(name = "itemId")
                                                             Long itemId,
                                                             Authentication authentication) {

        return orderService.findOrderItemByOrderItemIdAndOrderId(id,
                itemId, getUserById(authentication));
    }

    private Long getUserById(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
