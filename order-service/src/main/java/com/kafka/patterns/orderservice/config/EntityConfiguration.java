package com.kafka.patterns.orderservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {
        "com.kafka.patterns.common.domain.entities",
        "com.kafka.patterns.orderservice.entity.domain"
})
public class EntityConfiguration {
}
