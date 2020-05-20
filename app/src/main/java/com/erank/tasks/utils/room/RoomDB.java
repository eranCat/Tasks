package com.erank.tasks.utils.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.erank.tasks.models.UserTask;

@Database(entities = {UserTask.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class RoomDB extends RoomDatabase {
    private static final String DB_NAME = "db_task";

    public static RoomDB create(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                RoomDB.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract TasksDao tasksDao();
}
