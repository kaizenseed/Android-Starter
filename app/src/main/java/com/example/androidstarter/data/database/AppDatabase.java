package com.example.androidstarter.data.database;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.androidstarter.data.models.Task;

import java.util.List;

import static com.example.androidstarter.data.Config.DATABASE_NAME;

/**
 * Created by samvedana on 14/12/17.
 */

@Database(entities = Task.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase dbInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(final Context context) {
        if (dbInstance == null) {
            synchronized (AppDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = buildDatabase(context.getApplicationContext());
                    dbInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return dbInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        new DatabaseAsync(appContext).execute();
                    }
                }).build();
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<Task> tasks) {
        database.runInTransaction(() -> {
            database.taskDao().insertAll(tasks);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private static class DatabaseAsync extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        public DatabaseAsync(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //add delay to ensure dbInstance has been instantiated on return of buildDatabase call
            addDelay();
            // Generate the data for pre-population
            List<Task> tasks = DataGenerator.generateTasks();

            insertData(AppDatabase.getInstance(mContext), tasks);
            // notify that the database was created and it's ready to be used
            AppDatabase.getInstance(mContext).setDatabaseCreated();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }

}

