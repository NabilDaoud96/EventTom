package API.EventTom.services.websockets;

import API.EventTom.dto.WebSocketMessageDTO;
import API.EventTom.services.websockets.interfaces.IDestinationStrategy;
import API.EventTom.services.websockets.interfaces.IUserBroadcastService;
import API.EventTom.services.websockets.interfaces.IWebsocketBroadcastStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBroadcastServiceImpl implements IUserBroadcastService {
    private final IDestinationStrategy destinationStrategy;
    private final IWebsocketBroadcastStrategy broadcaster;

    @Override
    public void broadcastToUser(Long userId, WebSocketMessageDTO message) {
        String destination = destinationStrategy.buildDestination("USER_NOTIFICATION", userId);
        broadcaster.broadcast(destination, message);
    }
}
