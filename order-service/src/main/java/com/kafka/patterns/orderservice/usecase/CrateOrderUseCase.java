package com.kafka.patterns.orderservice.usecase;

import com.kafka.patterns.orderservice.entity.domain.OrderEntity;

public interface CrateOrderUseCase {

    OrderEntity execute(String product, int quantity);

}
