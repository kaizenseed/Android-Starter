package com.example.androidstarter.tasks;

import com.example.androidstarter.base.BasePresenter;
import com.example.androidstarter.data.models.Task;

import java.util.List;

/**
 * Created by samvedana on 3/1/18.
 */

public interface TasksContract {
    interface View {
        void showTasks(List<Task> tasks);
    }

    interface Presenter extends BasePresenter<TasksContract.View> {
        void loadTasks(boolean onlineRequired);
    }
}
