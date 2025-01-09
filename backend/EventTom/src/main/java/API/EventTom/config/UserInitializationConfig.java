package API.EventTom.config;

import API.EventTom.DTO.request.CustomerRegisterRequest;
import API.EventTom.DTO.request.EmployeeRegisterRequest;
import API.EventTom.models.Roles;
import API.EventTom.services.users.RegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.EnumSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class UserInitializationConfig {

    private final RegistrationServiceImpl registrationService;

    @Bean
    @Order(1) // Ensure this runs before event initialization
    public CommandLineRunner initializeUsers() {
        return args -> {
            // Initialize employees first
            createAdminEmployee();
            createEventManagerEmployee();
            createSalesRepEmployee();

            // Then initialize customers
            createSampleCustomers();
        };
    }

    private void createAdminEmployee() {
        Set<Roles> adminRoles = EnumSet.of(
                Roles.ADMINISTRATOR,
                Roles.EVENT_CREATOR,
                Roles.EVENT_MANAGER
        );

        EmployeeRegisterRequest adminRequest = new EmployeeRegisterRequest(
                "admin@eventtom.com",
                "Admin123!",
                "Admin",
                "User",
                adminRoles
        );

        try {
            registrationService.registerEmployee(adminRequest);
        } catch (Exception e) {
            System.err.println("Failed to create admin employee: " + e.getMessage());
        }
    }

    private void createEventManagerEmployee() {
        Set<Roles> managerRoles = EnumSet.of(
                Roles.EVENT_MANAGER,
                Roles.EVENT_CREATOR
        );

        EmployeeRegisterRequest managerRequest = new EmployeeRegisterRequest(
                "manager@eventtom.com",
                "Manager123!",
                "Event",
                "Manager",
                managerRoles
        );

        try {
            registrationService.registerEmployee(managerRequest);
        } catch (Exception e) {
            System.err.println("Failed to create manager employee: " + e.getMessage());
        }
    }

    private void createSalesRepEmployee() {
        Set<Roles> salesRoles = EnumSet.of(
                Roles.SALES_REP
        );

        EmployeeRegisterRequest salesRequest = new EmployeeRegisterRequest(
                "sales@eventtom.com",
                "Sales123!",
                "Sales",
                "Representative",
                salesRoles
        );

        try {
            registrationService.registerEmployee(salesRequest);
        } catch (Exception e) {
            System.err.println("Failed to create sales employee: " + e.getMessage());
        }
    }

    private void createSampleCustomers() {
        CustomerRegisterRequest[] customerRequests = {
                createCustomerRequest(
                        "john.doe@example.com",
                        "Customer123!",
                        "John",
                        "Doe"
                ),
                createCustomerRequest(
                        "jane.smith@example.com",
                        "Customer123!",
                        "Jane",
                        "Smith"
                ),
                createCustomerRequest(
                        "bob.wilson@example.com",
                        "Customer123!",
                        "Bob",
                        "Wilson"
                )
        };

        for (CustomerRegisterRequest request : customerRequests) {
            try {
                registrationService.registerCustomer(request);
            } catch (Exception e) {
                System.err.println("Failed to create customer: " + request.getEmail());
            }
        }
    }

    private CustomerRegisterRequest createCustomerRequest(
            String email,
            String password,
            String firstName,
            String lastName
    ) {
        CustomerRegisterRequest request = new CustomerRegisterRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        return request;
    }
}