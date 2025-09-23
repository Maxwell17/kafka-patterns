package com.example.paymentservice.inbox.listener;

import com.example.paymentservice.inbox.mapper.InboxEventMapper;
import com.example.paymentservice.inbox.repository.InboxRepository;
import com.kafka.patterns.proto.OutboxOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final InboxEventMapper inboxEventMapper;
    private final InboxRepository inboxRepository;

    @KafkaListener(topics = "create-order-topic", groupId = "payments-group")
    public void onMessage(@Payload OutboxOrderEvent event, ConsumerRecord<String,String> record) {

        String messageId = String.join("-", event.getProduct(), String.valueOf(event.getQuantity()));
        log.info("Received message: {}", messageId);
        boolean already = inboxRepository.existsById(messageId);
        if (already) {
            log.warn("Message already exists: {}", messageId);
            return;
        }

        inboxRepository.save(inboxEventMapper.toInboxEvent(event, messageId));
        log.info("Saved message: {}", messageId);
//        // process
//        paymentService.processOrder(payload)
//                .flatMap(v -> inboxRepo.save(new InboxMessage(messageId, record.topic(), OffsetDateTime.now())))
//                .subscribe();
    }
}