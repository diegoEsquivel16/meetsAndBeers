package santander_tec.sevice.weather.clients;

import okhttp3.OkHttpClient;

public abstract class CommonConnector {

    protected OkHttpClient client;

    public OkHttpClient getClient() {
        return client;
    }
}
