package com.example.androidstarter.tasks;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidstarter.MyApplication;
import com.example.androidstarter.R;
import com.example.androidstarter.base.BaseActivity;
import com.example.androidstarter.data.models.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class TasksFragment extends Fragment implements TasksContract.View {
    @BindView(R.id.recycler_tasks) RecyclerView recyclerView;

    TasksPresenter presenter;
    TasksAdapter adapter;


    public TasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, view);

        presenter = TasksPresenter.getInstance(getMyApplication().getDatabase(), this);

        // Setup recycler view
        adapter = new TasksAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // do other adapter + recyclerView tasks like setting onClickListeners, paging, swiping etc

        return view;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        Timber.d("in showTasks");
        if (tasks.isEmpty()) {
            adapter.clearData();
            // this is no data communication. todo communicate better
        }
        else {
            adapter.replaceData(tasks);
        }
    }

    MyApplication getMyApplication() {
        BaseActivity activity = (BaseActivity)getActivity();
        return  activity.getMyApplication();
    }
}
