package com.erank.tasks.interfaces;

import com.erank.tasks.models.UserTask;

public interface TaskClickCallback {
    void onTaskTapped(UserTask task, int pos);
}
