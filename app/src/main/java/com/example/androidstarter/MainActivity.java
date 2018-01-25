package com.example.androidstarter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidstarter.base.activities.BaseActivity;
import com.example.androidstarter.base.activities.WidgetActivity;
import com.example.androidstarter.tasks.TasksFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends WidgetActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TasksFragment frag = new TasksFragment();
        setFragment(R.id.fragment, frag, TasksFragment.class.getSimpleName(), true);
    }

    public boolean fabExists() {
        return false;
    }

    public boolean toolbarExists() {
        return true;
    }

    public boolean navDrawerExists() {
        return false;
    }

    public boolean rightNavDrawerExists() {
        return false;
    }

    public void configureToolbar(){
    }

    public void configureFab(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (tag == null) {
            super.onBackPressed();
        }
        else if (tag.equals(TasksFragment.class.getSimpleName())) {
            finish();
        }
    }
}
