package com.example.androidstarter;


import android.app.Application;

import com.example.androidstarter.data.api.RetrofitClient;
import com.example.androidstarter.data.api.UtilsApi;
import com.example.androidstarter.data.database.AppDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by samvedana on 14/12/17.
 */

public class MyApplication extends Application {

    boolean serverlessRetrofit = true;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public Retrofit getRetrofit() {
        return RetrofitClient.getClient(UtilsApi.BASE_URL_API);
    }

}
