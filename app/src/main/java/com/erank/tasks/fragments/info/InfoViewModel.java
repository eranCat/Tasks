package com.erank.tasks.fragments.info;

import androidx.lifecycle.ViewModel;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.Repository;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;
import com.erank.tasks.utils.room.callbacks.UpdateTaskCallback;

import javax.inject.Inject;

public class InfoViewModel extends ViewModel {
    private Repository repo;

    @Inject
    public InfoViewModel(Repository repo) {
        this.repo = repo;
    }


    public void getTask(long taskID, FetchTaskCallback callback) {
        repo.getTask(taskID, callback);
    }

    public void updateTask(UpdateTaskCallback callback, UserTask... tasks) {
        repo.updateTask(callback, tasks);
    }

    public void deleteTask(UserTask task, DeleteTaskCallback callback) {
        repo.deleteTask(task, callback);
    }
}
