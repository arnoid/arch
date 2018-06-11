package org.arnoid.archapplication.logic.component;

import org.arnoid.archapplication.logic.network.NetworkController;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ControllerModule {

    @Provides
    public NetworkController provideNetworkController(Retrofit retrofit) {
        return new NetworkController(retrofit);
    }
}
