package santander_tec.sevice;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import santander_tec.dto.Employee;
import santander_tec.dto.request.NewEmployeeRequest;
import santander_tec.exceptions.EmployeeNotFoundException;
import santander_tec.repository.EmployeeRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(String employeeId){
        LOGGER.info("Going to find the employee {}",employeeId);
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Set<Employee> findByEmailSet(Set<String> employeeEmails) {
        return employeeEmails.stream().map(this::findByEmail).collect(Collectors.toSet());
    }

    public Employee findByEmail(String email){
        LOGGER.info("Going to find employee for email {}",email);
        return employeeRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    public Employee createEmployee(NewEmployeeRequest newEmployeeRequest){
        LOGGER.info("Going to validate the new employee request");
        validateNewEmployeeRequest(newEmployeeRequest);
        LOGGER.info("Going to build the new employee {} and save it", newEmployeeRequest.getEmail());
        return employeeRepository.save(buildNewEmployee(newEmployeeRequest));
    }

    private Employee buildNewEmployee(NewEmployeeRequest newEmployeeRequest) {
        Employee newEmployee = new Employee();
        newEmployee.setId(UUID.randomUUID().toString());
        newEmployee.setFirstName(newEmployeeRequest.getFirstName());
        newEmployee.setLastName(newEmployeeRequest.getLastName());
        newEmployee.setEmail(newEmployeeRequest.getEmail());

        return newEmployee;
    }

    private void validateNewEmployeeRequest(NewEmployeeRequest newEmployeeRequest) {
        validateRequestField(newEmployeeRequest.getFirstName(), "firstName");
        validateRequestField(newEmployeeRequest.getLastName(), "lastName");
        validateRequestField(newEmployeeRequest.getEmail(), "email");
    }

    private void validateRequestField(String field, String fieldName){
        if(StringUtils.isBlank(field)){
            throw new IllegalArgumentException(format("The field %s is invalid", fieldName));
        }
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
