package com.workshop.passenger.infraestructure.messaging.kafka.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.passenger.application.services.kafka.PassengerEventProcessor;
import com.workshop.passenger.domain.events.PassengerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("PASSENGER_CREATED")
public class PassengerCreatedEventProcessor implements EventProcessor<PassengerCreatedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PassengerCreatedEventProcessor.class);

    private final PassengerEventProcessor passengerEventProcessor;
    private final ObjectMapper objectMapper;

    public PassengerCreatedEventProcessor(PassengerEventProcessor passengerEventProcessor, ObjectMapper objectMapper) {
        this.passengerEventProcessor = passengerEventProcessor;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> process(String message) {
        logger.info("Starting to process PASSENGER_CREATED event. Message: {}", message);
        try {
            PassengerCreatedEvent event = objectMapper.readValue(message, PassengerCreatedEvent.class);
            return passengerEventProcessor.processPassengerCreatedEvent(event);
        } catch (Exception e) {
            logger.error("Failed to process PASSENGER_CREATED event. Message: {}, Error: {}", message, e.getMessage(), e);
            return Mono.error(new RuntimeException("Failed to process PASSENGER_CREATED event", e));
        }
    }
}
