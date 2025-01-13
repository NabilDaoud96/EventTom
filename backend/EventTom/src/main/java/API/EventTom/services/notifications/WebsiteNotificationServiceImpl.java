package API.EventTom.services.notifications;

import API.EventTom.models.Notification;
import API.EventTom.models.user.User;
import API.EventTom.repositories.NotificationRepository;
import API.EventTom.repositories.UserRepository;
import API.EventTom.services.websockets.interfaces.IUserBroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebsiteNotificationServiceImpl implements IWebsiteNotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final IUserBroadcastService userBroadcastService;

    @Override
    @Transactional
    public void sendNotification(User recipient, String message, String notificationType) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setNotificationType(notificationType);
        userBroadcastService.broadcastToUser(recipient.getId(), notification);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUserAndReadOrderByCreatedAtDesc(user, false);
    }


    @Transactional
    public void markAsRead(Long notificationId, Long userId) {

        // TODO: ADD CHECK IF CORRECT USER READ NOTIFICATION

        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        notificationRepository.markAllAsRead(user);
    }


}