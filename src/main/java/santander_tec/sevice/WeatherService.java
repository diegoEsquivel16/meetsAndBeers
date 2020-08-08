package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander_tec.dto.Location;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.exceptions.ApiWeatherException;
import santander_tec.sevice.weather.ApiWeatherSelector;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private final ApiWeatherSelector weatherSelector;

    @Autowired
    public WeatherService(ApiWeatherSelector weatherSelector) {
        this.weatherSelector = weatherSelector;
    }

    public WeatherInformation getWeatherInformation(Meetup meetup){
        try{
            if(meetupIsToday(meetup.getDate())){
                return getCurrentWeatherInformation(meetup.getLocation());
            }
            return getFurtherWeatherInformation(meetup.getLocation(), meetup.getDate());
        } catch (Exception exc){
            LOGGER.error("Couldn't get the weather information. Error {}", exc.getMessage());
            throw new ApiWeatherException(meetup.getLocation());
        }
    }

    private WeatherInformation getCurrentWeatherInformation(Location location){
        LOGGER.info("Going to get the current weather information for the location {}", location);
        return weatherSelector.getApiWeather().getCurrentWeatherFor(location);
    }

    private WeatherInformation getFurtherWeatherInformation(Location location, LocalDateTime date){
        LOGGER.info("Going to get the further weather information for the location {}", location);
        return weatherSelector.getApiWeather().getFurtherWeatherFor(location, date);
    }

    private Boolean meetupIsToday(LocalDateTime date){
        LocalDateTime now = LocalDateTime.now();
        return sameYear(now, date) && sameMonth(now, date) && sameDay(now, date);
    }

    private Boolean sameYear(LocalDateTime now, LocalDateTime date) { return now.getYear() == date.getYear();}
    private Boolean sameMonth(LocalDateTime now, LocalDateTime date) { return now.getMonthValue() == date.getMonthValue();}
    private Boolean sameDay(LocalDateTime now, LocalDateTime date) { return now.getDayOfMonth() == date.getDayOfMonth();}

}
