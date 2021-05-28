package ru.startandroid.develop.roomproject.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drivers")
public class Driver {
    @PrimaryKey(autoGenerate = true)
    public long _id;

    @ColumnInfo
    public  String name;

    @ColumnInfo
    public  int is_enabled;

    public Driver(long _id, String name, int is_enabled){
        this._id = _id;
        this.name = name;
        this.is_enabled = is_enabled;
    }

    public long get_id() { return this._id;}
    public String getName() { return this.name;}

    @Override
    public String toString() {
        return "Driver{" +
                "_id=" + _id +
                ", name=" + name  +
                ", is_enabled=" + is_enabled +
                "}";
    }
}
