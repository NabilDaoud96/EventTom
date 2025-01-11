package API.EventTom.services.security.interfaces;

import API.EventTom.DTO.request.LoginRequestDTO;
import API.EventTom.DTO.response.LoginResponseDTO;
import API.EventTom.DTO.response.TokenRefreshResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
    LoginResponseDTO authenticate(LoginRequestDTO request, HttpServletResponse response);
    void logout(String refreshToken, HttpServletResponse response);
    TokenRefreshResponseDTO refreshToken(String refreshToken, HttpServletResponse response);
}