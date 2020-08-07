package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.sevice.MeetupService;

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

    @GetMapping("/meetups/{meetupId}")
    public ResponseEntity<Meetup> findMeetup(@PathVariable String meetupId){
        try {
            return ResponseEntity.ok(meetupService.findMeetup(meetupId));
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

    @PatchMapping("/meetups/{meetupId}/subscribe/{employeeId}") //TODO sacar el employeeId de un header o la sesion
    public ResponseEntity<Void> suscribeToMeetup(@PathVariable String meetupId, @PathVariable String employeeId){
        try {
            meetupService.subscribeToMeetup(meetupId, employeeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while subscribing guest {} for meetup {}", employeeId, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/meetups/{meetupId}/check-in/{employeeId}") //TODO sacar el employeeId de un header o la sesion
    public ResponseEntity<String> confirmGuestParticipationToMeetup(@PathVariable String meetupId, @PathVariable String employeeId){
        try {
            meetupService.confirmGuestParticipationToMeetup(meetupId, employeeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while making check-in of {} for meetup {}", employeeId, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/meetups/{meetupId}/reject-invitation/{employeeId}") //TODO sacar el employeeId de un header o la sesion
    public ResponseEntity<String> guestRefusesParticipationToMeetup(@PathVariable String meetupId, @PathVariable String employeeId){
        try {
            meetupService.guestRefusesParticipationToMeetup(meetupId, employeeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while making refusing invitation of {} for meetup {}", employeeId, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

}
