package com.example.andtdb.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Table1.class}, version = 1, exportSchema = false)
public abstract class Table1Database extends RoomDatabase {
    public abstract Table1Dao table1Dao();
}
