package API.EventTom.services.websockets;

import API.EventTom.DTO.WebSocketMessageDTO;
import API.EventTom.services.websockets.interfaces.IUserBroadcastService;
import API.EventTom.services.websockets.interfaces.IWebsocketBroadcastStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBroadcastServiceImpl implements IUserBroadcastService {
    private final DestinationBuilder destinationBuilder;
    private final IWebsocketBroadcastStrategy broadcaster;

    @Override
    public void broadcastToUser(Long userId, WebSocketMessageDTO message) {
        broadcaster.broadcast(destinationBuilder.buildUserDestination(userId), message);
    }
}
