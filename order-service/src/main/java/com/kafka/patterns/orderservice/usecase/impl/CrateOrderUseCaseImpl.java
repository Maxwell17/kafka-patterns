package com.kafka.patterns.orderservice.usecase.impl;

import com.kafka.patterns.common.dto.OrderStatus;
import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import com.kafka.patterns.orderservice.entity.domain.OutboxEventEntity;
import com.kafka.patterns.orderservice.entity.mapper.OrderMapper;
import com.kafka.patterns.orderservice.outbox.OutboxRepository;
import com.kafka.patterns.orderservice.repo.OrderRepository;
import com.kafka.patterns.orderservice.usecase.CrateOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrateOrderUseCaseImpl implements CrateOrderUseCase {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    @Override
    @Transactional
    public OrderEntity execute(String product, int quantity) {
        OrderEntity savedOrder = createOrder(product, quantity);
        log.info("Order created with id: {}", savedOrder.getId());
        OutboxEventEntity outboxEventForOrder = createOutboxEventForOrder(savedOrder);
        log.info("Outbox event created with id: {}", outboxEventForOrder.getId());
        return savedOrder;
    }

    private OutboxEventEntity createOutboxEventForOrder(OrderEntity savedOrder) {
        OutboxEventEntity event = OutboxEventEntity.builder()
                .aggregateId(savedOrder.getId().toString())
                .aggregateType(OrderEntity.class.getSimpleName())
                .payload(orderMapper.toDto(savedOrder))
                .topic("create-order-topic")
                .published(false)
                .build();

        return outboxRepository.save(event);
    }

    @NotNull
    private OrderEntity createOrder(String product, int quantity) {
        OrderEntity newOrder = OrderEntity.builder()
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.CREATED)
                .build();
        return orderRepository.save(newOrder);
    }
}
