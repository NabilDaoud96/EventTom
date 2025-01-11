package API.EventTom.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VoucherDTO(
        long customerId,
        BigDecimal amount,
        LocalDateTime ticketValidUntil
) {}