package santander_tec.exceptions;

import static java.lang.String.format;

public class UserRepositoryException extends RuntimeException {

    public UserRepositoryException(String userId) {
        super(format("Couldn't find the user %s", userId));
    }

}
