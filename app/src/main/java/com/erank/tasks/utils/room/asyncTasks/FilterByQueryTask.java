package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.TasksDao;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;

import java.util.List;

public class FilterByQueryTask extends AsyncTask<Void, Void, List<UserTask>> {
    private TasksDao dao;
    private String query;
    private FetchTasksCallback callback;

    public FilterByQueryTask(TasksDao dao, String query, FetchTasksCallback callback) {
        this.dao = dao;
        this.query = query;
        this.callback = callback;
    }

    @Override
    protected List<UserTask> doInBackground(Void... voids) {
        return dao.getFiltered(query);
    }

    @Override
    protected void onPostExecute(List<UserTask> tasks) {
        callback.onDataFetched(tasks);
    }
}
