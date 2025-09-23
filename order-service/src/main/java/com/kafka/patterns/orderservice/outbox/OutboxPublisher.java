package com.kafka.patterns.orderservice.outbox;

import com.kafka.patterns.common.mapper.OrderEventMapper;
import com.kafka.patterns.orderservice.entity.domain.OutboxEventEntity;
import com.kafka.patterns.proto.OutboxOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OrderEventMapper orderEventMapper;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, OutboxOrderEvent> kafkaTemplate;

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void publish() {
        List<OutboxEventEntity> events = outboxRepository.findByPublishedIsFalseOrderByCreatedAt();
        for (OutboxEventEntity event : events) {
            try {
                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), orderEventMapper.toProto(event.getPayload())).get();
                log.info("Published outbox event id={} topic={}", event.getId(), event.getTopic());
            } catch (Exception e) {
                log.error("Failed to publish event id={}, sending to DLQ. cause={}", event.getId(), e.getMessage());
                try {
                    kafkaTemplate.send(event.getTopic() + "-dlq", event.getAggregateId(), orderEventMapper.toProto(event.getPayload())).get();
                } catch (Exception ex) {
                    log.error("Failed to send to DLQ for event id={}. cause={}", event.getId(), ex.getMessage());
                }
            } finally {
                outboxRepository.updatePublishedStatus(event);
            }
        }
    }
}
