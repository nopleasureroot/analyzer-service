package com.microservices.analyzer.module.producer;

import com.microservices.analyzer.model.kafka.DataProducedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, DataProducedEvent> kafkaTemplate;
    @Value("${spring.kafka.producer-default-topic}")
    private String topic;

    public void execute(DataProducedEvent event) {
        kafkaTemplate.send(topic, event);
        log.info(event.toString());
    }
}
