package santander_tec.dto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "meetups")
public class Meetup extends IdentificableEntity{

    @ManyToOne
    @JoinColumn(name = "organizer_employee_id")
    private Employee organizer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meetup", orphanRemoval = true)
    private Set<Guest> guests;
    @Column
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Location location;

    public Employee getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Employee organizer) {
        this.organizer = organizer;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
