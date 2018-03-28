package com.example.androidstarter.task;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.AsyncTask;

import com.example.androidstarter.custom.DataViewState;
import com.example.androidstarter.data.database.AppDatabase;
import com.example.androidstarter.data.models.Task;
import com.example.androidstarter.tasks.TasksContract;
import com.example.androidstarter.tasks.TasksPresenter;

import java.util.List;

import timber.log.Timber;

/**
 * Created by samvedana on 26/2/18.
 */

public class TaskPresenter implements TaskContract.Presenter, LifecycleObserver {
    private static TaskPresenter instance;

    private AppDatabase appDatabase;
    private static TaskContract.View taskView;

    private TaskPresenter(AppDatabase database, TaskContract.View view) {
        Timber.d("TasksPresenter constructor");
        appDatabase = database;
        attachView(view);
    }

    public  static TaskPresenter getInstance(AppDatabase database, TaskContract.View view) {
        if (instance == null || taskView == null) {
            //seems hacky for some reason.. dunno why though
            instance = new TaskPresenter(database, view);
        }
        return instance;
    }

    @Override
    public void attachView(TaskContract.View view) {
        if (taskView == null) {
            taskView = view;
            // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
            if (taskView instanceof LifecycleOwner) {
                ((LifecycleOwner) taskView).getLifecycle().addObserver(this);
                Timber.d("TaskPresenter added as observer of %s",
                        taskView.getClass().toString());
            }
        }
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        Timber.d("ON_RESUME event");
        //loadTasks(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        //next time a fresh view ought to be used
        if (taskView != null) {
            ((LifecycleOwner) taskView).getLifecycle().removeObserver(this);
            taskView = null;
        }
    }

    @Override
    public void getTask(long taskId) {

    }

    @Override
    public void saveTask(Task task) {
        Timber.d("in saveTask");
        //query db for tasks
        new TaskPresenter.SaveAsync(appDatabase, taskView).execute(task);
    }

    private static class SaveAsync extends AsyncTask<Task, Void, Void> {
        private AppDatabase appDatabase;
        private TaskContract.View taskView;


        SaveAsync(AppDatabase db, TaskContract.View view) {
            appDatabase = db;
            taskView = view;
        }

        @Override
        protected Void doInBackground(Task... params) {
            Task t = params[0];
            appDatabase.taskDao().insert(t);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskView.taskSaved();
        }
    }
}
