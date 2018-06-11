package org.arnoid.archapplication.ui.step4;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.arnoid.archapplication.R;
import org.arnoid.archapplication.WeatherApplication;
import org.arnoid.archapplication.data.OpenWeatherData;
import org.arnoid.archapplication.logic.network.NetworkController;
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActivityStep4 extends AppCompatActivity implements ButtonsDecorator.ButtonsDecoratorInterface {
    public Disposable weatherDataRequestDisposable;

    protected Toolbar toolbar;

    @Inject
    protected
    NetworkController networkController;

    private WeatherViewModel weatherViewModel;
    private ActivityStep4Navigator navigator;

    public static Intent intent(Context ctx) {
        return new Intent(ctx, ActivityStep4.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        WeatherApplication.applicationComponent.inject(this);

        setContentView(R.layout.activity_weather_data);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        navigator = new ActivityStep4NavigatorImpl(this);

        new EditTextDecorator(this, this, weatherViewModel);
        new ButtonsDecorator(this, this, weatherViewModel);
        new WeatherDataDecorator(this, this, weatherViewModel);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigator.close();
        }
        return super.onOptionsItemSelected(item);
    }

    public void disposeWeatherRequest() {
        if (weatherDataRequestDisposable != null) {
            weatherDataRequestDisposable.dispose();
        }
    }
}
