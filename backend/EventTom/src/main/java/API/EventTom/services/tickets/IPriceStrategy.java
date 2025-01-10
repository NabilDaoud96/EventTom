package API.EventTom.services.tickets;

import API.EventTom.models.Event;

import java.math.BigDecimal;

public interface IPriceStrategy {
    BigDecimal calculatePrice(Event event, BigDecimal basePrice);
}