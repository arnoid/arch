package org.arnoid.archapplication.ui.step5;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.text.TextUtils;
import android.widget.EditText;

import org.arnoid.archapplication.R;
import org.arnoid.archapplication.util.OnTextChangeTextWatcher;
import org.arnoid.archapplication.viewmodel.WeatherViewModel;

public class EditTextDecorator {

    private final EditText edtCityName;
    private final EditText edtStateName;

    public EditTextDecorator(Activity activity, LifecycleOwner lifecycleOwner, WeatherViewModel weatherViewModel) {
        edtCityName = activity.findViewById(R.id.edt_city_name);
        edtStateName = activity.findViewById(R.id.edt_state_name);

        edtCityName.setText(weatherViewModel.getCityName().getValue());
        edtStateName.setText(weatherViewModel.getStateName().getValue());

        edtCityName.addTextChangedListener(new OnTextChangeTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weatherViewModel.getCityName().setValue(s.toString());
            }
        });

        edtStateName.addTextChangedListener(new OnTextChangeTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weatherViewModel.getStateName().setValue(s.toString());
            }
        });

        weatherViewModel.getCityName().observe(lifecycleOwner, (String newValue) -> {
            if (TextUtils.isEmpty(newValue)) {
                setCityNameError("Empty");
            } else {
                setCityNameError(null);
            }
        });

        weatherViewModel.getStateName().observe(lifecycleOwner, (String newValue) -> {
            if (TextUtils.isEmpty(newValue)) {
                setStateNameError("Empty");
            } else {
                setStateNameError(null);
            }
        });
    }

    private void setCityNameError(String error) {
        edtCityName.setError(error);
    }

    private void setStateNameError(String error) {
        edtStateName.setError(error);
    }
}
