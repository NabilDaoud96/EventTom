package API.EventTom.DTO.response;

public record TokenRefreshResponseDTO(
        String accessToken,
        String refreshToken
) {}
