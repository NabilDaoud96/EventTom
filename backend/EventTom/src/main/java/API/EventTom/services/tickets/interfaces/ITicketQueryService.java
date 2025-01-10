package API.EventTom.services.tickets.interfaces;

import API.EventTom.DTO.TicketDTO;

import java.util.List;

public interface ITicketQueryService {

    List<TicketDTO> getTicketsByEventId(Long id);
    TicketDTO getTicketById(long ticketId);
    List<TicketDTO> getTicketsByUserId(Long userId);
    List<TicketDTO> getTicketsByCustomerNumber(String customerNumber);
}