package com.example.androidstarter.task;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidstarter.MyApplication;
import com.example.androidstarter.R;
import com.example.androidstarter.base.activities.BaseActivity;
import com.example.androidstarter.data.models.Task;
import com.mikepenz.iconics.view.IconicsImageButton;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by samvedana on 26/2/18.
 */

public class TaskFragment extends Fragment implements TaskContract.View {
    @BindView(R.id.task_description_layout) TextInputLayout textInputLayout;
    @BindView(R.id.task_description_edit) EditText taskEditText;
    @BindView(R.id.estimate_options) RecyclerView estimatesRecyclerView;
    @BindView(R.id.save_button) IconicsImageButton saveButton;

    TaskPresenter presenter;
    EstimatesAdapter adapter;


    public TaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("in onCreateView");
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        ButterKnife.bind(this, view);

        presenter = TaskPresenter.getInstance(getMyApplication().getDatabase(), this);

        List<Long> estimates = Arrays.asList(10l, 20l, 30l, 45l, 60l, 90l, 120l, 150l, 180l, 210l);

        // Setup recycler view
        adapter = new EstimatesAdapter(estimates, estimatesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(),
                LinearLayoutManager.HORIZONTAL, false);
        estimatesRecyclerView.setLayoutManager(layoutManager);
        estimatesRecyclerView.setAdapter(adapter);
        // do other adapter + recyclerView tasks like setting onClickListeners, paging, swiping etc

        return view;
    }

    MyApplication getMyApplication() {
        BaseActivity activity = (BaseActivity)getActivity();
        return  activity.getMyApplication();
    }

    @Override
    public void showTask(Task task) {

    }
}
