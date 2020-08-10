package santander_tec.exceptions;

import static java.lang.String.format;

public class InvalidUserRegisterRequest extends RuntimeException{

    public InvalidUserRegisterRequest(String reason) {
        super(format("Invalid User Register Request. Reason: %s", reason));
    }
}
