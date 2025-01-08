package API.EventTom.services.events;

import API.EventTom.DTO.EventDTO;
import API.EventTom.exceptions.RuntimeExceptions.ResourceNotFoundException;
import API.EventTom.mappers.StandardDTOMapper;
import API.EventTom.models.Event;
import API.EventTom.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventQueryServiceImpl implements IEventQueryService {

    EventRepository eventRepository;
    StandardDTOMapper standardDTOMapper;
    @Override
    public Page<EventDTO> getAllEvents(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Event> eventPage = eventRepository.findAll(pageable);

        return eventPage.map(standardDTOMapper::mapEventToEventDTO);
    }

    @Override
    public EventDTO getEventById(long id) {
        return eventRepository.findById(id)
                .map(standardDTOMapper::mapEventToEventDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    @Override
    public List<EventDTO> getEventsByManagerId(long managerId) {
        return eventRepository.findByManagers_Id(managerId).stream()
                .map(standardDTOMapper::mapEventToEventDTO)
                .collect(Collectors.toList());
    }
}
