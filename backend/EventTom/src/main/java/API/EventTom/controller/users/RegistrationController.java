package API.EventTom.controller.users;

import API.EventTom.DTO.request.CustomerRegisterRequestDTO;
import API.EventTom.DTO.request.EmployeeRegisterRequestDTO;
import API.EventTom.services.users.interfaces.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final IRegistrationService registrationService;

    @PostMapping("/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterRequestDTO request) {
        return registrationService.registerCustomer(request);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRegisterRequestDTO request) {
        return registrationService.registerEmployee(request);
    }
}