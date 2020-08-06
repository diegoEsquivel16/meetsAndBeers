package santander_tec.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santander_tec.dto.response.CateringResponse;
import santander_tec.sevice.CateringService;

@RestController
@RequestMapping("/meets-and-beer")
public class CateringController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CateringController.class);
    private final CateringService cateringService;

    @Autowired
    public CateringController(CateringService cateringService) {
        this.cateringService = cateringService;
    }

    @GetMapping("/meetups/{meetupId}/required-catering")
    public ResponseEntity<CateringResponse> getRequiredCatering(@PathVariable String meetupId){
        return ResponseEntity.ok(cateringService.getRequiredCatering(meetupId));
    }

}
