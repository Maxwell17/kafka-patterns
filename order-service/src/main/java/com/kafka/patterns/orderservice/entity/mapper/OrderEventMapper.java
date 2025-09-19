package com.kafka.patterns.orderservice.entity.mapper;

import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import com.kafka.patterns.orderservice.entity.domain.OutboxEventEntity;
import com.kafka.patterns.proto.OutboxOrderEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEventMapper {

    OutboxOrderEvent toProto(OrderEntity orderEntity);

    OrderEntity toDto(OutboxEventEntity event);

}
