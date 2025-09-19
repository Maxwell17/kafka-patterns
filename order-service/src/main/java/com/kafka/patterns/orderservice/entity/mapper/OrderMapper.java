package com.kafka.patterns.orderservice.entity.mapper;

import com.kafka.patterns.common.dto.OrderDTO;
import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDto(OrderEntity order);
}