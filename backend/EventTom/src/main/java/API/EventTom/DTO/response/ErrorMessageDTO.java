package API.EventTom.DTO.response;

import java.util.Date;



public record ErrorMessageDTO(
        String message,
        String error,
        Date timestamp,
        int status
) {}