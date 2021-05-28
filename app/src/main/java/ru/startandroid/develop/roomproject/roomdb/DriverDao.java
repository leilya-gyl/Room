package ru.startandroid.develop.roomproject.roomdb;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DriverDao {

    @Query("SELECT * FROM drivers")
    LiveData<List<Driver>> getAll();

    @Query("SELECT * FROM drivers WHERE _id = :id")
    Driver getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)// если запись уже есть в таблице, то старая запись будет заменена на новую
    void insert(Driver driver);

    @Insert(onConflict = OnConflictStrategy.REPLACE)// если запись уже есть в таблице, то старая запись будет заменена на новую
    void insert(List<Driver> driver);

    @Update
    void update(Driver driver);

    @Delete
    void delete(Driver driver);

    @Query("DELETE FROM drivers")
    void deleteAll();
}
