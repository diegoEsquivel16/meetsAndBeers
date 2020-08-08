package santander_tec.sevice.weather.clients.open_weather.response;

public class OpenWeatherDayResponse {

    private OpenWeatherTemperatureResponse temp;

    public OpenWeatherTemperatureResponse getTemp() {
        return temp;
    }

    public void setTemp(OpenWeatherTemperatureResponse temp) {
        this.temp = temp;
    }
}
