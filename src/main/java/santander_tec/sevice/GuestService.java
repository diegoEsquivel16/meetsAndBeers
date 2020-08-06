package santander_tec.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import santander_tec.dto.Employee;
import santander_tec.dto.Guest;
import santander_tec.dto.GuestStatus;
import santander_tec.dto.Meetup;
import santander_tec.repository.GuestRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestService.class);
    private final GuestRepository guestRepository;
    private final EmployeeService employeeService;

    public GuestService(GuestRepository guestRepository, EmployeeService employeeService) {
        this.guestRepository = guestRepository;
        this.employeeService = employeeService;
    }

    public List<Guest> addGuests(Set<String> guestEmails, Meetup meetup){
        LOGGER.info("Going to find the employees of the guest emails.");
        Set<Employee> employeesSet = guestEmails.stream().map(employeeService::findByEmail).collect(Collectors.toSet());
        LOGGER.info("Going to build and save the guests for the meetup {}", meetup.getId());
        Set<Guest> guests = employeesSet.stream().map(employee -> buildGuest(employee, meetup)).collect(Collectors.toSet());
        return guestRepository.saveAll(guests);
    }

    private Guest buildGuest(Employee employee, Meetup meetup) {
        Guest newGuest = new Guest();
        newGuest.setId(UUID.randomUUID().toString());
        newGuest.setEmployee(employee);
        newGuest.setMeetup(meetup);
        newGuest.setStatus(GuestStatus.NOT_CONFIRMED);
        return newGuest;
    }
}
