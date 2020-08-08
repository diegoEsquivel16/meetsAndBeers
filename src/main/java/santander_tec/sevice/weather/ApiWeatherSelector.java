package santander_tec.sevice.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import santander_tec.sevice.weather.clients.DummyApiWeather;
import santander_tec.sevice.weather.clients.open_weather.OpenWeatherConnector;

@Component
public class ApiWeatherSelector {

    private ApiWeather apiWeather;
    private final DummyApiWeather dummyApiWeather;
    private final OpenWeatherConnector openWeatherApi;

    @Autowired
    public ApiWeatherSelector(DummyApiWeather dummyApiWeather, OpenWeatherConnector openWeatherApi) {
        this.dummyApiWeather = dummyApiWeather;
        this.openWeatherApi = openWeatherApi;
        this.apiWeather = dummyApiWeather;
    }

    public ApiWeather getApiWeather() {
        return apiWeather;
    }

    public void changeApiToOpenWeather() {
        this.apiWeather = openWeatherApi;
    }

    public void changeApiToDummyApiWeather() {
        this.apiWeather = dummyApiWeather;
    }

    public void changeDummyApiWeatherTemperature(Double temperature) {
        this.dummyApiWeather.setResponseTemperature(temperature);
    }
}
