package com.workshop.passenger.application.services;

import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.entities.Trip;
import com.workshop.passenger.domain.repository.PassengerQueryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

class PassengerQueryServiceImplTest {

    private PassengerQueryRepository passengerRepository;
    private PassengerQueryServiceImpl passengerQueryService;

    @BeforeEach
    void setUp() {
        passengerRepository = Mockito.mock(PassengerQueryRepository.class);
        passengerQueryService = new PassengerQueryServiceImpl(passengerRepository);
    }

    @Test
    void getPassengerById_shouldReturnPassenger() {
        ObjectId passengerId = new ObjectId();
        Passenger passenger = new Passenger("Juan Pérez", "juan.perez@example.com", "+1234567890", "card", null, null);

        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));

        StepVerifier.create(passengerQueryService.getPassengerById(passengerId))
                .expectNext(passenger)
                .verifyComplete();

        Mockito.verify(passengerRepository).findById(passengerId);
    }

    @Test
    void getPassengerById_shouldReturnErrorWhenNotFound() {
        ObjectId passengerId = new ObjectId();
        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Mono.empty());

        StepVerifier.create(passengerQueryService.getPassengerById(passengerId))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException
                        && throwable.getMessage().contains("Passenger not found with ID"))
                .verify();

        Mockito.verify(passengerRepository).findById(passengerId);
    }

    @Test
    void getPassengerTrips_shouldReturnTrips() {
        ObjectId passengerId = new ObjectId();
        Trip trip1 = new Trip("T-1001", "123", "V-456", null, null, "Estación Central", "Plaza Norte", 2.50);
        Trip trip2 = new Trip("T-1002", "123", "V-456", null, null, "Plaza Norte", "Terminal Norte", 2.50);
        List<Trip> trips = Arrays.asList(trip1, trip2);

        Passenger passenger = new Passenger("Juan Pérez", "juan.perez@example.com", "+1234567890", "card", null, trips);
        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));

        StepVerifier.create(passengerQueryService.getPassengerTrips(passengerId))
                .expectNext(trip1)
                .expectNext(trip2)
                .verifyComplete();

        Mockito.verify(passengerRepository).findById(passengerId);
    }

    @Test
    void getPassengerTrips_shouldReturnEmptyWhenNoTrips() {
        ObjectId passengerId = new ObjectId();
        Passenger passenger = new Passenger("Juan Pérez", "juan.perez@example.com", "+1234567890", "card", null, null);
        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));

        StepVerifier.create(passengerQueryService.getPassengerTrips(passengerId))
                .verifyComplete();

        Mockito.verify(passengerRepository).findById(passengerId);
    }

    @Test
    void getPassengerTrips_shouldReturnEmptyWhenTripsIsNull() {
        ObjectId passengerId = new ObjectId();
        Passenger passenger = new Passenger("Juan Pérez", "juan.perez@example.com", "+1234567890", "card", null, null);
        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));

        StepVerifier.create(passengerQueryService.getPassengerTrips(passengerId))
                .verifyComplete();

        Mockito.verify(passengerRepository).findById(passengerId);
    }
}