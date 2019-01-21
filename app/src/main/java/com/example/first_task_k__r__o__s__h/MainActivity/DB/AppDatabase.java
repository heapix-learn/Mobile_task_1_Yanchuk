package com.example.first_task_k__r__o__s__h.MainActivity.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.first_task_k__r__o__s__h.ConvertPostForServer;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;

@Database(entities = {MapItem.class, ConvertPostForServer.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    abstract MapItemDao mapItemDoa();
    abstract PostDao postDao();
}
