package com.example.androidstarter.task;

import com.example.androidstarter.base.BasePresenter;
import com.example.androidstarter.data.models.Task;

/**
 * Created by samveda on 26/2/18.
 */

public interface TaskContract {
    // The plan is to reuse the same for creating and editing a task
    interface View {
        void showTask(Task task);
        void taskSaved();
    }
    interface Presenter extends BasePresenter<TaskContract.View> {
        void getTask(long taskId);
        void saveTask(Task task);
    }
}
