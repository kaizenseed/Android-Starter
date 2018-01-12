package com.example.androidstarter.tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidstarter.R;
import com.example.androidstarter.data.models.Task;
import com.example.androidstarter.utils.DateTimeHelper;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samvedana on 3/1/18.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.status) View statusView; do something with this later
        @BindView(R.id.task_description) TextView descriptionTextView;
        @BindView(R.id.task_estimate) TextView estimateTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<Task> tasks;

    public TasksAdapter(@NonNull List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder; //safe cast
        Task task = tasks.get(position);
        vh.descriptionTextView.setText(task.getDescription());
        vh.estimateTextView.setText(DateTimeHelper.getStringDuration(task.getEstimate()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void replaceData(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public Task getItem(int position) {
        if (position < 0 || position >= tasks.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return tasks.get(position);
    }

    public void clearData() {
        tasks.clear();
        notifyDataSetChanged();
    }
}
