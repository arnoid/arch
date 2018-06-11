package org.arnoid.archapplication.ui.step5;

import org.arnoid.archapplication.WeatherApplication;
import org.arnoid.archapplication.data.OpenWeatherData;
import org.arnoid.archapplication.logic.network.NetworkController;
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ButtonsPresenter implements ButtonsDecorator.ButtonsDecoratorInterface {

    private final ActivityStep5Navigator navigator;
    private final WeatherViewModel weatherViewModel;
    public Disposable weatherDataRequestDisposable;

    @Inject
    NetworkController networkController;

    public ButtonsPresenter(ActivityStep5Navigator navigator, WeatherViewModel weatherViewModel) {
        this.weatherViewModel = weatherViewModel;
        WeatherApplication.applicationComponent.inject(this);
        this.navigator = navigator;

    }

    @Override
    public void requestWeatherData(String city, String state) {
        disposeWeatherRequest();
        weatherDataRequestDisposable = networkController.requestWeatherData(city, state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (OpenWeatherData weatherData) -> weatherViewModel.updateWeatherData(weatherData),
                        (Throwable t) -> weatherViewModel.getWeatherData().setValue(t.getMessage())
                );
    }

    @Override
    public void findCityWeather(String city, String state) {
        navigator.openUrl(networkController.citySearchUrl(city, state));
    }

    public void disposeWeatherRequest() {
        if (weatherDataRequestDisposable != null) {
            weatherDataRequestDisposable.dispose();
        }
    }
}
