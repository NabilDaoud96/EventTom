package API.EventTom.services.events;

import API.EventTom.DTO.EventDTO;
import API.EventTom.DTO.request.EventCreateDTO;
import API.EventTom.DTO.request.EventUpdateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

public interface IEventCommandService {
    @Transactional
    EventDTO createEvent(EventCreateDTO eventCreateDTO, Long userId);

    EventDTO updateEvent(long id, EventUpdateDTO eventUpdateDTO, Long userId) throws AccessDeniedException;


    void deleteEvent(long id, Long userId) throws AccessDeniedException;
}