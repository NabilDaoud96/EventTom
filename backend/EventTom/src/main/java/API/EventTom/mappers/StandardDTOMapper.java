package API.EventTom.mappers;

import API.EventTom.DTO.*;
import API.EventTom.models.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StandardDTOMapper {

    public CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        User user = customer.getUser();
        return new CustomerDTO(
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                customer.getVouchers().stream()
                        .map(this::mapVoucherToVoucherDTO)
                        .collect(Collectors.toList()),
                customer.getTickets().stream()
                        .map(this::mapTicketToTicketDTO)
                        .collect(Collectors.toList())
        );
    }

    public EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        User user = employee.getUser();
        return new EmployeeDTO(
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                user.getFirstName() + " " + user.getLastName(),
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
                voucher.getExpirationDate()
        );
    }

    public TicketDTO mapTicketToTicketDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getBasePrice(),
                ticket.getPurchaseDate(),
                ticket.isStatusUsed(),
                ticket.getEvent().getId(),
                ticket.getCustomer().getUser().getId()
        );
    }
}