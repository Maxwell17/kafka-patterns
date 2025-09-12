package com.kafka.patterns.orderservice.outbox;

import com.kafka.patterns.common.domain.entities.Order;
import com.kafka.patterns.orderservice.entity.domain.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    public void publish() {
        List<OutboxEvent> events = outboxRepository.findByPublishedIsFalseOrderByCreatedAt();
        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), event.getPayload()).get();
                outboxRepository.updatePublishedStatus(event, false);
                log.info("Published outbox event id={} topic={}", event.getId(), event.getTopic());
            } catch (Exception e) {
                log.error("Failed to publish event id={}, sending to DLQ. cause={}", event.getId(), e.getMessage());
                try {
                    kafkaTemplate.send(event.getTopic() + "-dlq", event.getAggregateId(), event.getPayload()).get();
                } catch (Exception ex) {
                    log.error("Failed to send to DLQ for event id={}. cause={}", event.getId(), ex.getMessage());
                }
            }
        }
    }
}
