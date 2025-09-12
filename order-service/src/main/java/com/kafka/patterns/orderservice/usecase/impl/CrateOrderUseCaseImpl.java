package com.kafka.patterns.orderservice.usecase.impl;

import com.kafka.patterns.common.domain.entities.Order;
import com.kafka.patterns.common.dto.OrderStatus;
import com.kafka.patterns.orderservice.entity.domain.OutboxEvent;
import com.kafka.patterns.orderservice.outbox.OutboxRepository;
import com.kafka.patterns.orderservice.repo.OrderRepository;
import com.kafka.patterns.orderservice.usecase.CrateOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrateOrderUseCaseImpl implements CrateOrderUseCase {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    @Override
    public Order execute(String product, int quantity) {
        Order newOrder = Order.builder()
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.CREATED)
                .build();
        OutboxEvent event = OutboxEvent.builder()
                .aggregateId(newOrder.getId().toString())
                .aggregateType(Order.class.getSimpleName())
                .payload(newOrder)
                .topic("create-order-topic")
                .build();
        newOrder = orderRepository.save(newOrder);
        outboxRepository.save(event);
        return newOrder;
    }
}
