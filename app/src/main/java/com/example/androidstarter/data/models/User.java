package com.example.androidstarter.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.androidstarter.data.Config;

/**
 * Created by samvedana on 26/1/18.
 */
@Entity(tableName = Config.USERS_TABLE_NAME)
public class User {
    @PrimaryKey(autoGenerate = true) public long id;

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
