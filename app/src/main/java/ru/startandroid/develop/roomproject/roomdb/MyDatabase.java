package ru.startandroid.develop.roomproject.roomdb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Driver.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract DriverDao driverDao();
    //public abstract TestResultDao testResultDao();

    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DriverDao driverDao = INSTANCE.driverDao();
                //driverDao.deleteAll();

                driverDao.insert(new Driver(1, "Sam", 0));
                driverDao.insert(new Driver(2, "Jane", 0));
                driverDao.insert(new Driver(3, "Peter", 0));
            });
        }
    };

    static MyDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "driver_database.db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}