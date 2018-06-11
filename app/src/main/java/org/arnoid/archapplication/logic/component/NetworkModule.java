package org.arnoid.archapplication.logic.component;

import android.os.Build;

import org.arnoid.archapplication.BuildConfig;
import org.arnoid.archapplication.logic.network.LoggingInterceptor;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggingInterceptor())
                .build();
    }
}
