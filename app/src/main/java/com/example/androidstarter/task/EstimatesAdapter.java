package com.example.androidstarter.task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidstarter.R;
import com.example.androidstarter.utils.DateTimeHelper;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samvedana on 25/2/18.
 */

public class EstimatesAdapter extends RecyclerView.Adapter<EstimatesAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_value) TextView valueTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<Long> estimates;

    public EstimatesAdapter(@NonNull List<Long> estimates) {
        this.estimates = estimates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.estimate_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder; //safe cast
        Long estimate = estimates.get(position);
        vh.valueTextView.setText(DateTimeHelper.getStringDuration(estimate));
    }

    @Override
    public int getItemCount() {
        return estimates.size();
    }

    public Long getItem(int position) {
        if (position < 0 || position >= estimates.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return estimates.get(position);
    }
}
