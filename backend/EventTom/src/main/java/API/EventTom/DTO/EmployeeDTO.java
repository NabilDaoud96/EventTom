package API.EventTom.DTO;

import API.EventTom.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public record EmployeeDTO(
        Set<Roles> roles,
        String name,
        String email
) {}
