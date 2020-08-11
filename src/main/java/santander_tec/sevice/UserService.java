package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import santander_tec.dto.Employee;
import santander_tec.dto.Rol;
import santander_tec.dto.User;
import santander_tec.dto.request.UserRegisterRequest;
import santander_tec.repository.UserRepository;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static santander_tec.FieldValidator.validateRequestField;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByEmail(String email){
        Employee employee = employeeService.findByEmail(email);
        LOGGER.info("Going to find user of employee {}", employee.getId());
        return this.userRepository.findByEmployeeId(employee);
    }

    public void registerUserAsUser(UserRegisterRequest request){
        registerUser(request, Collections.singleton(Rol.USER));
    }

    public void registerUserAsAdmin(UserRegisterRequest request){
        registerUser(request, Collections.singleton(Rol.ADMIN));
    }

    private void registerUser(UserRegisterRequest request, Set<Rol> roles) {
        validateUserCreationRequest(request);
        Employee employee = employeeService.findByEmail(request.getEmail());
        User newUser = buildNewUser(request, employee, roles);
        this.userRepository.save(newUser);
    }


    private User buildNewUser(UserRegisterRequest request, Employee employee, Set<Rol> roles){
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setEmployee(employee);
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setRoles(roles);
        return newUser;
    }

    private void validateUserCreationRequest(UserRegisterRequest request){
        validateRequestField(request.getEmail(), "email");
        validateRequestField(request.getPassword(), "password");
        //TODO Add password format validation
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        try{
            return findByEmail(userName);
        } catch (Exception exc) {
            throw new UsernameNotFoundException(userName, exc);
        }
    }

}
