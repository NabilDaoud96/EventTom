package API.EventTom.services.websockets;

import API.EventTom.services.websockets.interfaces.IDestinationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketIDestinationStrategyImpl implements IDestinationStrategy {
    private static final Map<String, String> DESTINATION_FORMATS = Map.of(
            "USER_NOTIFICATION", "/topic/users/%d/notifications",
            "NEW_EVENT", "/topic/events/new",
            "TICKET_SALE", "/topic/events/tickets/%d",
            "MANAGER_EVENT", "/topic/managers/%d/events/%d"
    );

    @Override
    public String buildDestination(String type, Object... params) {
        String format = DESTINATION_FORMATS.get(type);
        if (format == null) {
            throw new IllegalArgumentException("Unknown destination type: " + type);
        }
        return String.format(format, params);
    }
}
