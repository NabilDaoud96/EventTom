package API.EventTom.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
public record CustomerDTO(
        String name,
        String email,
        List<VoucherDTO> vouchers,
        List<TicketDTO> tickets
) {}