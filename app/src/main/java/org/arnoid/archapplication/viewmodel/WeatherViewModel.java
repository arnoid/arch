package org.arnoid.archapplication.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import org.arnoid.archapplication.data.OpenWeatherData;
import org.arnoid.archapplication.data.Weather;

public class WeatherViewModel extends android.arch.lifecycle.ViewModel {

    MutableLiveData<String> cityName;
    MutableLiveData<String> stateName;
    MutableLiveData<String> weatherData;

    public WeatherViewModel() {
        cityName = new MutableLiveData<>();
        stateName = new MutableLiveData<>();
        weatherData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getCityName() {
        return cityName;
    }

    public MutableLiveData<String> getStateName() {
        return stateName;
    }

    public MutableLiveData<String> getWeatherData() {
        return weatherData;
    }

    public void updateWeatherData(OpenWeatherData weatherData) {
        Weather[] weatherArray = weatherData.getWeather();
        StringBuilder sb = new StringBuilder();
        for (Weather weather : weatherArray) {
            sb.append(weather.getDescription())
                    .append("\n\n");
        }

        this.weatherData.setValue(sb.toString());
    }
}
