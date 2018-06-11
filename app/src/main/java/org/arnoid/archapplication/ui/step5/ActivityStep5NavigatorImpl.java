package org.arnoid.archapplication.ui.step5;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class ActivityStep5NavigatorImpl implements ActivityStep5Navigator {
    private WeakReference<AppCompatActivity> appCompatActivityWeakRef;

    public ActivityStep5NavigatorImpl(AppCompatActivity activity) {
        appCompatActivityWeakRef = new WeakReference<>(activity);
    }

    @Override
    public void close() {
        AppCompatActivity activity = appCompatActivityWeakRef.get();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void openUrl(String url) {
        AppCompatActivity activity = appCompatActivityWeakRef.get();
        if (activity != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        }
    }
}
