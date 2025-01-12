package API.EventTom.controller.users;

import API.EventTom.dto.EmployeeDTO;
import API.EventTom.services.users.interfaces.IEmployeeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController{
    private final IEmployeeQueryService employeeService;


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        return ResponseEntity.ok(employeeDTO);
    }


    @GetMapping("/event-managers")
    // @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'EVENT_MANAGER', 'EVENT_CREATOR')")
    public ResponseEntity<List<EmployeeDTO>> getAllEventManagers() {
        List<EmployeeDTO> eventManagers = employeeService.getAllEventManagers();
        return ResponseEntity.ok(eventManagers);
    }

}


