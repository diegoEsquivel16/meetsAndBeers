package santander_tec.sevice.weather.clients.open_weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import santander_tec.dto.Location;
import santander_tec.dto.WeatherInformation;
import santander_tec.exceptions.ApiWeatherException;
import santander_tec.sevice.weather.ApiWeather;
import santander_tec.sevice.weather.clients.CommonConnector;
import santander_tec.sevice.weather.clients.open_weather.response.OpenWeatherCurrentWeatherResponse;
import santander_tec.sevice.weather.clients.open_weather.response.OpenWeatherForecastResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OpenWeatherConnector extends CommonConnector implements ApiWeather {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherConnector.class);
    private ObjectMapper mapper;
    private final String host;
    private final String apiKey;
    private static final String CURRENT_WEATHER_PATH= "/weather";
    private static final String FORECAST_WEATHER_PATH= "/forecast/daily";
    private static final Integer MAX_FORECAST_DAY = 15;

    public OpenWeatherConnector(OkHttpClient client) {
        this.client = client;
        this.host = "community-open-weather-map.p.rapidapi.com";
        this.apiKey = "fc9d791fa2mshc615367e40159e3p1dc71ejsnef3513605858";
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);
     }

    @Override
    public WeatherInformation getCurrentWeatherFor(Location location) {
        URI uri = buildCurrentWeatherURI(location);
        Request request = new Request.Builder().url(uri.toString()).get()
                .addHeader("x-rapidapi-host", this.host)
                .addHeader("x-rapidapi-key", this.apiKey)
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) {
                LOGGER.error("Couldn't get current weather for location {}. Status code: {}", location, response.code());
                throw new ApiWeatherException(location);
            }
            return buildWeatherInformation(response.body());
        } catch (Exception exc) {
            LOGGER.error("Couldn't build the weather information. Error {}", exc.getMessage());
            throw new ApiWeatherException(location);
        }
    }

    @Override
    public WeatherInformation getFurtherWeatherFor(Location location, LocalDateTime date) {
        Integer forecastDay = getForecastDay(date);
        if(dateAfterMaxPossibleForecastDays(forecastDay)) {
            throw new ApiWeatherException(location, date);
        }
        URI uri = buildFurtherWeatherURI(location, forecastDay);
        Request request = new Request.Builder().url(uri.toString()).get()
                .addHeader("x-rapidapi-host", this.host)
                .addHeader("x-rapidapi-key", this.apiKey)
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) {
                LOGGER.error("Couldn't get the forecast weather for location {}. Status Code: {}", location, response.code());
                throw new ApiWeatherException(location, date);
            }
            return buildWeatherInformation(response.body(), forecastDay);
        } catch (Exception exc) {
            LOGGER.error("Couldn't build the weather information. Error {}", exc.getMessage());
            throw new ApiWeatherException(location, date);
        }
    }

    private Boolean dateAfterMaxPossibleForecastDays(Integer forecastDay) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusDays(forecastDay).isAfter(now.plusDays(MAX_FORECAST_DAY));
    }

    private Integer getForecastDay(LocalDateTime date) {
        return Math.toIntExact(LocalDateTime.now().until(date, ChronoUnit.DAYS));
    }

    private WeatherInformation buildWeatherInformation(ResponseBody responseBody) throws IOException {
        OpenWeatherCurrentWeatherResponse apiResponse = mapper.readValue(responseBody.string(), OpenWeatherCurrentWeatherResponse.class);
        WeatherInformation weatherInformation = new WeatherInformation();
        weatherInformation.setTemperature(apiResponse.getMain().getTemp());
        return weatherInformation;
    }

    private WeatherInformation buildWeatherInformation(ResponseBody responseBody, Integer forecastDay) throws IOException {
        OpenWeatherForecastResponse apiResponse = mapper.readValue(responseBody.string(), OpenWeatherForecastResponse.class);
        WeatherInformation weatherInformation = new WeatherInformation();
        weatherInformation.setTemperature(apiResponse.getDayForecastMap().get(forecastDay).get(forecastDay).getTemp().getDay());
        return weatherInformation;
    }

    private URI buildCurrentWeatherURI(Location location){
        try{
            return new URIBuilder().setHost(this.host).setScheme("https")
                    .setPath(CURRENT_WEATHER_PATH)
                    .addParameter("units", "metric")
                    .addParameter("q", location.getLocationName())
                    .addParameter("lat", String.valueOf(location.getLatitude()))
                    .addParameter("lon", String.valueOf(location.getLongitude()))
                    .build();
        } catch (URISyntaxException use){
            LOGGER.error("Couldn't build the URI to get the current weather for location {}. Error: {}", location, use.getMessage());
            throw new ApiWeatherException(location);
        }
    }

    private URI buildFurtherWeatherURI(Location location, Integer forecastDays){
        try{
            return new URIBuilder().setHost(this.host).setScheme("https")
                    .setPath(FORECAST_WEATHER_PATH)
                    .addParameter("units", "metric")
                    .addParameter("q", location.getLocationName())
                    .addParameter("lat", String.valueOf(location.getLatitude()))
                    .addParameter("lon", String.valueOf(location.getLongitude()))
                    .addParameter("cnt", String.valueOf(forecastDays))
                    .build();
        } catch (URISyntaxException use){
            LOGGER.error("Couldn't build the URI to get the forecast weather for location {}. Error: {}", location, use.getMessage());
            throw new ApiWeatherException(location);
        }
    }
}
