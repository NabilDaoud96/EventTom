package API.EventTom.services.notifications;

import API.EventTom.models.User;

public interface INotificationService {
    void sendNotification(User recipient, String message, String notificationType);
}