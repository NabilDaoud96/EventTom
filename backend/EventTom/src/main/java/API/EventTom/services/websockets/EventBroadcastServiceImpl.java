package API.EventTom.services.websockets;

import API.EventTom.DTO.EventDTO;
import API.EventTom.mappers.StandardDTOMapper;
import API.EventTom.models.Event;
import API.EventTom.services.websockets.interfaces.IEventBroadcastService;
import API.EventTom.services.websockets.interfaces.IWebsocketBroadcastStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventBroadcastServiceImpl implements IEventBroadcastService {
    private final StandardDTOMapper standardDTOMapper;
    private final DestinationBuilder destinationBuilder;
    private final IWebsocketBroadcastStrategy broadcaster;

    public void broadcastEventCreation(EventDTO event) {
        String destination = destinationBuilder.buildNewEventDestination();
        broadcaster.broadcast(destination, event);
    }

    @Override
    public void broadcastTicketSale(Event event, boolean notifyAllUsers) {
        EventDTO eventDTO = standardDTOMapper.mapEventToEventDTO(event);

        if (notifyAllUsers) {
            broadcaster.broadcast(destinationBuilder.buildTicketSaleDestination(event.getId()), eventDTO);
        }

        event.getManagers().forEach(manager ->
                broadcaster.broadcast(destinationBuilder.buildManagerEventDestination(manager.getId(), event.getId()), eventDTO)
        );
    }
}
