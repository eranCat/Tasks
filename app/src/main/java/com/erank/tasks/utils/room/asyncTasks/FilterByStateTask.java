package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.TasksDao;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;

import java.util.List;

public class FilterByStateTask extends AsyncTask<Void, Void, List<UserTask>> {
    private TasksDao dao;
    private TaskState state;
    private FetchTasksCallback callback;

    public FilterByStateTask(TasksDao dao, TaskState state, FetchTasksCallback callback) {
        this.dao = dao;
        this.state = state;
        this.callback = callback;
    }

    @Override
    protected List<UserTask> doInBackground(Void... voids) {
        return dao.getFilteredByState(state);
    }

    @Override
    protected void onPostExecute(List<UserTask> liveData) {
        callback.onDataFetched(liveData);
    }
}
