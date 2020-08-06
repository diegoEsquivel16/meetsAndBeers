package santander_tec.dto;

public enum Location {

    CABA("lat","long","CABA");

    Location(String latitude, String longitude, String locationId){
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationId = locationId;
    }

    private final String latitude;
    private final String longitude;
    private final String locationId;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocationId() {
        return locationId;
    }
}
