package com.erank.tasks.interfaces;

import com.erank.tasks.models.UserTask;

public interface ItemUpdatable {
    void updateItem(UserTask task, int taskPos);
}
