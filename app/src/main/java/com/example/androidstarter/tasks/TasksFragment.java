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
import android.widget.Button;
import android.widget.TextView;

import com.example.androidstarter.MyApplication;
import com.example.androidstarter.R;
import com.example.androidstarter.base.BaseActivity;
import com.example.androidstarter.custom.DataViewState;
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
    @BindView(R.id.null_state) View nullStateView;
    @BindView(R.id.status) TextView statusTextView;
    @BindView(R.id.status_description) TextView statusDescriptionTextView;
    @BindView(R.id.action_btn) Button actionButtonView;

    TasksPresenter presenter;
    TasksAdapter adapter;

    DataViewState dataState;
    //todo - check if maintaining DataViewState is actually useful.. maybe in case of config changes?

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
            // this is no data communication.
            switchState(DataViewState.NO_DATA);
        }
        else {
            adapter.replaceData(tasks);
            switchState(DataViewState.DATA_AVAILABLE);
        }
    }

    MyApplication getMyApplication() {
        BaseActivity activity = (BaseActivity)getActivity();
        return  activity.getMyApplication();
    }

    @Override
    public void switchState(DataViewState newState) {
        if (dataState == newState) return;
        switch (newState) {
            case DATA_AVAILABLE:
                recyclerView.setVisibility(View.VISIBLE);
                nullStateView.setVisibility(View.GONE);
                break;
            case NO_DATA:
                initNoDataAvailable();
                recyclerView.setVisibility(View.GONE);
                nullStateView.setVisibility(View.VISIBLE);
                break;
            case NETWORK_ERROR:
                initNetworkError();
                recyclerView.setVisibility(View.GONE);
                nullStateView.setVisibility(View.VISIBLE);
                break;
            default:
                Timber.d("Attempt to switch to invalid state %s", newState);
        }
        dataState = newState;
    }

    private void initNoDataAvailable() {
        statusTextView.setText(R.string.tasks_empty);
        statusDescriptionTextView.setText(R.string.tasks_empty_description);
        actionButtonView.setText(R.string.add_task);
    }

    private void initNetworkError() {
        statusTextView.setText(R.string.network_error);
        statusDescriptionTextView.setText(R.string.network_error_description);
        actionButtonView.setText(R.string.retry);
    }
}
