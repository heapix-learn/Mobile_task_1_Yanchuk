package com.example.first_task_k__r__o__s__h.MainActivity.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;

import java.util.List;

@Dao
public interface MapItemDao {
    @Query("SELECT * FROM mapitem WHERE access = 0")
    List<MapItem> getAllPublic();

    @Query("SELECT * FROM mapitem WHERE accountId = :accountId")
    List<MapItem> getAllPrivate(String accountId);

    @Query("SELECT * FROM mapitem WHERE postId = :postId")
    MapItem getByPostId(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MapItem mapItem);

    @Delete
    void delete(MapItem mapItem);
}
