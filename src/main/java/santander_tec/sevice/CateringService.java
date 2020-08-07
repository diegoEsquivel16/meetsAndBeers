package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander_tec.dto.Beverages;
import santander_tec.dto.GuestStatus;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.response.CateringResponse;
import santander_tec.exceptions.ApiWeatherException;
import santander_tec.exceptions.CateringException;

import static java.lang.String.format;

@Service
public class CateringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CateringService.class);
    private static final Double MAX_AMOUNT_OF_BEER_BOTTLES = 2.0;
    private static final Double NORMAL_AMOUNT_OF_BEER_BOTTLES = 1.0;
    private static final Double MINIMUM_AMOUNT_OF_BEER_BOTTLES = 0.75;
    private static final Integer BEER_BOTTLES_PER_BOX = 6;
    private final MeetupService meetupService;
    private final WeatherService weatherService;

    @Autowired
    public CateringService(MeetupService meetupService, WeatherService weatherService) {
        this.meetupService = meetupService;
        this.weatherService = weatherService;
    }

    public CateringResponse getRequiredCatering(String meetupId){
        LOGGER.info("Going to calculate the required catering for the meetup {}...", meetupId);
        Meetup meetup = meetupService.findMeetupByIdIfNotFinished(meetupId);
        CateringResponse cateringResponse = new CateringResponse();
        try{
            WeatherInformation weatherInformation = weatherService.getWeatherInformation(meetup);
            cateringResponse.setBeverages(buildBeverages(meetup, weatherInformation));
        } catch (ApiWeatherException awe){
            String message = format("Between %s to %s bottles of beer will be needed.",
                    maxAmountOfBeerBoxes(meetup), minimumAmountOfBeerBoxes(meetup));
            LOGGER.warn("Returning estimated values of beer needed. {}", message);
            throw new CateringException(meetupId, message);
        }
        return cateringResponse;
    }

    private Beverages buildBeverages(Meetup meetup, WeatherInformation weatherInformation) {
        Beverages beverages = new Beverages();
        beverages.setBeerBottleBoxes(calculateAmountOfBeerBoxesForWeather(meetup, weatherInformation).longValue());
        return beverages;
    }

    private Double calculateAmountOfBeerBoxesForWeather(Meetup meetup, WeatherInformation weatherInformation){
        return calculateAmountOfBeerBoxes(meetup, bottlesPerGuest(weatherInformation));
    }
    private Double maxAmountOfBeerBoxes(Meetup meetup) { return calculateAmountOfBeerBoxes(meetup, MAX_AMOUNT_OF_BEER_BOTTLES); }
    private Double minimumAmountOfBeerBoxes(Meetup meetup) { return calculateAmountOfBeerBoxes(meetup, MINIMUM_AMOUNT_OF_BEER_BOTTLES); }
    private Double calculateAmountOfBeerBoxes(Meetup meetup, Double bottlesPerGuest){
        return Math.ceil(amountOfPossiblesGuests(meetup) * bottlesPerGuest / BEER_BOTTLES_PER_BOX);
    }

    private Long amountOfPossiblesGuests(Meetup meetup){
        return meetup.getGuests().stream().filter(guest -> !GuestStatus.NOT_GOING.equals(guest.getStatus())).count();
    }

    private Double bottlesPerGuest(WeatherInformation weatherInformation){
        if(isAHotDay(weatherInformation)) return MAX_AMOUNT_OF_BEER_BOTTLES;
        if(isANormalDay(weatherInformation)) return NORMAL_AMOUNT_OF_BEER_BOTTLES;
        return MINIMUM_AMOUNT_OF_BEER_BOTTLES;
    }

    private Boolean isAHotDay(WeatherInformation weatherInformation) { return weatherInformation.getTemperature() > 24; }
    private Boolean isANormalDay(WeatherInformation weatherInformation) {
        return weatherInformation.getTemperature() > 20 && weatherInformation.getTemperature() <= 24 ;
    }
}



