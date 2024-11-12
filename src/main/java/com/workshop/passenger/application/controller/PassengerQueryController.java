package com.workshop.passenger.application.controller;

import com.workshop.passenger.application.services.PassengerQueryService;
import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.entities.Trip;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/passengers")
public class PassengerQueryController {

    private final PassengerQueryService passengerService;

    @Autowired
    public PassengerQueryController(PassengerQueryService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/{id}")
    public Mono<Passenger> getPassengerById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return passengerService.getPassengerById(objectId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found")));
    }

    @GetMapping("/{id}/trips")
    public Flux<Trip> getPassengerTrips(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return passengerService.getPassengerTrips(objectId);
    }
}
