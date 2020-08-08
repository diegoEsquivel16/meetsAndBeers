package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import santander_tec.sevice.weather.ApiWeatherSelector;

@Controller
@RequestMapping("/meets-and-beer")
public class ApiWeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiWeatherController.class);
    private final ApiWeatherSelector weatherSelector;

    @Autowired
    public ApiWeatherController(ApiWeatherSelector weatherSelector) {
        this.weatherSelector = weatherSelector;
    }

    @PatchMapping("/api-weather/open-weather")
    public ResponseEntity<Void> setOpenWeatherApi(){
        LOGGER.info("Changing API Weather to Open Weather Api...");
        weatherSelector.changeApiToOpenWeather();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api-weather/dummy-api-weather")
    public ResponseEntity<Void> setDummyApi(){
        LOGGER.info("Changing API Weather to Dummy Api Weather...");
        weatherSelector.changeApiToDummyApiWeather();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api-weather/set-dummy-temperature")
    public ResponseEntity<Void> setDummyApiWeatherTemperature(@RequestBody Double temperature){
        LOGGER.info("Changing Temperature of API Weather to Dummy to {}", temperature);
        weatherSelector.changeDummyApiWeatherTemperature(temperature);
        return ResponseEntity.ok().build();
    }
}
