package com.erank.tasks;

import java.util.ArrayList;
import java.util.List;

public class DataManger {
    private static final DataManger ourInstance = new DataManger();

    public List<UserTask> getTasks() {
        return tasks;
    }

    private List<UserTask> tasks;

    public static DataManger getInstance() {
        return ourInstance;
    }

    private DataManger() {
        tasks = new ArrayList<>();
    }

    public void addTask(UserTask userTask) {
        tasks.add(userTask);
    }
}
