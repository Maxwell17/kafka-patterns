package com.kafka.patterns.common.dto;

import java.time.OffsetDateTime;

public record OrderDTO(String id, String product, int quantity, OrderStatus status, OffsetDateTime createdAt) {
}
