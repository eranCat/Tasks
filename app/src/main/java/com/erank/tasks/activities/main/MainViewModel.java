package com.erank.tasks.activities.main;

import androidx.lifecycle.ViewModel;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.Repository;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final Repository repo;

    @Inject
    public MainViewModel(Repository repo) {
        this.repo = repo;
    }

    public void getTasks(FetchLiveTasksCallback callback) {
        repo.getTasks(callback);
    }

    public void getTasksOrderedByState(FetchTasksCallback callback) {
        repo.getTasksOrderedByState(callback);
    }

    public void getFilteredTasks(TaskState state, FetchTasksCallback callback) {
        repo.getFilteredTasks(state, callback);
    }

    public void getFilteredTasks(String query, FetchTasksCallback callback) {
        repo.getFilteredTasks(query, callback);
    }

    public void deleteTask(UserTask task, DeleteTaskCallback callback) {
        repo.deleteTask(task, callback);
    }

    public void insertTask(UserTask task, InsertTaskCallback callback) {
        repo.insertTask(task, callback);
    }
}
