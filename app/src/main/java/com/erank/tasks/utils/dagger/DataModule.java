package com.erank.tasks.utils.dagger;

import android.content.Context;

import com.erank.tasks.utils.room.Repository;
import com.erank.tasks.utils.room.RepositoryImpl;
import com.erank.tasks.utils.room.RoomDB;
import com.erank.tasks.utils.room.TasksDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    public Repository provideRepository(TasksDao dao) {
        return new RepositoryImpl(dao);
    }

    @Singleton
    @Provides
    RoomDB provideDatabase(Context context) {
        return RoomDB.create(context);
    }

    @Provides
    TasksDao provideDatabaseDao(RoomDB database) {
        return database.tasksDao();
    }
}
