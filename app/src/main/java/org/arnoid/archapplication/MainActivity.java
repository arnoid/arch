package org.arnoid.archapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.arnoid.archapplication.ui.step1.ActivityStep1;
import org.arnoid.archapplication.ui.step2.ActivityStep2;
import org.arnoid.archapplication.ui.step3.ActivityStep3;
import org.arnoid.archapplication.ui.step4.ActivityStep4;
import org.arnoid.archapplication.ui.step5.ActivityStep5;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_step1).setOnClickListener(v -> startActivity(ActivityStep1.intent(this)));
        findViewById(R.id.btn_step2).setOnClickListener(v -> startActivity(ActivityStep2.intent(this)));
        findViewById(R.id.btn_step3).setOnClickListener(v -> startActivity(ActivityStep3.intent(this)));
        findViewById(R.id.btn_step4).setOnClickListener(v -> startActivity(ActivityStep4.intent(this)));
        findViewById(R.id.btn_step5).setOnClickListener(v -> startActivity(ActivityStep5.intent(this)));
    }
}
