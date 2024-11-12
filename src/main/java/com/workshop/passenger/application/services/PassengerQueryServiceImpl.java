package com.workshop.passenger.application.services;

import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.entities.Trip;
import com.workshop.passenger.domain.repository.PassengerQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PassengerQueryServiceImpl implements PassengerQueryService {

    private final PassengerQueryRepository passengerRepository;

    public PassengerQueryServiceImpl(PassengerQueryRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Mono<Passenger> getPassengerById(ObjectId id) {
        return passengerRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Passenger not found with ID: " + id)));
    }

    @Override
    public Flux<Trip> getPassengerTrips(ObjectId id) {
        return passengerRepository.findById(id)
                .flatMapMany(passenger -> Optional.ofNullable(passenger.getTrips())
                        .map(Flux::fromIterable)
                        .orElseGet(Flux::empty));
    }
}
