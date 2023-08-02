package com.example.andtdb;

import android.app.Application;

import androidx.room.Room;

import com.example.andtdb.data.Table1Database;

public class ANDTDBApplication extends Application {

    private Table1Database db;

    public void onCreate(){
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                Table1Database.class, "andtdb1-database").build();
    }

}
