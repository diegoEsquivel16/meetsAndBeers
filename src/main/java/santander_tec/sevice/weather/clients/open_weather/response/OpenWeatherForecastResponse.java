package santander_tec.sevice.weather.clients.open_weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class OpenWeatherForecastResponse {

    @JsonProperty("list")
    private List<Map<Integer, OpenWeatherDayResponse>> dayForecastMap;

    public List<Map<Integer, OpenWeatherDayResponse>> getDayForecastMap() {
        return dayForecastMap;
    }

    public void setDayForecastMap(List<Map<Integer, OpenWeatherDayResponse>> dayForecastMap) {
        this.dayForecastMap = dayForecastMap;
    }
}
