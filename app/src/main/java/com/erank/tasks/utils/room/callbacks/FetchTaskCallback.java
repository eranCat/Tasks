package com.erank.tasks.utils.room.callbacks;

import com.erank.tasks.models.UserTask;

public interface FetchTaskCallback {
    void onTaskFetched(UserTask task);
}
