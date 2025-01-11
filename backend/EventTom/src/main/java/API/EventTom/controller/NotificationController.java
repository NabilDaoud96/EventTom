package API.EventTom.controller;

import API.EventTom.config.security.AuthenticatedUserId;
import API.EventTom.dto.NotificationDTO;
import API.EventTom.models.Notification;
import API.EventTom.services.notifications.INotificationQueryService;
import API.EventTom.services.notifications.IWebsiteNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final IWebsiteNotificationService notificationService;
    private final INotificationQueryService notificationQueryService;

    @GetMapping()
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@AuthenticatedUserId Long userId) {
        return ResponseEntity.ok(notificationQueryService.findAllByUserId(userId));
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@AuthenticatedUserId Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark-all-read")
    public ResponseEntity<Void> markAllAsRead(@AuthenticatedUserId Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }
}