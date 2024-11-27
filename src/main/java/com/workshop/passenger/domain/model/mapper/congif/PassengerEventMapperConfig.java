package com.workshop.passenger.domain.model.mapper.congif;

import com.workshop.passenger.domain.events.PassengerCreatedEvent;
import com.workshop.passenger.domain.events.PassengerUpdatedEvent;
import com.workshop.passenger.domain.model.aggregates.Passenger;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class PassengerEventMapperConfig {

    @Getter
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.addMappings(new PropertyMap<PassengerCreatedEvent, Passenger>() {
            @Override
            protected void configure() {
                map(source.getId(), destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<PassengerUpdatedEvent, Passenger>() {
            @Override
            protected void configure() {
                map(source.getId(), destination.getId());
            }
        });
    }

    private PassengerEventMapperConfig() {
    }
}
