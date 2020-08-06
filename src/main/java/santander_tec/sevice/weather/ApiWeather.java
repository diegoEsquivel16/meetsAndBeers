package santander_tec.sevice.weather;

import santander_tec.dto.Location;
import santander_tec.dto.WeatherInformation;

import java.time.LocalDateTime;

public interface ApiWeather {

    WeatherInformation getFurtherWeatherFor(Location location, LocalDateTime date);

    WeatherInformation getCurrentWeatherFor(Location location);

}
