package com.kafka.patterns.orderservice.usecase;

import com.kafka.patterns.common.domain.entities.OrderEntity;

public interface CrateOrderUseCase {

    OrderEntity execute(String product, int quantity);

}
