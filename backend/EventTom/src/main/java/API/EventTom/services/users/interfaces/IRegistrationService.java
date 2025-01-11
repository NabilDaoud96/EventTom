package API.EventTom.services.users.interfaces;

import API.EventTom.DTO.request.CustomerRegisterRequestDTO;
import API.EventTom.DTO.request.EmployeeRegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IRegistrationService {
    ResponseEntity<?> registerCustomer(CustomerRegisterRequestDTO request);
    ResponseEntity<?> registerEmployee(EmployeeRegisterRequestDTO request);
}