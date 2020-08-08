package santander_tec.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import santander_tec.sevice.weather.clients.DummyApiWeather;
import santander_tec.sevice.weather.clients.open_weather.OpenWeatherConnector;

@Configuration
public class MeetAndBeerConfig {

    @Bean
    public OkHttpClient getOkHttpClient() { return new OkHttpClient(); }

    @Bean(name = "dummyApiWeather")
    public DummyApiWeather getDummyApiWeather(){
        return new DummyApiWeather(24.0);
    }

    @Bean(name = "openWeather")
    public OpenWeatherConnector getOpenWeather(){
        return new OpenWeatherConnector(getOkHttpClient());
    }

}
