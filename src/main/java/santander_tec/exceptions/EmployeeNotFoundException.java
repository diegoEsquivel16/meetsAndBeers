package santander_tec.exceptions;

import static java.lang.String.format;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(String employeeId){
        super(format("Couldn't find the employee %s", employeeId));
    }
}
