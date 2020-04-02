package com.erank.tasks.utils.room.callbacks;

import com.erank.tasks.models.UserTask;

import java.util.List;

public interface FetchLiveTasksCallback {
    void onLiveTasksFetched(List<UserTask> tasks);
}
