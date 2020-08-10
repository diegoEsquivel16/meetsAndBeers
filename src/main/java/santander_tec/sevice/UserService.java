package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander_tec.dto.Employee;
import santander_tec.dto.Rol;
import santander_tec.dto.User;
import santander_tec.dto.request.UserRegisterRequest;
import santander_tec.repository.UserRepository;

import java.util.UUID;

import static santander_tec.FieldValidator.validateRequestField;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final EmployeeService employeeService;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.employeeService = employeeService;
    }

    public User findByEmail(String email){
        Employee employee = employeeService.findByEmail(email);
        LOGGER.info("Going to find user of employee {}", employee.getId());
        return this.userRepository.findByEmployeeId(employee);
    }

    public void registerUserAsUser(UserRegisterRequest request){
        registerUser(request, Rol.USER);
    }

    public void registerUserAsAdmin(UserRegisterRequest request){
        registerUser(request, Rol.ADMIN);
    }
    private void registerUser(UserRegisterRequest request, Rol rol) {
        validateUserCreationRequest(request);
        Employee employee = employeeService.findByEmail(request.getEmail());
        User newUser = buildNewUser(request, employee, rol);
        this.userRepository.save(newUser);
    }


    private User buildNewUser(UserRegisterRequest request, Employee employee, Rol rol){
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setEmployee(employee);
        newUser.setPassword(request.getPassword());
        newUser.setRol(rol);
        return newUser;
    }

    private void validateUserCreationRequest(UserRegisterRequest request){
        validateRequestField(request.getEmail(), "email");
        validateRequestField(request.getPassword(), "password");
        //TODO Add password format validation
    }
}
