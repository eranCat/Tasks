package com.erank.tasks.utils.room.callbacks;

import com.erank.tasks.models.UserTask;

import java.util.List;

public interface FetchTasksCallback {
    void onDataFetched(List<UserTask> tasks);
}
