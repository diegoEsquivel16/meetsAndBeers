package santander_tec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santander_tec.dto.Guest;

@Repository
public interface GuestRepository  extends JpaRepository<Guest, String> {


}
