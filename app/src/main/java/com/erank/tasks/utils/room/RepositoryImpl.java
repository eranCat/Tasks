package com.erank.tasks.utils.room;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.asyncTasks.FetchLiveTasksTask;
import com.erank.tasks.utils.room.asyncTasks.FetchTaskTask;
import com.erank.tasks.utils.room.asyncTasks.FetchTasksOrderedByState;
import com.erank.tasks.utils.room.asyncTasks.FilterByQueryTask;
import com.erank.tasks.utils.room.asyncTasks.FilterByStateTask;
import com.erank.tasks.utils.room.asyncTasks.GeneraAsyncTask;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;
import com.erank.tasks.utils.room.callbacks.UpdateTaskCallback;

import javax.inject.Inject;

public class RepositoryImpl implements Repository {

    TasksDao dao;

    @Inject
    public RepositoryImpl(TasksDao dao) {
        this.dao = dao;
    }

    @Override
    public void insertTask(UserTask task, InsertTaskCallback doneCallback) {
        runOnBackground(() -> dao.insert(task), doneCallback::onInsertDone);
    }

    @Override
    public void updateTask(UpdateTaskCallback callback, UserTask... tasks) {
        runOnBackground(() -> dao.update(tasks), callback::onUpdateTaskDone);
    }

    @Override
    public void deleteTask(UserTask task, DeleteTaskCallback callback) {
        runOnBackground(() -> dao.deleteTask(task), callback::onDeleteTaskDone);
    }

    @Override
    public void getTask(long id, FetchTaskCallback callback) {
        new FetchTaskTask(dao, id, callback).execute();
    }

    @Override
    public void getTasks(FetchLiveTasksCallback callback) {
        new FetchLiveTasksTask(dao, callback).execute();
    }

    @Override
    public void getFilteredTasks(TaskState state, FetchTasksCallback callback) {
        new FilterByStateTask(dao, state, callback).execute();
    }

    @Override
    public void getFilteredTasks(String query, FetchTasksCallback callback) {
        new FilterByQueryTask(dao, query, callback).execute();
    }

    @Override
    public void getTasksOrderedByState(FetchTasksCallback callback) {
        new FetchTasksOrderedByState(dao, callback).execute();
    }

    private void runOnBackground(Runnable task, Runnable done) {
        new GeneraAsyncTask(task, done).execute();
    }

}

