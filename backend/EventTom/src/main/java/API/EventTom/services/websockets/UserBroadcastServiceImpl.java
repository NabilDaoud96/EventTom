package API.EventTom.services.websockets;

import API.EventTom.config.security.AuthenticatedUserId;
import API.EventTom.dto.WebSocketMessageDTO;
import API.EventTom.models.user.UserDetailsImpl;
import API.EventTom.services.websockets.interfaces.IDestinationStrategy;
import API.EventTom.services.websockets.interfaces.IUserBroadcastService;
import API.EventTom.services.websockets.interfaces.IWebsocketBroadcastStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBroadcastServiceImpl implements IUserBroadcastService {
    private final IDestinationStrategy destinationStrategy;
    private final IWebsocketBroadcastStrategy broadcaster;

    @Override
    public void broadcastToUser(Long userId, WebSocketMessageDTO message) {
        String destination = destinationStrategy.buildDestination("USER_NOTIFICATION", userId);
        System.out.println("Sending to: " + destination);
        broadcaster.broadcastToUser(userId.toString(), destination, message);
    }
}
