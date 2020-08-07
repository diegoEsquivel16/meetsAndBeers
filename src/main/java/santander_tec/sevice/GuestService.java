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
        Set<Employee> employeesSet = employeeService.findByEmailSet(guestEmails);
        LOGGER.info("Going to build and save the guests for the meetup {}", meetup.getId());
        Set<Guest> guests = employeesSet.stream().map(employee -> buildGuest(employee, meetup)).collect(Collectors.toSet());
        return guestRepository.saveAll(guests);
    }

    public Guest addGuest(String guestEmployeeId, Meetup meetup){
        LOGGER.info("Going to find the employee {}", guestEmployeeId);
        Employee employee = employeeService.findById(guestEmployeeId);
        LOGGER.info("Going to build and save the guest for the meetup {}", meetup.getId());
        return guestRepository.save(buildGuest(employee, meetup));
    }

    private Guest buildGuest(Employee employee, Meetup meetup) {
        Guest newGuest = new Guest();
        newGuest.setId(UUID.randomUUID().toString());
        newGuest.setEmployee(employee);
        newGuest.setMeetup(meetup);
        newGuest.setStatus(GuestStatus.NOT_CONFIRMED);
        return newGuest;
    }

    public void updateGuestStatus(Meetup meetup, String guestEmployeeId, GuestStatus newGuestStatus){
        Employee employee = employeeService.findById(guestEmployeeId);
        LOGGER.info("Going to update the guest status to {} for the meetup {}", newGuestStatus, meetup.getId());
        guestRepository.updateGuestStatus(meetup, employee, newGuestStatus);
    }
}
