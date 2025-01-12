package API.EventTom.dto;

import API.EventTom.models.user.Roles;

import java.util.Set;

public record EmployeeDTO(
        Long id,
        Set<Roles> roles,
        String firstName,
        String lastName,
        String employeeNumber,
        String email
) {}
