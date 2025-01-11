package API.EventTom.repositories;

import API.EventTom.models.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByManagers_Id(Long managerId);
    Page<Event> findByManagers_Id(Long managerId, Pageable pageable);

}