package com.example.androidstarter.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.androidstarter.data.Config;
import com.example.androidstarter.data.models.User;

import java.util.List;

/**
 * Created by samvedana on 26/1/18.
 */


@Dao
public interface UserDao {
    @Query("SELECT * FROM " + Config.USERS_TABLE_NAME)
    public List<User> getAll();

    @Query("SELECT * FROM " + Config.USERS_TABLE_NAME + " WHERE id == :id")
    public User getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("DELETE FROM " + Config.USERS_TABLE_NAME)
    void deleteAll();

    @Query("SELECT COUNT(*) FROM " + Config.USERS_TABLE_NAME)
    int countAll();
}