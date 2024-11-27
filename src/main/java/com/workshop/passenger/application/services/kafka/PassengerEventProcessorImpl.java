package com.workshop.passenger.application.service.kafka;

import com.workshop.passenger.application.services.kafka.PassengerEventProcessor;
import com.workshop.passenger.domain.events.PassengerCreatedEvent;
import com.workshop.passenger.domain.events.PassengerDeletedEvent;
import com.workshop.passenger.domain.events.PassengerUpdatedEvent;
import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.mapper.PassengerEventMapper;
import com.workshop.passenger.infraestructure.repository.PassengerCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PassengerEventProcessorImpl implements PassengerEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PassengerEventProcessorImpl.class);

    private final PassengerCommandRepository passengerCommandRepository;

    public PassengerEventProcessorImpl(PassengerCommandRepository passengerCommandRepository) {
        this.passengerCommandRepository = passengerCommandRepository;
    }

    @Override
    public Mono<Void> processPassengerCreatedEvent(PassengerCreatedEvent event) {
        logger.info("Processing PassengerCreatedEvent: {}", event);
        Passenger passenger = PassengerEventMapper.toPassenger(event);
        logger.debug("Mapped PassengerCreatedEvent to Passenger: {}", passenger);

        return passengerCommandRepository.save(passenger)
                .doOnSuccess(savedPassenger -> logger.info("Successfully saved passenger to database: {}", savedPassenger))
                .doOnError(error -> logger.error("Failed to save passenger to database: {}", error.getMessage(), error))
                .then();
    }

    @Override
    public Mono<Void> processPassengerDeletedEvent(PassengerDeletedEvent event) {
        logger.info("Processing PassengerDeletedEvent: {}", event);
        return passengerCommandRepository.deleteById(event.getId())
                .doOnSuccess(unused -> logger.info("Successfully deleted passenger with ID: {}", event.getId()))
                .doOnError(error -> logger.error("Failed to delete passenger with ID: {}", event.getId(), error))
                .then();
    }

    @Override
    public Mono<Void> processPassengerUpdatedEvent(PassengerUpdatedEvent event) {
        logger.info("Processing PassengerUpdatedEvent: {}", event);
        return passengerCommandRepository.findById(event.getId())
                .doOnSubscribe(subscription -> logger.debug("Fetching passenger with ID: {}", event.getId()))
                .doOnNext(existingPassenger -> logger.debug("Found existing passenger: {}", existingPassenger))
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("No passenger found with ID: {}", event.getId());
                    return Mono.empty();
                }))
                .flatMap(existingPassenger -> {
                    Passenger updatedPassenger = PassengerEventMapper.toPassenger(event, existingPassenger);
                    logger.debug("Mapped PassengerUpdatedEvent to updated Passenger: {}", updatedPassenger);
                    return passengerCommandRepository.save(updatedPassenger)
                            .doOnSuccess(savedPassenger -> logger.info("Successfully updated passenger in database: {}", savedPassenger));
                })
                .doOnError(error -> logger.error("Failed to update passenger with ID: {}", event.getId(), error))
                .then();
    }
}
