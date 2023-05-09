package com.microservices.analyzer.module;

import com.microservices.analyzer.db.TargetRepository;
import com.microservices.analyzer.mapper.TargetMapper;
import com.microservices.analyzer.model.kafka.DataConsumedEvent;
import com.microservices.analyzer.model.kafka.DataProducedEvent;
import com.microservices.analyzer.module.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalyzerProcessor {
    private final TargetRepository targetRepository;
    private final Producer producer;
    private final TargetMapper targetMapper;

    @KafkaListener(topics = "${spring.kafka.consumer-default-topic}", groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaMessageContainerFactory")
    public void analyze(@Payload DataConsumedEvent event) {
        targetRepository.findByUuid(event.uuid())
            .map(target -> target.getRuleValue() > event.product().price())
            .ifPresent(state -> {
                if (state) {
                    producer.execute(DataProducedEvent.builder()
                        .product(DataProducedEvent.Product.builder()
                            .imageUrl(event.product().imageUrl())
                            .link(event.product().link())
                            .name(event.product().name())
                            .price(event.product().price())
                            .build())
                        .build());
                targetRepository.save(targetMapper.updateEntity(targetRepository.findByUuid(event.uuid()).get(),"STOPPED"));
                }
            });
    }
}
