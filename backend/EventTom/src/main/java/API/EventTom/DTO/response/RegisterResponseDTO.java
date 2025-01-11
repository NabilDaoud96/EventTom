package API.EventTom.DTO.response;


public record RegisterResponseDTO(
        Long id,
        String email,
        String message
) {}