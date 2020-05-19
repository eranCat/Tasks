package com.erank.tasks.activities;

import androidx.lifecycle.ViewModel;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.Repo;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    public MainViewModel() {
    }

    public void getTasks(FetchLiveTasksCallback callback) {
        Repo.getTasks(callback);
    }

    public void getTasksOrderedByState(FetchTasksCallback callback) {
        Repo.getTasksOrderedByState(callback);
    }

    public void getFilteredTasks(TaskState state, FetchTasksCallback callback) {
        Repo.getFilteredTasks(state, callback);
    }

    public void getFilteredTasks(String query, FetchTasksCallback callback) {
        Repo.getFilteredTasks(query, callback);
    }

    public void deleteTask(UserTask task, DeleteTaskCallback callback) {
        Repo.deleteTask(task, callback);
    }

    public void insertTask(UserTask task, InsertTaskCallback callback) {
        Repo.insertTask(task, callback);
    }
}
