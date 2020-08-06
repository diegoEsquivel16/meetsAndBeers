package santander_tec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.jdbc.Sql;

@SpringBootApplication
@EnableScheduling
@Sql({"/import_initial_data.sql"})
public class MeetsAndBeerStartup {

    public static void main(String[] args) {
        SpringApplication.run(MeetsAndBeerStartup.class, args);
    }

}
