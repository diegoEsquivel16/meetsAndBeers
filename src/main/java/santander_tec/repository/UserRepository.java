package santander_tec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santander_tec.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //@Query("SELECT u from User u where u.id = :userId")
    //User findById(@Param("userId") String userId);

}
