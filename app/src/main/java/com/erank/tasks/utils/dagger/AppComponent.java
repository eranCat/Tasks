package com.erank.tasks.utils.dagger;

import android.content.Context;

import com.erank.tasks.activities.MainActivity;
import com.erank.tasks.activities.create.CreateActivity;
import com.erank.tasks.fragments.info.InfoFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = DataModule.class)
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);

    void inject(CreateActivity activity);

    void inject(InfoFragment fragment);

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Context context);
    }
}
