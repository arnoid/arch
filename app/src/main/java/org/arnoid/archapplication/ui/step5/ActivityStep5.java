package org.arnoid.archapplication.ui.step5;

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
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

public class ActivityStep5 extends AppCompatActivity {
    protected Toolbar toolbar;

    private WeatherViewModel weatherViewModel;
    private ActivityStep5Navigator navigator;

    public static Intent intent(Context ctx) {
        return new Intent(ctx, ActivityStep5.class);
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
        navigator = new ActivityStep5NavigatorImpl(this);

        new EditTextDecorator(this, this, weatherViewModel);
        ButtonsPresenter buttonsPresenter = new ButtonsPresenter(navigator, weatherViewModel);
        new ButtonsDecorator(buttonsPresenter, this, weatherViewModel);
        new WeatherDataDecorator(this, this, weatherViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigator.close();
        }
        return super.onOptionsItemSelected(item);
    }

}
