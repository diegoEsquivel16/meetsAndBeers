package santander_tec.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Beverages {

    private Long beerBottleBoxes;

    public Long getBeerBottleBoxes() {
        return beerBottleBoxes;
    }

    public void setBeerBottleBoxes(Long beerBottleBoxes) {
        this.beerBottleBoxes = beerBottleBoxes;
    }
}
