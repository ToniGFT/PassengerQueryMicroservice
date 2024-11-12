package com.workshop.passenger.application.controller;


import com.workshop.passenger.application.services.PassengerQueryService;
import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.entities.Trip;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

@ExtendWith(MockitoExtension.class)
public class PassengerQueryControllerTest {

    @Mock
    private PassengerQueryService passengerService;

    @InjectMocks
    private PassengerQueryController passengerQueryController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        this.webTestClient = bindToController(passengerQueryController).build();
    }

    @Test
    public void testGetPassengerById_Success() {
        String passengerId = "60d2f0f8b5a6c5e9c8a71e6f";
        ObjectId objectId = new ObjectId(passengerId);
        Passenger passenger = new Passenger("John Doe", "john.doe@example.com", "123456789", "CreditCard",
                LocalDateTime.now(), List.of(new Trip("trip1", "route1", "vehicle1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "startStop", "endStop", 10.0)));

        when(passengerService.getPassengerById(objectId)).thenReturn(Mono.just(passenger));

        webTestClient.get()
                .uri("/passengers/{id}", passengerId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Passenger.class)
                .isEqualTo(passenger);

        verify(passengerService, times(1)).getPassengerById(objectId);
    }

    @Test
    public void testGetPassengerById_NotFound() {
        String passengerId = "60d2f0f8b5a6c5e9c8a71e6f";
        ObjectId objectId = new ObjectId(passengerId);

        when(passengerService.getPassengerById(objectId))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found")));

        webTestClient.get()
                .uri("/passengers/{id}", passengerId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();

        verify(passengerService, times(1)).getPassengerById(objectId);
    }


    @Test
    public void testGetPassengerTrips_Success() {
        String passengerId = "60d2f0f8b5a6c5e9c8a71e6f";
        ObjectId objectId = new ObjectId(passengerId);
        Trip trip = new Trip("trip1", "route1", "vehicle1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "startStop", "endStop", 10.0);

        when(passengerService.getPassengerTrips(objectId)).thenReturn(Flux.just(trip));

        webTestClient.get()
                .uri("/passengers/{id}/trips", passengerId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Trip.class)
                .hasSize(1)
                .contains(trip);

        verify(passengerService, times(1)).getPassengerTrips(objectId);
    }

    @Test
    public void testGetPassengerTrips_NotFound() {
        String passengerId = "60d2f0f8b5a6c5e9c8a71e6f";
        ObjectId objectId = new ObjectId(passengerId);

        when(passengerService.getPassengerTrips(objectId)).thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/passengers/{id}/trips", passengerId)
                .exchange()
                .expectStatus().isOk();

        verify(passengerService, times(1)).getPassengerTrips(objectId);
    }
}