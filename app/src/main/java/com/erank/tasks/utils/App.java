package com.erank.tasks.utils;

import android.app.Application;

import com.erank.tasks.utils.room.Repo;
import com.facebook.stetho.Stetho;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        Repo.getInstance().initRoom(this);
    }
}
