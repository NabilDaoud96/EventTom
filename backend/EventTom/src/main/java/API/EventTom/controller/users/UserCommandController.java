package API.EventTom.controller.users;


import API.EventTom.config.security.AuthenticatedUserId;
import API.EventTom.dto.request.UserEditProfileRequestDTO;
import API.EventTom.dto.response.UserDTO;
import API.EventTom.services.users.interfaces.IUserCommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/user/command")
@AllArgsConstructor
public class UserCommandController {

    private final IUserCommandService userCommandService;

    @PostMapping("/edit")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserEditProfileRequestDTO userDTO, @AuthenticatedUserId Long userId) {
        return ResponseEntity.ok(userCommandService.editProfile(userDTO, userId));
    }
}
