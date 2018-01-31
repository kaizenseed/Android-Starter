package com.example.androidstarter.data.database;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.androidstarter.custom.DataViewState;
import com.example.androidstarter.data.models.Task;
import com.example.androidstarter.data.models.User;
import com.example.androidstarter.tasks.TasksContract;

import java.util.List;

import timber.log.Timber;

import static com.example.androidstarter.data.Config.DATABASE_NAME;
import static com.example.androidstarter.data.Config.USERS_TABLE_NAME;

/**
 * Created by samvedana on 14/12/17.
 */

@Database(entities = {Task.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase dbInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public abstract TaskDao taskDao();
    public abstract UserDao userDao();

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
                        Timber.d("buildDatabase : onCreate db version %d", db.getVersion());
                        super.onCreate(db);
                        new FreshDbGenerateAsync(appContext).execute();
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        Timber.d("buildDatabase : onOpen db version %d", db.getVersion());
                        super.onOpen(db);
                        new QueryAsync(AppDatabase.getInstance(appContext)).execute();
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build();
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
    /*
    Migrations for Room
     */

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.d("migrate : %d to %d version", this.startVersion, this.endVersion);
            // Create the new Users table
            String query = "CREATE TABLE " + USERS_TABLE_NAME + " (`id` INTEGER NOT NULL, "
                            + "`name` TEXT, PRIMARY KEY(`id`))";
            database.execSQL(query);
        }
    };

    private static class FreshDbGenerateAsync extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        FreshDbGenerateAsync(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //add delay to ensure dbInstance has been instantiated on return of buildDatabase call
            addDelay();
            // Generate the data for pre-population
            List<Task> tasks = DataGenerator.generateTasks();

            //Create User
            User user = new User("Samvedana Bajpai");

            insertData(AppDatabase.getInstance(mContext), tasks);
            AppDatabase.getInstance(mContext).userDao().insert(user);
            // notify that the database was created and it's ready to be used
            AppDatabase.getInstance(mContext).setDatabaseCreated();
            return null;
        }
    }

    private static class QueryAsync extends AsyncTask<Void, Void, Void> {
        private AppDatabase appDatabase;

        QueryAsync(AppDatabase db) {
            appDatabase = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (appDatabase.userDao().countAll() == 0) {
                Timber.d("Insert user entry in db");
                User user = new User("Samvedana Bajpai");
                appDatabase.userDao().insert(user);
            }
            return null;
        }
    }

}

