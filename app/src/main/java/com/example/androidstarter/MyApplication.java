package com.example.androidstarter;


import android.app.Application;

import com.example.androidstarter.data.database.AppDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

import timber.log.Timber;

/**
 * Created by samvedana on 14/12/17.
 */

public class MyApplication extends Application {

    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        appDatabase = AppDatabase.getInstance(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }

}
