package API.EventTom.DTO.response;

import java.util.Set;

public record LoginResponseDTO(
        Long id,
        String email,
        Set<String> roles
) {}