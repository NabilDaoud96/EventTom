package API.EventTom.controller.tickets;

import API.EventTom.DTO.TicketDTO;
import API.EventTom.config.AuthenticatedUserId;
import API.EventTom.services.tickets.interfaces.ITicketQueryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/tickets/query")
@RequiredArgsConstructor
public class TicketQueryController {
    private final ITicketQueryService ticketQueryService;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TicketDTO>> getEventTickets(@PathVariable Long eventId) {
        List<TicketDTO> tickets = ticketQueryService.getTicketsByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TicketDTO>> getLoggedInUserTickets(@AuthenticatedUserId Long userId) {
        List<TicketDTO> tickets = ticketQueryService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/event/{eventId}/user")
    public ResponseEntity<List<TicketDTO>> getLoggedInUserTicketsByEvent(
            @AuthenticatedUserId Long userId,
            @PathVariable Long eventId) {
        List<TicketDTO> tickets = ticketQueryService.getTicketsByUserIdAndEventId(userId, eventId);
        return ResponseEntity.ok(tickets);
    }
    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<List<TicketDTO>> getCustomerTickets(@PathVariable String customerNumber) {
        List<TicketDTO> tickets = ticketQueryService.getTicketsByCustomerNumber(customerNumber);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable long id) {
        TicketDTO ticketDTO = ticketQueryService.getTicketById(id);
        return ResponseEntity.ok(ticketDTO);
    }
}