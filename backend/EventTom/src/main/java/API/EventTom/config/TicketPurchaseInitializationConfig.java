package API.EventTom.config;

import API.EventTom.DTO.request.PurchaseTicketDTO;
import API.EventTom.models.Customer;
import API.EventTom.models.Event;
import API.EventTom.repositories.CustomerRepository;
import API.EventTom.repositories.EventRepository;
import API.EventTom.repositories.VoucherRepository;
import API.EventTom.services.tickets.TicketPurchaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class TicketPurchaseInitializationConfig {

    private final TicketPurchaseServiceImpl ticketPurchaseService;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final VoucherRepository voucherRepository;
    private final Random random = new Random();

    @Bean
    @Order(4)
    public CommandLineRunner initializeTicketPurchases() {
        return args -> {
            List<Customer> customers = customerRepository.findAll();
            List<Event> events = eventRepository.findAll();

            if (customers.isEmpty() || events.isEmpty()) {
                System.err.println("No customers or events found for ticket initialization");
                return;
            }

            simulateRegularPurchases(customers, events);
            simulateVoucherPurchases(customers, events);
            simulateBulkPurchases(customers, events);
        };
    }

    private void simulateRegularPurchases(List<Customer> customers, List<Event> events) {
        try {
            for (Customer customer : customers) {
                Event randomEvent = events.get(random.nextInt(events.size()));

                PurchaseTicketDTO purchaseDTO = new PurchaseTicketDTO();
                purchaseDTO.setEventId(randomEvent.getId());
                purchaseDTO.setAmount(1 + random.nextInt(3));
                purchaseDTO.setVoucherCodes(new ArrayList<>());

                ticketPurchaseService.purchaseTicket(purchaseDTO, customer.getUser().getId());
            }
        } catch (Exception e) {
            System.err.println("Failed to simulate regular purchases: " + e.getMessage());
        }
    }

    private void simulateVoucherPurchases(List<Customer> customers, List<Event> events) {
        try {
            var vouchers = voucherRepository.findAll();
            if (!vouchers.isEmpty()) {
                for (int i = 0; i < 3; i++) { // Create 3 purchases with vouchers
                    Customer randomCustomer = customers.get(random.nextInt(customers.size()));
                    Event randomEvent = events.get(random.nextInt(events.size()));
                    String randomVoucherCode = vouchers.get(random.nextInt(vouchers.size())).getCode();

                    PurchaseTicketDTO purchaseDTO = new PurchaseTicketDTO();
                    purchaseDTO.setEventId(randomEvent.getId());
                    purchaseDTO.setAmount(2);
                    purchaseDTO.setVoucherCodes(List.of(randomVoucherCode));

                    ticketPurchaseService.purchaseTicket(purchaseDTO, randomCustomer.getUser().getId());
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to simulate voucher purchases: " + e.getMessage());
        }
    }

    private void simulateBulkPurchases(List<Customer> customers, List<Event> events) {
        try {
            for (Event event : events) {
                Customer randomCustomer = customers.get(random.nextInt(customers.size()));

                PurchaseTicketDTO purchaseDTO = new PurchaseTicketDTO();
                purchaseDTO.setEventId(event.getId());
                purchaseDTO.setAmount(5 + random.nextInt(6));
                purchaseDTO.setVoucherCodes(new ArrayList<>());

                ticketPurchaseService.purchaseTicket(purchaseDTO, randomCustomer.getUser().getId());
            }
        } catch (Exception e) {
            System.err.println("Failed to simulate bulk purchases: " + e.getMessage());
        }
    }
}