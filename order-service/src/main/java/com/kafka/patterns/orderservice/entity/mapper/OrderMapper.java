package com.kafka.patterns.orderservice.entity.mapper;

import com.kafka.patterns.common.dto.OrderDTO;
import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    @ValueMappings({
            @ValueMapping(source = "CREATED", target = "CREATED"),
            @ValueMapping(source = "CANCELLED", target = "CANCELLED"),
            @ValueMapping(source = "COMPLETED", target = "COMPLETED")
    })
    @Mapping(target = "createdAt", source = "createdAt")
    OrderDTO toDto(OrderEntity order);
}