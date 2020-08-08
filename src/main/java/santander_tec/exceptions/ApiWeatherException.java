package santander_tec.exceptions;

import santander_tec.dto.Location;

import java.time.LocalDateTime;

import static java.lang.String.format;

public class ApiWeatherException extends RuntimeException{

    public ApiWeatherException(Location location) {
        super(format("Couldn't get the weather information for the location %s", location));
    }

    public ApiWeatherException(Location location, LocalDateTime date) {
        super(format("Couldn't get the weather information for the location %s and date %s", location, date));
    }
}
