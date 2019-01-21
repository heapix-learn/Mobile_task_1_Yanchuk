package com.example.first_task_k__r__o__s__h.MainActivity.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.first_task_k__r__o__s__h.ConvertPostForServer;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM convertpostforserver WHERE access = 0")
    List<ConvertPostForServer> getAllPublic();

    @Query("SELECT * FROM convertpostforserver WHERE accountId = :accountId")
    List<ConvertPostForServer> getAllPrivate(String accountId);

    @Query("SELECT * FROM convertpostforserver WHERE id = :id")
    ConvertPostForServer getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConvertPostForServer post );

    @Delete
    void delete(ConvertPostForServer post);
}
