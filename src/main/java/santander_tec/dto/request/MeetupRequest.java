package santander_tec.dto.request;

import java.time.LocalDateTime;
import java.util.Set;

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
