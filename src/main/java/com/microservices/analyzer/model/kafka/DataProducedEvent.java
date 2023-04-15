package com.microservices.analyzer.model.kafka;

import lombok.Builder;

public record DataProducedEvent(
   DataProducedEvent.Product product
) {

    @Builder
    public DataProducedEvent {
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