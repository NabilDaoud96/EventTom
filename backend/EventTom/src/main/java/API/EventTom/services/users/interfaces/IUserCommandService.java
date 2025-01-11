package API.EventTom.services.users.interfaces;

import API.EventTom.dto.request.UserEditProfileRequestDTO;

public interface IUserCommandService {
    UserEditProfileRequestDTO editProfile(UserEditProfileRequestDTO userDTO, Long userId);
}
