package com.erank.tasks.utils;

import com.erank.tasks.activities.MainActivity;

import dagger.Component;

@Component
public interface Injector {
    void inject(MainActivity activity);
}
