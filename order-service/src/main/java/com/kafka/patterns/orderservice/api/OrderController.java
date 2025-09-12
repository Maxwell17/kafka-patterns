package com.kafka.patterns.orderservice.api;

import com.kafka.patterns.common.domain.entities.Order;
import com.kafka.patterns.orderservice.usecase.CrateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CrateOrderUseCase createOrder;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderRequest req) {
        return ResponseEntity.ok(createOrder.execute(req.product(), req.quantity()));
    }

    public record CreateOrderRequest(String product, int quantity) {}
}