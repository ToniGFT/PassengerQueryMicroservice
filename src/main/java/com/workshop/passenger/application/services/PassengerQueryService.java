package com.workshop.passenger.application.services;

import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.entities.Trip;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassengerQueryService {
    Mono<Passenger> getPassengerById(ObjectId id);
    Flux<Trip> getPassengerTrips(ObjectId id);
}
