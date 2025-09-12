package com.kafka.patterns.orderservice.outbox;

import com.kafka.patterns.common.dto.OrderStatus;
import com.kafka.patterns.orderservice.entity.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByPublishedIsFalseOrderByCreatedAt();

    default OutboxEvent updatePublishedStatus(OutboxEvent event, Boolean published) {
        event.setPublished(published);
        return save(event);
    }
}
