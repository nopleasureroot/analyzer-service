package com.microservices.analyzer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaProperty {
    @NotNull private String bootstrapServers;
    @NotNull private String groupId;
}
