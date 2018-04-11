package com.example.androidstarter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidstarter.base.activities.WidgetActivity;
import com.example.androidstarter.custom.MaterialDrawerHelper;
import com.example.androidstarter.data.models.User;
import com.example.androidstarter.task.TaskFragment;
import com.example.androidstarter.tasks.TasksFragment;
import com.mikepenz.materialdrawer.Drawer;

import timber.log.Timber;

public class MainActivity extends WidgetActivity {
    Drawer mainDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("in onCreate");
        super.onCreate(savedInstanceState);

        TasksFragment frag = new TasksFragment();
        setFragment(R.id.fragment, frag, TasksFragment.class.getSimpleName(), true);
    }

    @Override
    public boolean fabExists() {
        return true;
    }

    @Override
    public boolean navDrawerExists() {
        return true;
    }

    @Override
    public boolean rightNavDrawerExists() {
        return false;
    }

    @Override
    public void configureToolbar() {
    }

    @Override
    public void configureFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                TaskFragment fragment = new TaskFragment();
                replaceFragment(R.id.fragment, fragment, TaskFragment.class.getSimpleName(), true);
            }
        });
    }

    public void setMainDrawer(Drawer drawer) {
        mainDrawer = drawer;
    }

    @Override
    public void configureNavDrawer(WidgetActivity activity) {
        Timber.d("in configureNavDrawer");
        LiveData<User> user = getMyApplication().getDatabase().userDao().getMyUser();

        // Create the observer which updates the UI.
        final Observer<User> userObserver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User newUser) {
                Timber.d("User changed to %s", user.toString());
                // Update the UI, in this case, a nav drawer header.
                setMainDrawer(MaterialDrawerHelper.configureStdNavDrawer(activity,
                        toolbar, newUser));

            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        user.observe(this, userObserver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String tag = getCurrentTag();
        Timber.d("Back pressed with current tag : %s", tag);
        if (tag == null) {
            super.onBackPressed();
        } 
        else if (tag.equals(TasksFragment.class.getSimpleName())) {
            Timber.d("finish called.");
            finish();
        }
        else if (tag.equals(TaskFragment.class.getSimpleName())) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to go back? " +
                            "You will lose unsaved changes.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.super.onBackPressed();
                            fab.setVisibility(View.VISIBLE);
                            popFragmentBackStack();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}
