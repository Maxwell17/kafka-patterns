package com.kafka.patterns.common.mapper;

import com.kafka.patterns.common.domain.entities.Order;
import com.kafka.patterns.common.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}