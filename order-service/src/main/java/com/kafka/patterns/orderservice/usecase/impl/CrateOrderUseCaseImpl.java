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
        OrderEntity savedOrder = orderRepository.save(OrderEntity.builder()
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.CREATED)
                .build());
        OutboxEventEntity event = OutboxEventEntity.builder()
                .aggregateId(savedOrder.getId().toString())
                .aggregateType(OrderEntity.class.getSimpleName())
                .payload(orderMapper.toDto(savedOrder))
                .topic("create-order-topic")
                .published(false)
                .build();

        outboxRepository.save(event);
        return savedOrder;
    }
}
