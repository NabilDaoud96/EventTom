package API.EventTom.services.users.interfaces;

import API.EventTom.DTO.request.CustomerRegisterRequestDTO;
import API.EventTom.DTO.request.EmployeeRegisterRequestDTO;
import API.EventTom.DTO.response.RegisterResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IRegistrationService {
    RegisterResponseDTO registerCustomer(CustomerRegisterRequestDTO request);
    RegisterResponseDTO registerEmployee(EmployeeRegisterRequestDTO request);
}