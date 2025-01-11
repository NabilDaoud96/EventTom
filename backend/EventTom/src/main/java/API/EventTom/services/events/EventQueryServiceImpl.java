package API.EventTom.services.events;

import API.EventTom.dto.EventDTO;
import API.EventTom.mappers.StandardDTOMapper;
import API.EventTom.models.event.Event;
import API.EventTom.repositories.EventRepository;
import API.EventTom.services.common.BaseQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class EventQueryServiceImpl extends BaseQueryService<Event, EventDTO, Long>
        implements IEventQueryService {
    private final EventRepository eventRepository;

    public EventQueryServiceImpl(
            EventRepository eventRepository,
            StandardDTOMapper standardDTOMapper, EventRepository eventRepository1) {
        super(eventRepository,
                standardDTOMapper,
                standardDTOMapper::mapEventToEventDTO,
                "Event");
        this.eventRepository = eventRepository1;
    }

    @Override
    public Page<EventDTO> getAllEvents(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return getAll(pageable);
    }


    @Override
    public List<EventDTO> findAllByUserId(Long userId) {
        List<Event> events = eventRepository.findByManagers_Id(userId);
        return events.stream()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventDTO> findAllByUserId(Long userId, Pageable pageable) {
        Page<Event> eventPage = eventRepository.findByManagers_Id(userId, pageable);
        return eventPage.map(mapperFunction);
    }

}