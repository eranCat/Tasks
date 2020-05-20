package com.erank.tasks.utils.room;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.callbacks.DeleteTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchLiveTasksCallback;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;
import com.erank.tasks.utils.room.callbacks.FetchTasksCallback;
import com.erank.tasks.utils.room.callbacks.InsertTaskCallback;
import com.erank.tasks.utils.room.callbacks.UpdateTaskCallback;

import javax.inject.Singleton;

@Singleton
public interface Repository {
    void insertTask(UserTask task, InsertTaskCallback doneCallback);

    void updateTask(UpdateTaskCallback callback, UserTask... tasks);

    void deleteTask(UserTask task, DeleteTaskCallback callback);

    void getTask(long id, FetchTaskCallback callback);

    void getTasks(FetchLiveTasksCallback callback);

    void getFilteredTasks(TaskState state, FetchTasksCallback callback);

    void getFilteredTasks(String query, FetchTasksCallback callback);

    void getTasksOrderedByState(FetchTasksCallback callback);
}
