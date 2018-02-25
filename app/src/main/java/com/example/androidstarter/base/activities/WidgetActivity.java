package com.example.androidstarter.base.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidstarter.R;
import com.example.androidstarter.base.activities.behaviours.WidgetsAvailable;
//import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

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
        //Define IconicsLayoutInflater to enable automatic xml icons detection
        // see https://github.com/mikepenz/Android-Iconics
//        LayoutInflaterCompat.setFactory2(getLayoutInflater(),
//                new IconicsLayoutInflater2(getDelegate()));
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

        if (toolbarExists() && navDrawerExists()) {
            configureNavDrawer();
            //since this is done programmatically, just skip the step if not used
        }
    }

    // putting this here instead of in MainActivity as each activity would
    // in 99% cases at least have a toolbar
    // possible exceptions could be a splash screen activity, a login activity or any other
    // activity requiring full screen size available for its fragment

    //however the toolbar configuration may optionally change depending on screen context
    public boolean toolbarExists() {
        return true;
    }
}
