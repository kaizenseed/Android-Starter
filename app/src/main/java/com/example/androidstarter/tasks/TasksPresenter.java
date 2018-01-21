package com.example.androidstarter.tasks;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.AsyncTask;

import com.example.androidstarter.custom.DataViewState;
import com.example.androidstarter.data.database.AppDatabase;
import com.example.androidstarter.data.models.Task;

import java.util.List;

import timber.log.Timber;

/**
 * Created by samvedana on 3/1/18.
 */

public class TasksPresenter implements TasksContract.Presenter, LifecycleObserver {
    private static TasksPresenter instance;

    private AppDatabase appDatabase;
    private TasksContract.View tasksView;

    private TasksPresenter(AppDatabase database, TasksContract.View view) {
        Timber.d("TasksPresenter constructor");

        appDatabase = database;
        tasksView = view;

        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (tasksView instanceof LifecycleOwner) {
            ((LifecycleOwner) tasksView).getLifecycle().addObserver(this);
            Timber.d("TasksPresenter added as observer of %s",
                    tasksView.getClass().toString());
        }

    }

    public  static TasksPresenter getInstance(AppDatabase database, TasksContract.View view) {
        if (instance == null) {
            instance = new TasksPresenter(database, view);
        }
        return instance;
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        Timber.d("ON_RESUME event");
        loadTasks(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {

    }

    @Override
    public void loadTasks(boolean onlineRequired) {
        Timber.d("in loadTasks");
        //query db for tasks
        new QueryAsync(appDatabase, tasksView).execute();
    }

    private static class QueryAsync extends AsyncTask<Void, Void, List<Task>> {
        private AppDatabase appDatabase;
        private TasksContract.View tasksView;


        QueryAsync(AppDatabase db, TasksContract.View view) {
            appDatabase = db;
            tasksView = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return appDatabase.taskDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Task> taskList) {
            if (taskList == null) {
                Timber.e("empty task list instance from db");
                tasksView.switchState(DataViewState.NO_DATA);
                return; //this is error or no data condition todo communicate better
            }
            //tasksView.stopLoadingIndicator();
            // no longer needs to be handled since state changes will be handled in showTasks
            tasksView.showTasks(taskList);
        }
    }

}
