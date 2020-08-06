package santander_tec.dto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends IdentificableEntity{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String email;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    @OneToMany(mappedBy = "employee", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Guest> guests;
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meetup> meetups;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
