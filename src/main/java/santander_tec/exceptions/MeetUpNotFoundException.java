package santander_tec.exceptions;

import static java.lang.String.format;

public class MeetUpNotFoundException extends RuntimeException{

    public MeetUpNotFoundException(String meetupId) {
        super(format("The meetup %s not found!", meetupId));
    }
}
