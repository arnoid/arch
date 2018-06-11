package org.arnoid.archapplication.logic.network;

import android.support.annotation.NonNull;

import org.arnoid.archapplication.BuildConfig;
import org.arnoid.archapplication.data.OpenWeatherData;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class NetworkController {
    private final OpenWeatherInterface openWeatherInterface;

    public NetworkController(Retrofit retrofit) {
        openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
    }

    public Single<OpenWeatherData> requestWeatherData(String city, String state) {
        return openWeatherInterface.getWeatherData(produceCityStateQuery(city, state), BuildConfig.OPEN_WEATHER_APPID);
    }

    public String citySearchUrl(String city, String state) {
        return "https://openweathermap.org/find?q=" + city + "," + state;

    }

    @NonNull
    private String produceCityStateQuery(String city, String state) {
        return city + "," + state;
    }
}
