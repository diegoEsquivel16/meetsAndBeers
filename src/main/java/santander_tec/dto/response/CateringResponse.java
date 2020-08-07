package santander_tec.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import santander_tec.dto.Beverages;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CateringResponse {

    private Beverages beverages;

    public Beverages getBeverages() {
        return beverages;
    }

    public void setBeverages(Beverages beverages) {
        this.beverages = beverages;
    }
}
