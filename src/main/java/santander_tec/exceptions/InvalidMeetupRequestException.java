package santander_tec.exceptions;

import static java.lang.String.format;

public class InvalidMeetupRequestException extends RuntimeException{

    public InvalidMeetupRequestException(String message) {
        super(format("The meetup request is invalid! %s", message));
    }
}
