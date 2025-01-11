package API.EventTom.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PurchaseTicketDTO(
        @NotNull(message = "Event ID is required")
        Long eventId,

        @Positive(message = "Amount must be positive")
        @Max(value = 10, message = "Cannot purchase more than 10 tickets at once")
        int amount,

        @Size(max = 5, message = "Cannot use more than 5 vouchers per purchase")
        List<String> voucherCodes
) {}