package com.workshop.passenger.infraestructure.messaging.kafka.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.passenger.application.services.kafka.PassengerEventProcessor;
import com.workshop.passenger.domain.events.PassengerUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("PASSENGER_UPDATED")
public class PassengerUpdatedEventProcessor implements EventProcessor<PassengerUpdatedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PassengerUpdatedEventProcessor.class);

    private final PassengerEventProcessor passengerEventProcessor;
    private final ObjectMapper objectMapper;

    public PassengerUpdatedEventProcessor(PassengerEventProcessor passengerEventProcessor, ObjectMapper objectMapper) {
        this.passengerEventProcessor = passengerEventProcessor;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> process(String message) {
        logger.info("Starting to process PASSENGER_UPDATED event. Message: {}", message);
        try {
            PassengerUpdatedEvent event = objectMapper.readValue(message, PassengerUpdatedEvent.class);
            return passengerEventProcessor.processPassengerUpdatedEvent(event);
        } catch (Exception e) {
            logger.error("Failed to process PASSENGER_UPDATED event. Message: {}, Error: {}", message, e.getMessage(), e);
            return Mono.error(new RuntimeException("Failed to process PASSENGER_UPDATED event", e));
        }
    }
}
