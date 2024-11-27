package com.workshop.passenger.domain.model.aggregates;

import com.workshop.passenger.domain.model.entities.Trip;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Passenger")
public class Passenger {

    @Id
    private ObjectId id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Preferred payment method is required")
    private String preferredPaymentMethod;

    @NotNull(message = "Registration date is required")
    private LocalDateTime registeredAt;

    @NotEmpty(message = "Passenger must have at least one trip")
    @Valid
    private List<Trip> trips;

}