package com.workshop.passenger.domain.model.mapper;

import com.workshop.passenger.domain.events.PassengerCreatedEvent;
import com.workshop.passenger.domain.events.PassengerUpdatedEvent;
import com.workshop.passenger.domain.model.aggregates.Passenger;
import com.workshop.passenger.domain.model.mapper.congif.PassengerEventMapperConfig;
import org.modelmapper.ModelMapper;

public class PassengerEventMapper {

    private static final ModelMapper modelMapper = PassengerEventMapperConfig.getModelMapper();

    private PassengerEventMapper() {
    }

    public static Passenger toPassenger(PassengerCreatedEvent event) {
        return modelMapper.map(event, Passenger.class);
    }

    public static Passenger toPassenger(PassengerUpdatedEvent event, Passenger existingPassenger) {
        modelMapper.map(event, existingPassenger);
        return existingPassenger;
    }
}
