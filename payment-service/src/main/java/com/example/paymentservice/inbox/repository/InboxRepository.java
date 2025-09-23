package com.example.paymentservice.inbox.repository;

import com.example.paymentservice.inbox.domain.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboxRepository extends JpaRepository<InboxEvent, String> {
}
