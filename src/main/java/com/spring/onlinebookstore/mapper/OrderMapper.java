package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.order.OrderDto;
import com.spring.onlinebookstore.dto.order.OrderItemDto;
import com.spring.onlinebookstore.model.Order;
import com.spring.onlinebookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderDto toDto(Order order);

    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);
}
