package santander_tec.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.dto.response.MeetupCreationResponse;
import santander_tec.sevice.MeetupService;

import static org.mockito.Mockito.*;

class MeetupsControllerTest {
    @Mock
    Logger LOGGER;
    @Mock
    MeetupService meetupService;
    @InjectMocks
    MeetupsController meetupsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWeatherInformation() {
        when(meetupService.getWeatherInformation(anyString())).thenReturn(new WeatherInformation());

        ResponseEntity<WeatherInformation> result = meetupsController.getWeatherInformation("meetupId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testFindMeetup() {
        when(meetupService.findMeetup(anyString())).thenReturn(new Meetup());

        ResponseEntity<Meetup> result = meetupsController.findMeetup("meetupId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateMeetup() {
        when(meetupService.createMeetup(any())).thenReturn(new MeetupCreationResponse());

        ResponseEntity result = meetupsController.createMeetup(new MeetupRequest());
        Assertions.assertEquals(null, result);
    }

    @Test
    void testSuscribeToMeetup() {
        ResponseEntity<Void> result = meetupsController.suscribeToMeetup("meetupId", "employeeId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testConfirmGuestParticipationToMeetup() {
        ResponseEntity<String> result = meetupsController.confirmGuestParticipationToMeetup("meetupId", "employeeId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGuestRefusesParticipationToMeetup() {
        ResponseEntity<String> result = meetupsController.guestRefusesParticipationToMeetup("meetupId", "employeeId");
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme