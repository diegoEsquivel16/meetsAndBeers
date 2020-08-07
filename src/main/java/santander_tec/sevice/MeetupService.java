package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import santander_tec.dto.*;
import santander_tec.dto.request.MeetupRequest;
import santander_tec.dto.response.MeetupCreationResponse;
import santander_tec.exceptions.FinishedMeetupException;
import santander_tec.exceptions.MeetUpNotFoundException;
import santander_tec.repository.MeetupRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MeetupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetupService.class);
    private final MeetupRepository meetupRepository;
    private final WeatherService weatherService;
    private final EmployeeService employeeService;
    private final GuestService guestService;

    @Autowired
    public MeetupService(MeetupRepository meetupRepository, WeatherService weatherService, EmployeeService employeeService, GuestService guestService) {
        this.meetupRepository = meetupRepository;
        this.weatherService = weatherService;
        this.employeeService = employeeService;
        this.guestService = guestService;
    }

    public WeatherInformation getWeatherInformation(String meetupId) {
        Meetup meetup = findMeetupByIdIfNotFinished(meetupId);
        return weatherService.getWeatherInformation(meetup);
    }

    public Meetup findMeetup(String meetupId){
        LOGGER.info("Going to find meetup {}",meetupId);
        return meetupRepository.findById(meetupId).orElseThrow(() -> new MeetUpNotFoundException(meetupId));
    }

    public MeetupCreationResponse createMeetup(MeetupRequest meetupRequest) {
        validateMeetupRequest(meetupRequest);
        Meetup newMeetup = buildNewMeetup(meetupRequest);

        Meetup persistedMeetup = meetupRepository.save(newMeetup);
        List<Guest> persistedGuests = guestService.addGuests(meetupRequest.getGuestEmails(), persistedMeetup);

        return buildMeetupResponse(newMeetup, persistedGuests);
    }

    public void subscribeToMeetup(String meetupId, String employeeId) {
        LOGGER.info("Going to subscribe the employee {} to the meetup {}", employeeId, meetupId);
        guestService.addGuest(employeeId, findMeetupByIdIfNotFinished(meetupId));
    }

    public void confirmGuestParticipationToMeetup(String meetupId, String employeeId){
        updateMeetupGuestStatus(meetupId, employeeId, GuestStatus.CONFIRMED);
    }

    public void guestRefusesParticipationToMeetup(String meetupId, String employeeId){
        updateMeetupGuestStatus(meetupId, employeeId, GuestStatus.NOT_GOING);
    }

    public Meetup findMeetupByIdIfNotFinished(String meetupId){
        Meetup meetup = findMeetup(meetupId);
        if(meetupAlreadyFinished(meetup.getDate())) {
            LOGGER.error("The meetup {} already finish the day {}", meetupId, meetup.getDate());
            throw new FinishedMeetupException(meetupId, meetup.getDate());
        }
        return meetup;
    }

    private Boolean meetupAlreadyFinished(LocalDateTime finishDate){
        return LocalDateTime.now().isAfter(finishDate);
    }

    private void validateMeetupRequest(MeetupRequest meetupRequest) {
        Assert.notNull(meetupRequest.getOrganizerId(), "The organizerId is null");
        Assert.notNull(meetupRequest.getDate(), "The date for the meetup is null");
        validateLocation(meetupRequest.getLocation());
        Assert.notNull(meetupRequest.getGuestEmails(), "The guests list is null");
    }

    private void validateLocation(String location) {
        Assert.notNull(location, "The location for the meetup is null");
        try{
            Location.valueOf(location);
        } catch (IllegalArgumentException iae) {
            LOGGER.error("The location {} is unknown", location);
            throw iae;
        }
    }

    private Meetup buildNewMeetup(MeetupRequest meetupRequest){
        Meetup newMeetup = new Meetup();
        newMeetup.setId(UUID.randomUUID().toString());
        newMeetup.setDate(meetupRequest.getDate());
        newMeetup.setOrganizer(employeeService.findById(meetupRequest.getOrganizerId()));
        newMeetup.setLocation(Location.valueOf(meetupRequest.getLocation()));
        return newMeetup;
    }

    private MeetupCreationResponse buildMeetupResponse(Meetup newMeetup, List<Guest> persistedGuests){
        MeetupCreationResponse meetupResponse = new MeetupCreationResponse();
        meetupResponse.setId(newMeetup.getId());
        meetupResponse.setOrganizerId(newMeetup.getOrganizer().getId());
        meetupResponse.setDate(newMeetup.getDate());
        meetupResponse.setLocation(newMeetup.getLocation().name());
        meetupResponse.setGuests(persistedGuests);
        return meetupResponse;
    }

    private void updateMeetupGuestStatus(String meetupId, String employeeId, GuestStatus newStatus) {
        Meetup meetup = findMeetupByIdIfNotFinished(meetupId);
        guestService.updateGuestStatus(meetup, employeeId, newStatus);
    }

}
