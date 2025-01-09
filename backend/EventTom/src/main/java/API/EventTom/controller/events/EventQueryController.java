package API.EventTom.controller.events;

import API.EventTom.DTO.EventDTO;
import API.EventTom.config.AuthenticatedUserId;
import API.EventTom.services.events.IEventQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/events")
public class EventQueryController {
    private final IEventQueryService eventQueryService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(eventQueryService.getAllEvents(page, size, sortBy, direction));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventQueryService.getEventById(id));
    }

    @GetMapping("/manager")
    public ResponseEntity<List<EventDTO>> getEventsByManager(@AuthenticatedUserId Long userId) {
        return ResponseEntity.ok(eventQueryService.getEventsByManagerId(userId));
    }
}
