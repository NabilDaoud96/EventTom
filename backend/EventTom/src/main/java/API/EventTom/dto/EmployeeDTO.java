package API.EventTom.dto;

import API.EventTom.models.user.Roles;

import java.util.Set;

public record EmployeeDTO(
        Set<Roles> roles,
        String name,
        String email
) {}
