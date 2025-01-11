package API.EventTom.controller.events;
import API.EventTom.dto.EventDTO;
import API.EventTom.dto.request.EventCreateDTO;
import API.EventTom.dto.request.EventUpdateDTO;
import API.EventTom.config.security.AuthenticatedUserId;
import API.EventTom.services.events.IEventCommandService;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@AllArgsConstructor
//sollte api/events/commands sein
@RequestMapping("api/events")
public class EventCommandController {
    private final IEventCommandService eventCommandService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('EVENT_CREATOR')")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreateDTO eventCreateDTO, @Nonnull @AuthenticatedUserId Long userId) {
        return new ResponseEntity<>(eventCommandService.createEvent(eventCreateDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('EVENT_MANAGER') or hasRole('EVENT_CREATOR')")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable long id, @RequestBody EventUpdateDTO eventUpdateDTO, @AuthenticatedUserId Long userId) throws AccessDeniedException {
        return ResponseEntity.ok(eventCommandService.updateEvent(id, eventUpdateDTO, userId));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('EVENT_MANAGER') or hasRole('EVENT_CREATOR')")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id, @AuthenticatedUserId Long userId) throws AccessDeniedException {
        eventCommandService.deleteEvent(id, userId);
        return ResponseEntity.noContent().build();
    }
}