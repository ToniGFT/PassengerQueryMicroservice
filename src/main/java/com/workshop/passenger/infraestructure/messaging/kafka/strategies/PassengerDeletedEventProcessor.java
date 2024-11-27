package com.workshop.passenger.infraestructure.messaging.kafka.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.passenger.application.services.kafka.PassengerEventProcessor;
import com.workshop.passenger.domain.events.PassengerDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("PASSENGER_DELETED")
public class PassengerDeletedEventProcessor implements EventProcessor<PassengerDeletedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PassengerDeletedEventProcessor.class);

    private final PassengerEventProcessor passengerEventProcessor;
    private final ObjectMapper objectMapper;

    public PassengerDeletedEventProcessor(PassengerEventProcessor passengerEventProcessor, ObjectMapper objectMapper) {
        this.passengerEventProcessor = passengerEventProcessor;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> process(String message) {
        logger.info("Starting to process PASSENGER_DELETED event. Message: {}", message);
        try {
            PassengerDeletedEvent event = objectMapper.readValue(message, PassengerDeletedEvent.class);
            return passengerEventProcessor.processPassengerDeletedEvent(event);
        } catch (Exception e) {
            logger.error("Failed to process PASSENGER_DELETED event. Message: {}, Error: {}", message, e.getMessage(), e);
            return Mono.error(new RuntimeException("Failed to process PASSENGER_DELETED event", e));
        }
    }
}
