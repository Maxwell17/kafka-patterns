package com.kafka.patterns.orderservice.outbox;

import com.kafka.patterns.orderservice.entity.domain.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEventEntity, Long> {

    List<OutboxEventEntity> findByPublishedIsFalseOrderByCreatedAt();

    default OutboxEventEntity updatePublishedStatus(OutboxEventEntity event, Boolean published) {
        event.setPublished(published);
        return save(event);
    }
}
