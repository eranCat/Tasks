package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.TasksDao;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;

public class FetchTaskTask extends AsyncTask<Void, Void, UserTask> {
    private TasksDao dao;
    private long id;
    private FetchTaskCallback callback;

    public FetchTaskTask(TasksDao dao, long id, FetchTaskCallback callback) {
        this.dao = dao;
        this.id = id;
        this.callback = callback;
    }

    @Override
    protected UserTask doInBackground(Void... voids) {
        return dao.getTask(id);
    }

    @Override
    protected void onPostExecute(UserTask task) {
        callback.onTaskFetched(task);
    }
}
