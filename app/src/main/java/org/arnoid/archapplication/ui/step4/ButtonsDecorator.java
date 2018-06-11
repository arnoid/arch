package org.arnoid.archapplication.ui.step4;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Button;

import org.arnoid.archapplication.R;
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

public class ButtonsDecorator {

    public ButtonsDecorator(ButtonsDecoratorInterface buttonsDecoratorInterface, Activity activity, WeatherViewModel weatherViewModel) {
        Button btnSearchCity = activity.findViewById(R.id.btn_search_city);
        Button btnRequestWeather = activity.findViewById(R.id.btn_request_weather);

        btnSearchCity.setOnClickListener(v -> {
            String stateName = weatherViewModel.getStateName().getValue();
            String cityName = weatherViewModel.getCityName().getValue();

            buttonsDecoratorInterface.findCityWeather(
                    cityName,
                    stateName);
        });

        btnRequestWeather.setOnClickListener(v -> {
            String stateName = weatherViewModel.getStateName().getValue();
            String cityName = weatherViewModel.getCityName().getValue();

            if (isInputValid(cityName, stateName)) {

                buttonsDecoratorInterface.requestWeatherData(
                        cityName,
                        stateName
                );
            } else {
                weatherViewModel.getWeatherData().setValue("One of the fields is empty");
            }
        });
    }

    private boolean isInputValid(String cityName, String stateName) {
        if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(stateName)) {
            return false;
        }

        return true;
    }

    public interface ButtonsDecoratorInterface {
        void requestWeatherData(String city, String state);

        void findCityWeather(String city, String state);
    }

}
