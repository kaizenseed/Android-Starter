package com.example.androidstarter.base.activities.behaviours;

import com.example.androidstarter.base.activities.WidgetActivity;

/**
 * Created by samvedana on 25/1/18.
 */

public interface WidgetsAvailable {
    boolean toolbarExists();
    boolean fabExists();
    boolean navDrawerExists();
    boolean rightNavDrawerExists();

    void configureToolbar();
    void configureFab();
    void configureNavDrawer(WidgetActivity activity);

}
