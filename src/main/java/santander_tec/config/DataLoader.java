package santander_tec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import santander_tec.dto.request.NewEmployeeRequest;
import santander_tec.dto.request.UserRegisterRequest;
import santander_tec.sevice.EmployeeService;
import santander_tec.sevice.UserService;

@Component
public class DataLoader implements ApplicationRunner {

    private final EmployeeService employeeService;
    private final UserService userService;

    @Autowired
    public DataLoader(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }


    public void run(ApplicationArguments args) {
        buildEmployees();
        buildUsers();
    }

    private void buildEmployees(){
        employeeService.createEmployee(buildNewEmployee("pepe1", "lastname1", "email1@g.com"));
        employeeService.createEmployee(buildNewEmployee("pepe2", "lastname2", "email2@g.com"));
        employeeService.createEmployee(buildNewEmployee("pepe3", "lastname3", "email3@g.com"));
        employeeService.createEmployee(buildNewEmployee("pepe4", "lastname4", "email4@g.com"));
        employeeService.createEmployee(buildNewEmployee("pepe5", "lastname5", "email5@g.com"));
    }

    private NewEmployeeRequest buildNewEmployee(String firstName, String lastName, String email){
        NewEmployeeRequest newEmployee = new NewEmployeeRequest();
        newEmployee.setEmail(email);
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        return newEmployee;
    }


    private void buildUsers(){
            userService.registerUserAsAdmin(buildNewUser("email1@g.com", "admin1"));
        userService.registerUserAsUser(buildNewUser("email2@g.com", "user2"));
        userService.registerUserAsUser(buildNewUser("email3@g.com", "user3"));
        userService.registerUserAsUser(buildNewUser("email4@g.com", "user4"));
        userService.registerUserAsUser(buildNewUser("email5@g.com", "user5"));
    }

    private UserRegisterRequest buildNewUser(String email, String password){
        UserRegisterRequest newUser = new UserRegisterRequest();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return newUser;
    }
}