package org.arnoid.archapplication.ui.step4;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.widget.TextView;

import org.arnoid.archapplication.R;
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

public class WeatherDataDecorator {

    public WeatherDataDecorator(Activity activity, LifecycleOwner lifecycleOwner, WeatherViewModel weatherViewModel) {
        TextView txtWeatherData = activity.findViewById(R.id.txt_weather_data);

        weatherViewModel.getWeatherData().observe(lifecycleOwner, (String newValue) -> txtWeatherData.setText(newValue));
    }
}
