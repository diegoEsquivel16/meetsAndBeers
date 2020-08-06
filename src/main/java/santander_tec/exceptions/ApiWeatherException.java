package santander_tec.exceptions;

import santander_tec.dto.Location;

import static java.lang.String.format;

public class ApiWeatherException extends RuntimeException{

    public ApiWeatherException(Location location) {
        super(format("Couldn't get the weather information for the location %s", location));
    }
}
