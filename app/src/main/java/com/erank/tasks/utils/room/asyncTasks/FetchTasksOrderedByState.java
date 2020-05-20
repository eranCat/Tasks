package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.TasksDao;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;

import java.util.List;

public class FetchTasksOrderedByState extends AsyncTask<Void, Void, List<UserTask>> {
    private TasksDao dao;
    private FetchTasksCallback callback;

    public FetchTasksOrderedByState(TasksDao dao, FetchTasksCallback callback) {
        this.dao = dao;
        this.callback = callback;
    }

    @Override
    protected List<UserTask> doInBackground(Void... voids) {
        return dao.getTasksOrderedByState();
    }

    @Override
    protected void onPostExecute(List<UserTask> tasks) {
        callback.onDataFetched(tasks);
    }
}
