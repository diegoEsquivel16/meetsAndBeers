package santander_tec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import santander_tec.dto.Employee;
import santander_tec.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u from User u where u.employee = :employee")
    User findByEmployeeId(Employee employee);

}
