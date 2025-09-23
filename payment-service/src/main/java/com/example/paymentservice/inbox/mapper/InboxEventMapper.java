package com.example.paymentservice.inbox.mapper;

import com.example.paymentservice.inbox.domain.InboxEvent;
import com.google.protobuf.Timestamp;
import com.kafka.patterns.proto.OutboxOrderEvent;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
imports = {Instant.class, Timestamp.class})
public interface InboxEventMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "event.product")
    @Mapping(target = "quantity", source = "event.quantity")
    @Mapping(target = "status", source = "event.status")
    @Mapping(target = "topic", constant = "create-order-topic")
    @ValueMappings({
            @ValueMapping(source = "CREATED", target = "CREATED"),
            @ValueMapping(source = "CANCELLED", target = "CANCELLED"),
            @ValueMapping(source = "COMPLETED", target = "COMPLETED"),
            @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.THROW_EXCEPTION)
    })
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    InboxEvent toInboxEvent(OutboxOrderEvent event, String id);

}
