package com.kafka.patterns.common.mapper;

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
            @ValueMapping(source = "COMPLETED", target = "COMPLETED"),
    })
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToTimestamp")
    OutboxOrderEvent toProto(OrderDTO orderDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    @ValueMappings({
            @ValueMapping(source = "CREATED", target = "CREATED"),
            @ValueMapping(source = "CANCELLED", target = "CANCELLED"),
            @ValueMapping(source = "COMPLETED", target = "COMPLETED"),
            @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.THROW_EXCEPTION)
    })
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToInstant")
    OrderDTO toDto(OutboxOrderEvent event);

    @Named("instantToTimestamp")
    default Timestamp instantToTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    @Named("timestampToInstant")
    default Instant timestampToInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

}
