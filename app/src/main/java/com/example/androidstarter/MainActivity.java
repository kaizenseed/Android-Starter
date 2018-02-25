package com.example.androidstarter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidstarter.base.activities.BaseActivity;
import com.example.androidstarter.base.activities.WidgetActivity;
import com.example.androidstarter.custom.DataViewState;
import com.example.androidstarter.custom.MaterialDrawerHelper;
import com.example.androidstarter.data.database.AppDatabase;
import com.example.androidstarter.data.database.UserDao;
import com.example.androidstarter.data.models.Task;
import com.example.androidstarter.data.models.User;
import com.example.androidstarter.tasks.TasksContract;
import com.example.androidstarter.tasks.TasksFragment;
import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setMainDrawer(Drawer drawer) {
        mainDrawer = drawer;
    }

    @Override
    public void configureNavDrawer() {
        new QueryAsync(getMyApplication().getDatabase(), this).execute();
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
    }

    private static class QueryAsync extends AsyncTask<Void, Void, User> {
        private AppDatabase appDatabase;
        private MainActivity activity;

        QueryAsync(AppDatabase db, MainActivity activity) {
            appDatabase = db;
            this.activity = activity;
        }

        @Override
        protected User doInBackground(Void... voids) {
            List<User> users = appDatabase.userDao().getAll();
            /*
            Ideally should directly do get(0) but due to asyncs used for updating db data
            in migrations, onUpgrade & onOpen, mainActivity reaches here before db has finished building
            The right approach would be
            - to use some kind of an eventbus/rxJava to ensure ordering
            - have a better way of loading initial data.
            this issue comes up on directly upgrading from app at DB_V1 tag to here
            => when db upgrade + user adding to db is
            happening along with user being needed for profile in material drawer
             */
            if (users.size() == 0) return null;
            else {
                return users.get(0);
            }
        }

        @Override
        protected void onPostExecute(User user) {
            activity.setMainDrawer(MaterialDrawerHelper.configureStdNavDrawer(activity,
                    activity.toolbar, user));
        }

    }
}
