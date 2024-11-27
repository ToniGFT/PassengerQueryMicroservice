package com.workshop.passenger.application.services.kafka;

import com.workshop.passenger.domain.events.PassengerCreatedEvent;
import com.workshop.passenger.domain.events.PassengerDeletedEvent;
import com.workshop.passenger.domain.events.PassengerUpdatedEvent;
import reactor.core.publisher.Mono;

public interface PassengerEventProcessor {
    Mono<Void> processPassengerCreatedEvent(PassengerCreatedEvent event);

    Mono<Void> processPassengerDeletedEvent(PassengerDeletedEvent event);

    Mono<Void> processPassengerUpdatedEvent(PassengerUpdatedEvent event);
}
