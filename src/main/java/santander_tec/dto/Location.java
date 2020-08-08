package santander_tec.dto;

public enum Location {

    CABA(-34.599722,-58.381944,"Buenos Aires,ar");

    Location(Double latitude, Double longitude, String locationName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    private final Double latitude;
    private final Double longitude;
    private final String locationName;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }
}
