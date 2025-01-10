package API.EventTom.controller;

import API.EventTom.config.AuthenticatedUserId;
import API.EventTom.models.Notification;
import API.EventTom.services.notifications.INotificationService;
import API.EventTom.services.notifications.IWebsiteNotificationService;
import API.EventTom.services.notifications.WebsiteNotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final IWebsiteNotificationService notificationService;

    @GetMapping()
    public ResponseEntity<List<Notification>> getAllNotifications(@AuthenticatedUserId Long userId) {
        return ResponseEntity.ok(notificationService.getAllNotifications(userId));
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