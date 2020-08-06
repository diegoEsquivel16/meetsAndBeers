package santander_tec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santander_tec.dto.Meetup;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, String> {

    //@Query("SELECT m from Meetup m where m.id = :meetupId")
    //Meetup findById(@Param("meetupId") String meetupId);

}
