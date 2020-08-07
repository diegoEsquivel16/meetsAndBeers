package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import santander_tec.dto.Employee;
import santander_tec.dto.request.NewEmployeeRequest;
import santander_tec.sevice.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/meets-and-beer")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(){
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @PostMapping("/employees")
    public ResponseEntity createEmployee(@RequestBody NewEmployeeRequest newEmployeeRequest){
        try {
            return new ResponseEntity<>(employeeService.createEmployee(newEmployeeRequest), HttpStatus.CREATED);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception exc) {
            LOGGER.error("Couldn't create a new Employee. Error: {}", exc.getMessage());
            return new ResponseEntity<>("Couldn't create a new Employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
