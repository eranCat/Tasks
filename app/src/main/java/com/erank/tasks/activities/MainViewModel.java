package com.erank.tasks.activities;

import androidx.lifecycle.ViewModel;

import com.erank.tasks.utils.room.Repo;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    public MainViewModel() {
    }

    public void getTasks(FetchLiveTasksCallback callback) {
        Repo.getTasks(callback);
    }
}
