package com.erank.tasks.utils.room.asyncTasks;

import android.os.AsyncTask;

public class GeneraAsyncTask extends AsyncTask<Void, Void, Void> {
    private Runnable task;
    private Runnable done;

    public GeneraAsyncTask(Runnable task, Runnable done) {
        this.task = task;
        this.done = done;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        task.run();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        done.run();
    }
}
