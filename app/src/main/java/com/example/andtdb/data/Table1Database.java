package com.example.andtdb.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Table1.class}, version = 1, exportSchema = false)
public abstract class Table1Database extends RoomDatabase {
    public abstract Table1Dao table1Dao();
    private static volatile Table1Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                Table1Dao dao = INSTANCE.table1Dao();
                dao.deleteAll();

                Table1 table1 = new Table1("Hello");
                dao.insert(table1);
                table1 = new Table1("World");
                dao.insert(table1);
            });
        }
    };

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
