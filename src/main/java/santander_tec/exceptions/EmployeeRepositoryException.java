package santander_tec.exceptions;

import static java.lang.String.format;

public class EmployeeRepositoryException extends RuntimeException{

    public EmployeeRepositoryException(String employeeId) {
        super(format("Couldn't find the employee %s", employeeId));
    }

}
