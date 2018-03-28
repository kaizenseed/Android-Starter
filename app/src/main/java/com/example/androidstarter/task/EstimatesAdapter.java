package com.example.androidstarter.task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidstarter.R;
import com.example.androidstarter.utils.DateTimeHelper;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by samvedana on 25/2/18.
 */

public class EstimatesAdapter extends RecyclerView.Adapter<EstimatesAdapter.ViewHolder> {
    List<Long> estimates;
    int selectedIndex = -1;
    RecyclerView recyclerViewInstance;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_value) TextView valueTextView;
        @BindView(R.id.cell) LinearLayout cellLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public EstimatesAdapter(@NonNull List<Long> estimatesList, RecyclerView recyclerView) {
        estimates = estimatesList;
        recyclerViewInstance = recyclerView;
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
        vh.cellLayout.setSelected(position != RecyclerView.NO_POSITION &&
                position == selectedIndex);

        vh.cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = vh.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION &&
                        clickedPosition != selectedIndex) {
                    if (selectedIndex != -1) {
                        ViewHolder selectedHolder =
                                (ViewHolder) recyclerViewInstance.findViewHolderForAdapterPosition(selectedIndex);
                        selectedHolder.cellLayout.setSelected(false);
                    }
                    selectedIndex = clickedPosition;
                    // We can access the data within the views
                    vh.cellLayout.setSelected(true);
                    Timber.d("selected cell with index %d, value %s",
                            selectedIndex, vh.valueTextView.getText().toString());
                }
                else {
                    selectedIndex = -1;
                    vh.cellLayout.setSelected(false);
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        Timber.d("deselected cell with index %d, value %s",
                                clickedPosition, vh.valueTextView.getText().toString());
                    }
                }
            }
        });
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

    public Long getSelectedEstimate() {
        if (selectedIndex != -1) {
            return getItem(selectedIndex);
        }
        else {
            return 0l;
        }
    }
}
