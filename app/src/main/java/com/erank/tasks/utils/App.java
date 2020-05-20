package com.erank.tasks.utils;

import android.app.Application;

import com.erank.tasks.utils.dagger.AppComponent;
import com.erank.tasks.utils.dagger.DaggerAppComponent;
import com.facebook.stetho.Stetho;

public class App extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        appComponent = DaggerAppComponent.factory()
                .create(getApplicationContext());

    }
}
