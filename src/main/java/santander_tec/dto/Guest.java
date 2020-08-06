package santander_tec.dto;

import javax.persistence.*;

@Entity
@Table(name = "guests")
public class Guest extends IdentificableEntity{

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private GuestStatus status;
    @ManyToOne
    @JoinColumn(name = "meetup_id")
    private Meetup meetup;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public GuestStatus getStatus() {
        return status;
    }

    public void setStatus(GuestStatus status) {
        this.status = status;
    }

    public Meetup getMeetup() {
        return meetup;
    }

    public void setMeetup(Meetup meetup) {
        this.meetup = meetup;
    }
}
