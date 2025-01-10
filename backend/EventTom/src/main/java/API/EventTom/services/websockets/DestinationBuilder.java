package API.EventTom.services.websockets;

import org.springframework.stereotype.Component;

@Component
public class DestinationBuilder {
    private static final String USER_DESTINATION_FORMAT = "/topic/users/%d/notifications";
    private static final String NEW_EVENT_DESTINATION = "/topic/events/new";
    private static final String TICKET_SALE_DESTINATION_FORMAT = "/topic/events/tickets/%d";
    private static final String MANAGER_EVENT_DESTINATION_FORMAT = "/topic/managers/%d/events/%d";

    public String buildUserDestination(Long userId) {
        return String.format(USER_DESTINATION_FORMAT, userId);
    }

    public String buildNewEventDestination() {
        return NEW_EVENT_DESTINATION;
    }

    public String buildTicketSaleDestination(Long eventId) {
        return String.format(TICKET_SALE_DESTINATION_FORMAT, eventId);
    }

    public String buildManagerEventDestination(Long managerId, Long eventId) {
        return String.format(MANAGER_EVENT_DESTINATION_FORMAT, managerId, eventId);
    }
}