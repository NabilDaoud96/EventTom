package API.EventTom.services.websockets.interfaces;


import API.EventTom.DTO.WebSocketMessageDTO;

public interface IUserBroadcastService {
    void broadcastToUser(Long userId, WebSocketMessageDTO message);
}