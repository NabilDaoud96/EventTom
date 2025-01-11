package API.EventTom.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketDTO(
        BigDecimal finalPrice,
        LocalDateTime purchaseDate,
        boolean statusUsed,
        long eventId,
        long customerId
) {}