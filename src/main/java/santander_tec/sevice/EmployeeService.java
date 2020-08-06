package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander_tec.dto.Employee;
import santander_tec.exceptions.EmployeeNotFoundException;
import santander_tec.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(String employeeId){
        LOGGER.info("Going to find employee {}",employeeId);
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Employee findByEmail(String email){
        LOGGER.info("Going to find employee for email {}",email);
        return employeeRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(email));
    }

}
