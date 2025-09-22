package com.kafka.patterns.common.dto;

import java.time.Instant;

public record OrderDTO(Long id, String product, int quantity, OrderStatus status, Instant createdAt) {
}
