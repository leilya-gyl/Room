package ru.startandroid.develop.roomproject.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private final MyRepository myRepository;

    private final LiveData<List<Driver>> driverList;

    public MyViewModel(@NonNull Application application) {
        super(application);
        myRepository = new MyRepository(application);
        driverList = myRepository.getAllDrivers();
    }

    public LiveData<List<Driver>> getDrivers() { return driverList;}

    public void insert(Driver driver) { myRepository.insert(driver);}

}
