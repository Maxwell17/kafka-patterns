package com.kafka.patterns.orderservice.repo;

import com.kafka.patterns.orderservice.entity.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}