package santander_tec.exceptions;

import java.time.LocalDateTime;

import static java.lang.String.format;

public class FinishedMeetupException extends RuntimeException{

    public FinishedMeetupException(String meetupId, LocalDateTime finishedDate) {
        super(format("The meetup %s finished, it was the day %s", meetupId, finishedDate));
    }
}
