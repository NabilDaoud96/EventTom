package API.EventTom.services.events;


import API.EventTom.DTO.EventDTO;
import org.springframework.data.domain.Page;


import java.util.List;

public interface IEventQueryService {


    Page<EventDTO> getAllEvents(int page, int size, String sortBy, String direction);
    EventDTO getEventById(long id);
    List<EventDTO> getEventsByManagerId(long managerId);
}
