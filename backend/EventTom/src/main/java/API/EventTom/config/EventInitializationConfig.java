package API.EventTom.config;

import API.EventTom.DTO.request.EventCreateDTO;
import API.EventTom.services.events.EventCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class EventInitializationConfig {

    private final EventCommandServiceImpl eventCommandService;

    @Bean
    public CommandLineRunner initializeEvents() {
        return args -> {
            createSampleEvent(
                    "Summer Music Festival",
                    "Central Park",
                    LocalDateTime.now().plusMonths(2),
                    1000,
                    100,
                    new BigDecimal("49.99"),
                    Arrays.asList(1L, 2L)
            );

            createSampleEvent(
                    "Tech Conference 2025",
                    "Convention Center",
                    LocalDateTime.now().plusMonths(3),
                    500,
                    50,
                    new BigDecimal("199.99"),
                    Arrays.asList(2L, 3L)  // Example manager IDs
            );

            createSampleEvent(
                    "Food & Wine Expo",
                    "City Hall",
                    LocalDateTime.now().plusMonths(1),
                    300,
                    30,
                    new BigDecimal("79.99"),
                    Arrays.asList(1L, 3L)  // Example manager IDs
            );
        };
    }

    private void createSampleEvent(
            String title,
            String location,
            LocalDateTime dateOfEvent,
            int totalTickets,
            int thresholdValue,
            BigDecimal basePrice,
            List<Long> managerIds
    ) {
        EventCreateDTO eventCreateDTO = new EventCreateDTO();
        eventCreateDTO.setTitle(title);
        eventCreateDTO.setLocation(location);
        eventCreateDTO.setDateOfEvent(dateOfEvent);
        eventCreateDTO.setTotalTickets(totalTickets);
        eventCreateDTO.setThresholdValue(thresholdValue);
        eventCreateDTO.setBasePrice(basePrice);
        eventCreateDTO.setManagerIds(managerIds);

        try {
            // Using ID 1 as the default creator ID - adjust as needed
            eventCommandService.createEvent(eventCreateDTO, 1L);
        } catch (Exception e) {
            // Log the error but don't prevent application startup
            System.err.println("Failed to create sample event: " + title);
            e.printStackTrace();
        }
    }
}