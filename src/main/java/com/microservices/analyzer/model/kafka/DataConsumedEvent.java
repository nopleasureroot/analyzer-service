package com.microservices.analyzer.model.kafka;

import lombok.Builder;

import java.util.UUID;

public record DataConsumedEvent(
    UUID uuid,
    Product product
) {

    @Builder
    public DataConsumedEvent {
    }

    public record Product(
        String name,
        String imageUrl,
        String link,
        Long price
    ) {
        @Builder
        public Product {
        }
    }
}