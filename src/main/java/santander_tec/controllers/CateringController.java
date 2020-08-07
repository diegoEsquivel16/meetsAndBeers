package santander_tec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santander_tec.dto.response.CateringResponse;
import santander_tec.exceptions.MeetUpNotFoundException;
import santander_tec.sevice.CateringService;

@RestController
@RequestMapping("/meets-and-beer")
public class CateringController {

    private final CateringService cateringService;

    @Autowired
    public CateringController(CateringService cateringService) {
        this.cateringService = cateringService;
    }

    @GetMapping("/meetups/{meetupId}/required-catering")
    public ResponseEntity<CateringResponse> getRequiredCatering(@PathVariable String meetupId){
        try{
            return ResponseEntity.ok(cateringService.getRequiredCatering(meetupId));
        } catch (MeetUpNotFoundException mnfe) {
            return ResponseEntity.notFound().build();
        } catch (Exception exc) {
            return new ResponseEntity(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
