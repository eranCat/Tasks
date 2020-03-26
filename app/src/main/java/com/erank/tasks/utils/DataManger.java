package com.erank.tasks.utils;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;

import java.util.ArrayList;
import java.util.List;

public class DataManger {
    private static final DataManger ourInstance = new DataManger();
    private List<UserTask> tasks;

    private DataManger() {
        tasks = new ArrayList<>();
    }

    public static DataManger getInstance() {
        return ourInstance;
    }

    public List<UserTask> getTasks() {
        return tasks;
    }

    public void addTask(UserTask userTask) {
        tasks.add(userTask);
    }

    public List<UserTask> getFiltered(TaskState state) {
        List<UserTask> filtered = new ArrayList<>();
        for (UserTask task : tasks) {
            if (task.getState() == state)
                filtered.add(task);
        }
        return filtered;
    }
}
