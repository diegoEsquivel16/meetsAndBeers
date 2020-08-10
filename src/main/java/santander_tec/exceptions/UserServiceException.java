package santander_tec.exceptions;

import static java.lang.String.format;

public class UserServiceException extends RuntimeException {

    public UserServiceException(String email) {
        super(format("Couldn't find the user %s", email));
    }

}
