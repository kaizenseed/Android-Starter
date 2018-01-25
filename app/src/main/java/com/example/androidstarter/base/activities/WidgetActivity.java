package com.example.androidstarter.base.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidstarter.R;
import com.example.androidstarter.base.activities.behaviours.WidgetsAvailable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samvedana on 25/1/18.
 */

public abstract class WidgetActivity extends BaseActivity implements
        WidgetsAvailable {
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbarExists()) {
            configureToolbar();
            setSupportActionBar(toolbar);
        }
        else {
            toolbar.setVisibility(View.GONE);
        }
        if (fabExists()) {
            configureFab();
        }
        else {
            fab.setVisibility(View.GONE);
        }
    }
}
