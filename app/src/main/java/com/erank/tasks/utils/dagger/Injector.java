package com.erank.tasks.utils.dagger;

import com.erank.tasks.activities.MainActivity;

import dagger.Component;

@Component(modules = DataModule.class)
public interface Injector {
    void inject(MainActivity activity);
}
