package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.order.OrderItemDto;
import com.spring.onlinebookstore.model.CartItem;
import com.spring.onlinebookstore.model.OrderItem;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "book")
    @Mapping(target = "price", source = "book.price")
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    Set<OrderItemDto> toOrderItemDtoSet(Set<OrderItem> orderItems);
}
