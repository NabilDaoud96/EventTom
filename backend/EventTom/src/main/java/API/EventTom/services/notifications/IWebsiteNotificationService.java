package API.EventTom.services.notifications;

import API.EventTom.models.Notification;
import java.util.List;

public interface IWebsiteNotificationService extends INotificationService {
    List<Notification> getUnreadNotifications(Long userId);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
}