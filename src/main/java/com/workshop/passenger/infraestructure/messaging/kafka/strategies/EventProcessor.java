package com.workshop.passenger.infraestructure.messaging.kafka.strategies;

import reactor.core.publisher.Mono;

public interface EventProcessor<T> {
    Mono<Void> process(String message);
}
