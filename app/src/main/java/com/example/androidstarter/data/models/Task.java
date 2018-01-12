package com.example.androidstarter.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.androidstarter.data.Config;

/**
 * Created by samvedana on 14/12/17.
 */

@Entity(tableName = Config.TASKS_TABLE_NAME)
public class Task {
    @PrimaryKey(autoGenerate = true) public long id;

    String description;
    long estimate; //estimated time to complete in

    public Task(String description, long estimate) {
        this.description = description;
        this.estimate = estimate;
    }

    public String getDescription() {
        return description;
    }

    public long getEstimate() {
        return estimate;
    }
}
