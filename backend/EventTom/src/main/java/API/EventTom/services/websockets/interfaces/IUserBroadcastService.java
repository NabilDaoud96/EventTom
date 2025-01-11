package API.EventTom.services.websockets.interfaces;


import API.EventTom.dto.WebSocketMessageDTO;

public interface IUserBroadcastService {
    void broadcastToUser(Long userId, WebSocketMessageDTO message);
}