package com.kafka.patterns.orderservice.entity.mapper;

import com.google.protobuf.Timestamp;
import com.kafka.patterns.common.dto.OrderDTO;
import com.kafka.patterns.proto.OutboxOrderEvent;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        imports = {com.google.protobuf.Timestamp.class})
public interface OrderEventMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    @ValueMappings({
            @ValueMapping(source = "CREATED", target = "CREATED"),
            @ValueMapping(source = "CANCELLED", target = "CANCELLED"),
            @ValueMapping(source = "COMPLETED", target = "COMPLETED")
    })
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToTimestamp")
    OutboxOrderEvent toProto(OrderDTO orderDTO);

    @Named("instantToTimestamp")
    default Timestamp instantToTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

//    @Mapping(target = "id", source = "payload.id")
//    @Mapping(target = "product", source = "payload.product")
//    @Mapping(target = "quantity", source = "payload.quantity")
//    @Mapping(target = "status", source = "payload.status")
//    @ValueMappings({
//            @ValueMapping(source = "CREATED", target = "CREATED"),
//            @ValueMapping(source = "CANCELLED", target = "CANCELLED"),
//            @ValueMapping(source = "COMPLETED", target = "COMPLETED")
//    })
//    @Mapping(source = "payload.createdAt", target = "createdAt", qualifiedByName = "timestampToInstant")
//    OrderEntity toDto(OutboxEventEntity event);

}
