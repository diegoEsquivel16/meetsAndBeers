package santander_tec.exceptions;

import static java.lang.String.format;

public class CateringException extends RuntimeException{

    public CateringException(String meetupId) {
        super(format("Couldn't get the information of the catering for the meetup %s", meetupId));
    }

    public CateringException(String meetupId, String message) {
        super(format("Couldn't get the information of the catering for the meetup %s. %s", meetupId, message));
    }
}
