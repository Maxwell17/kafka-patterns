package com.example.paymentservice.inbox.domain;

import com.kafka.patterns.common.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "inbox_event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboxEvent {

    @Id
    private String id;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

}