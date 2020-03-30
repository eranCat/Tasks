package com.erank.tasks.utils.room;

import android.content.Context;

import androidx.room.Room;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;

import java.util.List;

public class Repo {
    private static Repo instance;
    private TasksDao tasksDao;
    private String DB_NAME = "db_task";

    private Repo() {
    }

    public static Repo getInstance() {
        return instance != null ? instance : (instance = new Repo());
    }

    public void initRoom(Context context) {
        RoomDB tasksDb = Room.databaseBuilder(context, RoomDB.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()//TODO use executors
                .build();

        tasksDao = tasksDb.tasksDao();
    }

    public void insertTask(UserTask... tasks) {
        tasksDao.insert(tasks);
    }

    public void updateTask(UserTask... tasks) {
        tasksDao.update(tasks);
    }

    public void deleteTask(int id) {
        tasksDao.deleteTask(getTask(id));
    }

    public void deleteTask(UserTask task) {
        tasksDao.deleteTask(task);
    }

    public UserTask getTask(long id) {
        return tasksDao.getTask(id);
    }

    public List<UserTask> getTasks() {
        return tasksDao.getTasks();
    }

    public List<UserTask> getFilteredTasks(TaskState state) {
        return tasksDao.getFilteredByState(state);
    }

    public List<UserTask> getTasksOrderedByState() {
        return tasksDao.getTasksOrderedByState();
    }
}
