package com.example.androidstarter.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.androidstarter.data.Config;
import com.example.androidstarter.data.models.Task;

import java.util.List;

/**
 * Created by samvedana on 14/12/17.
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM " + Config.TASKS_TABLE_NAME)
    public LiveData<List<Task>> getAll();

    @Query("SELECT * FROM " + Config.TASKS_TABLE_NAME + " WHERE id == :id")
    public LiveData<Task> getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Query("DELETE FROM " + Config.TASKS_TABLE_NAME)
    void deleteAll();
}
