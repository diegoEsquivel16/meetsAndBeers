package santander_tec.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.Set;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeetupRequest {

    private String organizerId;//TODO cuando tenga login mandar esto por header
    private Set<String> guestEmails;
    private LocalDateTime date;
    private String location;

    public String getOrganizerId() {
        return organizerId;
    }

    public Set<String> getGuestEmails() {
        return guestEmails;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
