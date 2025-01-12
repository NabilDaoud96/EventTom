package API.EventTom.mappers;

import API.EventTom.dto.*;
import API.EventTom.dto.response.UserDTO;
import API.EventTom.models.Notification;
import API.EventTom.models.event.Event;
import API.EventTom.models.event.Ticket;
import API.EventTom.models.event.Voucher;
import API.EventTom.models.user.Customer;
import API.EventTom.models.user.Employee;
import API.EventTom.models.user.Role;
import API.EventTom.models.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StandardDTOMapper {

    public CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        User user = customer.getUser();
        return new CustomerDTO(
                new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()),
                customer.getCustomerNumber()
        );
    }

    public EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        User user = employee.getUser();
        return new EmployeeDTO(
                employee.getId(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                user.getFirstName(),
                user.getLastName(),
                employee.getEmployeeNumber(),
                user.getEmail()
        );
    }

    public EventDTO mapEventToEventDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDateOfEvent(),
                event.getTotalSoldTickets(),
                event.getThresholdValue(),
                event.getAvailableTickets(),
                event.getBasePrice(),
                event.getLocation()
        );
    }

    public VoucherDTO mapVoucherToVoucherDTO(Voucher voucher) {
        return new VoucherDTO(
                voucher.getCustomer().getUser().getId(),
                voucher.getAmount(),
                voucher.getExpirationDate(),
                voucher.isUsed()
        );
    }

    public TicketDTO mapTicketToTicketDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getBasePrice(),
                ticket.getPurchaseDate(),
                ticket.isStatusUsed(),
                ticket.getEvent().getId(),
                ticket.getCustomer().getUser().getId(),
                ticket.getEvent().getTitle(),
                ticket.getEvent().getDateOfEvent(),
                ticket.getEvent().getLocation()
        );
    }
    public NotificationDTO mapNotificationToNotificationDTO(Notification notification) {
        return new NotificationDTO(notification.getId(), notification.getUser().getId(), notification.getCreatedAt(), notification.getMessage());
    }

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}