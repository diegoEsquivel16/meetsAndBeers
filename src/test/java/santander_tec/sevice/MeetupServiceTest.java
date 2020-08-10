package santander_tec.sevice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import santander_tec.dto.Employee;
import santander_tec.dto.Guest;
import santander_tec.dto.Meetup;
import santander_tec.dto.WeatherInformation;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.dto.response.MeetupCreationResponse;
import santander_tec.repository.MeetupRepository;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class MeetupServiceTest {
    @Mock
    Logger LOGGER;
    @Mock
    MeetupRepository meetupRepository;
    @Mock
    WeatherService weatherService;
    @Mock
    EmployeeService employeeService;
    @Mock
    GuestService guestService;
    @InjectMocks
    MeetupService meetupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWeatherInformation() {
        when(weatherService.getWeatherInformation(any())).thenReturn(new WeatherInformation());

        WeatherInformation result = meetupService.getWeatherInformation("meetupId");
        Assertions.assertEquals(new WeatherInformation(), result);
    }

    @Test
    void testFindMeetup() {
        Meetup result = meetupService.findMeetup("meetupId");
        Assertions.assertEquals(new Meetup(), result);
    }

    @Test
    void testCreateMeetup() {
        when(employeeService.findById(anyString())).thenReturn(new Employee());
        when(guestService.addGuests(any(), any())).thenReturn(Arrays.<Guest>asList(new Guest()));

        MeetupCreationResponse result = meetupService.createMeetup(new MeetupRequest());
        Assertions.assertEquals(new MeetupCreationResponse(), result);
    }

    @Test
    void testSubscribeToMeetup() {
        when(guestService.addGuest(anyString(), any())).thenReturn(new Guest());

        meetupService.subscribeToMeetup("meetupId", "employeeId");
    }

    @Test
    void testConfirmGuestParticipationToMeetup() {
        meetupService.confirmGuestParticipationToMeetup("meetupId", "employeeId");
    }

    @Test
    void testGuestRefusesParticipationToMeetup() {
        meetupService.guestRefusesParticipationToMeetup("meetupId", "employeeId");
    }

    @Test
    void testFindMeetupByIdIfNotFinished() {
        Meetup result = meetupService.findMeetupByIdIfNotFinished("meetupId");
        Assertions.assertEquals(new Meetup(), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme