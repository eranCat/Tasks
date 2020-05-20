package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.TasksDao;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;

import java.util.List;

public class FetchLiveTasksTask extends AsyncTask<Void, Void, List<UserTask>> {
    private TasksDao dao;
    private FetchLiveTasksCallback callback;

    public FetchLiveTasksTask(TasksDao dao, FetchLiveTasksCallback callback) {
        this.dao = dao;
        this.callback = callback;
    }

    @Override
    protected List<UserTask> doInBackground(Void... voids) {
        return dao.getTasks();
    }

    @Override
    protected void onPostExecute(List<UserTask> liveData) {
        callback.onLiveTasksFetched(liveData);
    }
}
