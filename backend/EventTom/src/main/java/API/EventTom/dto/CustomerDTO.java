package API.EventTom.dto;

import java.util.List;
public record CustomerDTO(
        String name,
        String email,
        List<VoucherDTO> vouchers,
        List<TicketDTO> tickets
) {}