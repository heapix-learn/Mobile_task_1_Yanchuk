package com.example.first_task_k__r__o__s__h;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.first_task_k__r__o__s__h.MainActivity.DB.AppDatabase;

public class AppContext extends Application {
    private static AppContext instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "database"
        ).allowMainThreadQueries().build();
    }

    public static AppContext getInstance() {
        return instance;
    }
    public AppDatabase getDatabase(){
        return database;
    }
}
