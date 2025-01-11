package API.EventTom.DTO.request;


import API.EventTom.models.Event;
import API.EventTom.models.Voucher;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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