package com.microservices.analyzer.config;

import com.microservices.analyzer.model.kafka.DataConsumedEvent;
import com.microservices.analyzer.model.kafka.DataProducedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final KafkaProperty kafkaProperty;
    @Bean
    public ConsumerFactory<String, DataConsumedEvent> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        JsonDeserializer<DataConsumedEvent> deserializer = new JsonDeserializer<>(DataConsumedEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperty.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperty.getGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(
            config, new StringDeserializer(),
            deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DataConsumedEvent> kafkaMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DataConsumedEvent> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ProducerFactory<String, DataProducedEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            kafkaProperty.getBootstrapServers());
        configProps.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);
        configProps.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, DataProducedEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
