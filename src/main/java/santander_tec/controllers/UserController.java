package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import santander_tec.dto.request.UserRegisterRequest;
import santander_tec.sevice.UserService;

@Controller
@RequestMapping("/meets-and-beer")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity registerUser(@RequestBody UserRegisterRequest request){
        try {
            userService.registerUserAsUser(request);
            return ResponseEntity.ok().build();
        } catch (Exception exc){
            LOGGER.error("Invalid register user request as User! Error {}", exc.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity registerAdmin(@RequestBody UserRegisterRequest request){
        try {
            userService.registerUserAsAdmin(request);
            return ResponseEntity.ok().build();
        } catch (Exception exc){
            LOGGER.error("Invalid register user request as Admin! Error {}", exc.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
