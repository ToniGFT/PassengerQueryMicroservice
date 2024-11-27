package com.workshop.passenger.domain.events;

import com.workshop.passenger.domain.model.entities.Trip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerUpdatedEvent {
    private ObjectId id;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private LocalDateTime registeredAt;
    private List<Trip> trips;
}
