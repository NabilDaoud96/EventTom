package API.EventTom.services.websockets.interfaces;

public interface IWebsocketBroadcastStrategy {
    void broadcast(String destination, Object payload);
}
