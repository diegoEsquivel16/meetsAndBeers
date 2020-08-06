package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.sevice.MeetupService;

import static java.lang.String.format;

@RestController
@RequestMapping("/meets-and-beer")
public class MeetupsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetupsController.class);
    private final MeetupService meetupService;

    @Autowired
    public MeetupsController(MeetupService meetupService) {
        this.meetupService = meetupService;
    }

    @GetMapping("/meetups/{meetupId}/weather-information")
    public ResponseEntity<WeatherInformation> getWeatherInformation(@PathVariable String meetupId){
        try {
            return ResponseEntity.ok(meetupService.getWeatherInformation(meetupId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/meetups")
    public ResponseEntity createMeetup(@RequestBody MeetupRequest meetupRequest){
        try {
            return new ResponseEntity<>(meetupService.createMeetup(meetupRequest), HttpStatus.CREATED);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/meetups/{meetupId}/suscribe")
    public ResponseEntity<String> suscribeToMeetup(@PathVariable String meetupId){
        return ResponseEntity.ok(format("tas suscripto ameo %s", meetupId));
    }

    @PatchMapping("/meetups/{meetupId}/update-guest-status")
    public ResponseEntity<String> checkinToMeetup(@PathVariable String meetupId){
        return ResponseEntity.ok(format("checkin lesto ameo %s",meetupId));
    }

}
