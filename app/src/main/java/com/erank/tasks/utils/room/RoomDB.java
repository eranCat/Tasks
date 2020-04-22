package com.erank.tasks.utils.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.erank.tasks.models.UserTask;

@Database(entities = {UserTask.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class RoomDB extends RoomDatabase {
    public abstract TasksDao tasksDao();
}
