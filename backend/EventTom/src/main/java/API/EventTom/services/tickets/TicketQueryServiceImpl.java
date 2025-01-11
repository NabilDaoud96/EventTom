package API.EventTom.services.tickets;

import API.EventTom.DTO.TicketDTO;
import API.EventTom.mappers.StandardDTOMapper;
import API.EventTom.models.Ticket;
import API.EventTom.repositories.CustomerRepository;
import API.EventTom.repositories.EventRepository;
import API.EventTom.repositories.TicketRepository;
import API.EventTom.services.tickets.interfaces.ITicketQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketQueryServiceImpl implements ITicketQueryService {

    TicketRepository ticketRepository;
    StandardDTOMapper standardDTOMapper;
    EventRepository eventRepository;
    CustomerRepository customerRepository;


    @Override
    public List<TicketDTO> getTicketsByEventId(Long id) {
        List<Ticket> tickets = ticketRepository.findAllByEventId(id);
        return tickets.stream()
                .map(standardDTOMapper::mapTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findAllByUserId(userId);
        return tickets.stream()
                .map(standardDTOMapper::mapTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getTicketsByCustomerNumber(String customerNumber) {
        List<Ticket> tickets = ticketRepository.findAllTicketsByCustomerNumber(customerNumber);
        return tickets.stream()
                .map(standardDTOMapper::mapTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getTicketsByUserIdAndEventId(Long userId, Long eventId) {
        List<Ticket> tickets = ticketRepository.findAllByUserIdAndEventId(userId, eventId);
        return tickets.stream()
                .map(standardDTOMapper::mapTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getTicketById(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new RuntimeException("Ticket for ID could not be found"));
        return standardDTOMapper.mapTicketToTicketDTO(ticket);
    }

}
