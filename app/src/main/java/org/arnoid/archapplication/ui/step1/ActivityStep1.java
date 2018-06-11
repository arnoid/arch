package org.arnoid.archapplication.ui.step1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.arnoid.archapplication.R;
import org.arnoid.archapplication.WeatherApplication;
import org.arnoid.archapplication.data.OpenWeatherData;
import org.arnoid.archapplication.data.Weather;
import org.arnoid.archapplication.logic.network.NetworkController;
import org.arnoid.archapplication.util.OnTextChangeTextWatcher;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActivityStep1 extends AppCompatActivity {
    public Disposable weatherDataRequestDisposable;

    protected EditText edtCityName;
    protected EditText edtStateName;
    protected Button btnSearchCity;
    protected Button btnRequestWeather;
    protected TextView txtWeatherData;
    protected Toolbar toolbar;

    @Inject
    protected
    NetworkController networkController;

    public static Intent intent(Context ctx) {
        return new Intent(ctx, ActivityStep1.class);
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

        edtCityName = findViewById(R.id.edt_city_name);
        edtStateName = findViewById(R.id.edt_state_name);
        btnSearchCity = findViewById(R.id.btn_search_city);
        btnRequestWeather = findViewById(R.id.btn_request_weather);
        txtWeatherData = findViewById(R.id.txt_weather_data);

        edtCityName.addTextChangedListener(new OnTextChangeTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    edtCityName.setError("Empty");
                } else {
                    edtCityName.setError(null);
                }
            }
        });

        edtStateName.addTextChangedListener(new OnTextChangeTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    edtStateName.setError("Empty");
                } else {
                    edtStateName.setError(null);
                }
            }
        });

        btnSearchCity.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(networkController.citySearchUrl(
                    edtCityName.getText().toString(),
                    edtStateName.getText().toString()
            )));
            startActivity(i);
        });

        btnRequestWeather.setOnClickListener(v -> {
            if (isInputValid()) {
                disposeWeatherRequest();
                weatherDataRequestDisposable = networkController.requestWeatherData(
                        edtCityName.getText().toString(),
                        edtStateName.getText().toString()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (OpenWeatherData weatherData) -> {
                                    Weather[] weatherArray = weatherData.getWeather();
                                    StringBuilder sb = new StringBuilder();
                                    for (Weather weather : weatherArray) {
                                        sb.append(weather.getDescription())
                                                .append("\n\n");
                                    }
                                    txtWeatherData.setText(sb.toString());
                                },
                                (Throwable t) -> {
                                    txtWeatherData.setText(t.getMessage());
                                }
                        );
            } else {
                txtWeatherData.setText("One of the fields is empty");
            }
        });

    }

    private boolean isInputValid() {
        if (TextUtils.isEmpty(edtCityName.getText()) ||
                TextUtils.isEmpty(edtStateName.getText())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void disposeWeatherRequest() {
        if (weatherDataRequestDisposable != null) {
            weatherDataRequestDisposable.dispose();
        }
    }

}
