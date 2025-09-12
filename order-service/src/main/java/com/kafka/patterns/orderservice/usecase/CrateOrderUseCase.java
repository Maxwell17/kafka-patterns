package com.kafka.patterns.orderservice.usecase;

import com.kafka.patterns.common.domain.entities.Order;

public interface CrateOrderUseCase {

    Order execute(String product, int quantity);

}
