package com.workshop.passenger.domain.model.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @NotBlank(message = "Trip ID is required")
    private String tripId;

    @NotBlank(message = "Route ID is required")
    private String routeId;

    @NotBlank(message = "Vehicle ID is required")
    private String vehicleId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotBlank(message = "Start stop is required")
    private String startStop;

    @NotBlank(message = "End stop is required")
    private String endStop;

    @Min(value = 0, message = "Fare must be positive")
    private double fare;

}
