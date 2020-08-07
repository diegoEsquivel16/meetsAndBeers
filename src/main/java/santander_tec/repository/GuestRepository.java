package santander_tec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import santander_tec.dto.Employee;
import santander_tec.dto.Guest;
import santander_tec.dto.GuestStatus;
import santander_tec.dto.Meetup;

import javax.transaction.Transactional;

@Repository
public interface GuestRepository  extends JpaRepository<Guest, String> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Guest g set g.status = :status where g.meetup = :meetup and g.employee = :employee")
    int updateGuestStatus(Meetup meetup, Employee employee, GuestStatus status);

}
