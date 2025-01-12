package API.EventTom.services.events;


import API.EventTom.dto.EventDTO;
import API.EventTom.services.common.IBaseQueryService;
import org.springframework.data.domain.Page;


import java.util.List;

public interface IEventQueryService extends IBaseQueryService<EventDTO, Long> {
    Page<EventDTO> getAllEvents(int page, int size, String sortBy, String direction);
    EventDTO getByIdWithManagerCheck(Long id, Long userId);
}
