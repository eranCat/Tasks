package com.erank.tasks.utils.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;
import com.erank.tasks.utils.room.callbacks.UpdateTaskCallback;

import java.util.List;

public class Repo {
    private static final String DB_NAME = "db_task";
    private static Repo instance;

    private TasksDao tasksDao;

    private Repo() {
    }

    private static Repo getInstance() {
        return instance != null ? instance : (instance = new Repo());
    }

    public static void initRoom(Context context) {
        RoomDB tasksDb = Room.databaseBuilder(context, RoomDB.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        setDao(tasksDb.tasksDao());
    }

    private static void setDao(TasksDao dao) {
        getInstance().tasksDao = dao;
    }

    public static void insertTask(UserTask task, InsertTaskCallback doneCallback) {
        runOnBackground(() -> dao().insert(task), doneCallback::onInsertDone);
    }

    private static void runOnBackground(Runnable task, Runnable done) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                task.run();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                done.run();
            }
        }.execute();
    }

    public static void updateTask(UpdateTaskCallback callback, UserTask... tasks) {
        runOnBackground(() -> dao().update(tasks), callback::onUpdateTaskDone);
    }

    private static TasksDao dao() {
        return getInstance().tasksDao;
    }

    public static void deleteTask(UserTask task, DeleteTaskCallback callback) {
        runOnBackground(() -> dao().deleteTask(task), callback::onDeleteTaskDone);
    }

    public static void getTask(long id, FetchTaskCallback callback) {
        new AsyncTask<Void, Void, UserTask>() {
            @Override
            protected UserTask doInBackground(Void... voids) {
                return dao().getTask(id);
            }

            @Override
            protected void onPostExecute(UserTask task) {
                callback.onTaskFetched(task);
            }
        }.execute();
    }

    public static void getTasks(FetchLiveTasksCallback callback) {
        new AsyncTask<Void, Void, List<UserTask>>() {
            @Override
            protected List<UserTask> doInBackground(Void... voids) {
                return dao().getTasks();
            }

            @Override
            protected void onPostExecute(List<UserTask> liveData) {
                callback.onLiveTasksFetched(liveData);
            }
        }.execute();
    }

    public static void getFilteredTasks(TaskState state, FetchTasksCallback callback) {
        new AsyncTask<Void, Void, List<UserTask>>() {
            @Override
            protected List<UserTask> doInBackground(Void... voids) {
                return dao().getFilteredByState(state);
            }

            @Override
            protected void onPostExecute(List<UserTask> tasks) {
                callback.onDataFetched(tasks);
            }
        }.execute();
    }

    public static void getFilteredTasks(String query, FetchTasksCallback callback) {
        new AsyncTask<Void, Void, List<UserTask>>() {
            @Override
            protected List<UserTask> doInBackground(Void... voids) {
                return dao().getFiltered("%" + query + "%");
            }

            @Override
            protected void onPostExecute(List<UserTask> tasks) {
                callback.onDataFetched(tasks);
            }
        }.execute();
    }

    public static void getTasksOrderedByState(FetchTasksCallback callback) {
        new AsyncTask<Void, Void, List<UserTask>>() {
            @Override
            protected List<UserTask> doInBackground(Void... voids) {
                return dao().getTasksOrderedByState();
            }

            @Override
            protected void onPostExecute(List<UserTask> tasks) {
                callback.onDataFetched(tasks);
            }
        }.execute();
    }

}
