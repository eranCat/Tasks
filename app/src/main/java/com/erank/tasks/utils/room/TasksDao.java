package com.erank.tasks.utils.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;

import java.util.List;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTask... task);

    @Query("select * from tasks")
    List<UserTask> getTasks();


    @Query("select * from tasks order by state")
    List<UserTask> getTasksOrderedByState();

    @Query("select * from tasks where state = :state")
    List<UserTask> getFilteredByState(TaskState state);

    @Query("select * from tasks where id = :id")
    UserTask getTask(long id);

    @Delete
    void deleteTask(UserTask task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserTask... tasks);


}
