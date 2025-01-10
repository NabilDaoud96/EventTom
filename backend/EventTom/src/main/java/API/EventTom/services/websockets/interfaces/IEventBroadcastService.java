package API.EventTom.services.websockets.interfaces;

import API.EventTom.DTO.EventDTO;
import API.EventTom.models.Event;

public interface IEventBroadcastService {
    void broadcastEventCreation(EventDTO eventDTO);
    void broadcastTicketSale(Event event, boolean notifyAllUsers);
}