package API.EventTom.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventUpdateDTO(
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        @Future(message = "Event date must be in the future")
        LocalDateTime dateOfEvent,

        @Positive(message = "Total tickets must be positive")
        @Max(value = 100000, message = "Total tickets cannot exceed 100,000")
        int totalTickets,

        @PositiveOrZero(message = "Threshold value must be zero or positive")
        int thresholdValue,

        @Positive(message = "Base price must be positive")
        BigDecimal basePrice
) {}