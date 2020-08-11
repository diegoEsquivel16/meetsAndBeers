package santander_tec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeetsAndBeerStartup {

    public static void main(String[] args) {
        SpringApplication.run(MeetsAndBeerStartup.class, args);
    }

}
