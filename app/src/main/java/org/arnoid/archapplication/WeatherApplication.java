package org.arnoid.archapplication;

import android.app.Application;

import org.arnoid.archapplication.logic.component.ApplicationComponent;
import org.arnoid.archapplication.logic.component.ControllerModule;
import org.arnoid.archapplication.logic.component.DaggerApplicationComponent;
import org.arnoid.archapplication.logic.component.NetworkModule;

public class WeatherApplication extends Application {

    public static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .controllerModule(new ControllerModule())
                .networkModule(new NetworkModule())
                .build();
    }
}
