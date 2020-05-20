package com.erank.tasks.activities.create;

import androidx.lifecycle.ViewModel;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.Repository;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;

import javax.inject.Inject;

public class CreateActivityViewModel extends ViewModel {

    private Repository repo;

    @Inject
    public CreateActivityViewModel(Repository repo) {
        this.repo = repo;
    }

    public void insertTask(UserTask task, InsertTaskCallback callback) {
        repo.insertTask(task, callback);
    }
}
