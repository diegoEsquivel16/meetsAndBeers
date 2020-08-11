package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.sevice.MeetupService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/meets-and-beer")
public class MeetupsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetupsController.class);
    private final MeetupService meetupService;

    @Autowired
    public MeetupsController(MeetupService meetupService) {
        this.meetupService = meetupService;
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/meetups/{meetupId}/weather-information")
    public ResponseEntity<WeatherInformation> getWeatherInformation(@PathVariable String meetupId){
        try {
            return ResponseEntity.ok(meetupService.getWeatherInformation(meetupId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ADMIN"})
    @GetMapping("/meetups/{meetupId}")
    public ResponseEntity<Meetup> findMeetup(@PathVariable String meetupId){
        try {
            return ResponseEntity.ok(meetupService.findMeetup(meetupId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ADMIN"})
    @PostMapping("/meetups")
    public ResponseEntity createMeetup(@RequestBody MeetupRequest meetupRequest, HttpServletRequest servletRequest){
        String organizerEmployeeEmail = servletRequest.getUserPrincipal().getName();
        try {
            return new ResponseEntity<>(meetupService.createMeetup(organizerEmployeeEmail, meetupRequest), HttpStatus.CREATED);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ADMIN, USER"})
    @PatchMapping("/meetups/{meetupId}/subscribe")
    public ResponseEntity<Void> suscribeToMeetup(@PathVariable String meetupId, HttpServletRequest servletRequest){
        String employeeEmail = servletRequest.getUserPrincipal().getName();
        try {
            meetupService.subscribeToMeetup(meetupId, employeeEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while subscribing guest {} for meetup {}", employeeEmail, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ADMIN", "USER"})
    @PatchMapping("/meetups/{meetupId}/check-in")
    public ResponseEntity<String> confirmGuestParticipationToMeetup(@PathVariable String meetupId, HttpServletRequest servletRequest){
        String employeeEmail = servletRequest.getUserPrincipal().getName();
        try {
            meetupService.confirmGuestParticipationToMeetup(meetupId, employeeEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while making check-in of {} for meetup {}", employeeEmail, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ADMIN", "USER"})
    @PatchMapping("/meetups/{meetupId}/reject-invitation")
    public ResponseEntity<String> guestRefusesParticipationToMeetup(@PathVariable String meetupId, HttpServletRequest servletRequest){
        String employeeEmail = servletRequest.getUserPrincipal().getName();
        try {
            meetupService.guestRefusesParticipationToMeetup(meetupId, employeeEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while making refusing invitation of {} for meetup {}", employeeEmail, meetupId);
            return ResponseEntity.notFound().build();
        }
    }

}
