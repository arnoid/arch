package org.arnoid.archapplication.logic.network;

import org.arnoid.archapplication.data.OpenWeatherData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherInterface {

    @GET("/data/2.5/weather")
    Single<OpenWeatherData> getWeatherData(
            @Query("q") String query,
            @Query("APPID") String appid
    );
}
