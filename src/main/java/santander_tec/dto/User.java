package santander_tec.dto;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends IdentificableEntity {

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Column
    private String password;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
