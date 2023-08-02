package com.example.andtdb.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Table1.class}, version = 1, exportSchema = false)
public abstract class Table1Database extends RoomDatabase {
    public abstract Table1Dao table1Dao();
    private static volatile Table1Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static Table1Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Table1Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Table1Database.class, "andtdb1-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
