package com.kafka.patterns.orderservice.usecase;

import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import org.springframework.transaction.annotation.Transactional;

public interface CrateOrderUseCase {

    @Transactional
    OrderEntity execute(String product, int quantity);

}
