package ru.startandroid.develop.roomproject.roomdb;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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

    void delete(Driver driver) {
        MyDatabase.databaseWriteExecutor.execute(() -> driverDao.delete(driver));
    }

    public Driver getDriver(long id) {
        int count = driverList.getValue().size();
        Driver driver;
        for(int i = 0; i <count; i++)
        {
            driver = driverList.getValue().get(i);
            if( driver._id == id )
                return driver;
        }
        Log.d("myLog","Нет водителей с таким ID");
        return null;
    }

    void deleteAllDrivers() {
        MyDatabase.databaseWriteExecutor.execute(() -> driverDao.deleteAll());
    }
}
