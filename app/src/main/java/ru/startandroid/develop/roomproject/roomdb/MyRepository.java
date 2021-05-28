package ru.startandroid.develop.roomproject.roomdb;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyRepository {
    private final DriverDao driverDao;
    private final LiveData<List<Driver>> driverList;

    MyRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        driverDao = db.driverDao();
        driverList = driverDao.getAll();
    }

    LiveData<List<Driver>> getAllDrivers() {
        return driverList;
    }

    void insert(Driver driver) {
        MyDatabase.databaseWriteExecutor.execute(() -> driverDao.insert(driver));
    }
}
