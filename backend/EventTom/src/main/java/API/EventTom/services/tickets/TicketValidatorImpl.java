package API.EventTom.services.tickets;
import API.EventTom.DTO.request.PurchaseTicketDTO;
import API.EventTom.exceptions.validationExceptions.EventDatePassedException;
import API.EventTom.exceptions.validationExceptions.InvalidPurchaseAmountException;
import API.EventTom.exceptions.validationExceptions.InsufficientTicketsException;
import API.EventTom.models.Event;
import API.EventTom.services.tickets.interfaces.ITicketValidator;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TicketValidatorImpl implements ITicketValidator {
    private static final int MAX_TICKETS_PER_PURCHASE = 10;

    @Override
    public void validatePurchaseRequest(Event event, PurchaseTicketDTO dto) {
        validateTicketAvailability(event, dto.amount());
        validateEventDate(event);
        validatePurchaseAmount(dto.amount());
    }

    @Override
    public void validateTicketAvailability(Event event, int requestedAmount) {
        if (event.getAvailableTickets() < requestedAmount) {
            throw new InsufficientTicketsException(
                    String.format("Not enough tickets available. Requested: %d, Available: %d",
                            requestedAmount, event.getAvailableTickets())
            );
        }
    }

    @Override
    public void validateEventDate(Event event) {
        if (event.getDateOfEvent().isBefore(LocalDateTime.now())) {
            throw new EventDatePassedException("Cannot purchase tickets for past events");
        }
    }

    @Override
    public void validatePurchaseAmount(int amount) {
        if (amount <= 0) {
            throw new InvalidPurchaseAmountException("Purchase amount must be greater than zero");
        }
        if (amount > MAX_TICKETS_PER_PURCHASE) {
            throw new InvalidPurchaseAmountException(
                    String.format("Cannot purchase more than %d tickets at once", MAX_TICKETS_PER_PURCHASE)
            );
        }
    }
}