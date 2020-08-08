package santander_tec.sevice.weather.clients;

import org.springframework.beans.factory.annotation.Autowired;
import santander_tec.dto.Location;
import santander_tec.dto.WeatherInformation;
import santander_tec.sevice.weather.ApiWeather;

import java.time.LocalDateTime;

public class DummyApiWeather implements ApiWeather {

    private Double responseTemperature;

    @Autowired
    public DummyApiWeather(Double defaultTemperature) {
        this.responseTemperature = defaultTemperature;
    }

    public void setResponseTemperature(Double responseTemperature) {
        this.responseTemperature = responseTemperature;
    }

    @Override
    public WeatherInformation getFurtherWeatherFor(Location location, LocalDateTime date) {
        WeatherInformation weatherInformation = new WeatherInformation();
        weatherInformation.setTemperature(responseTemperature);
        return weatherInformation;
    }

    @Override
    public WeatherInformation getCurrentWeatherFor(Location location) {
        WeatherInformation weatherInformation = new WeatherInformation();
        weatherInformation.setTemperature(responseTemperature);
        return weatherInformation;
    }
}
