package com.example.andtdb.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Table1Repository {
    Table1Database table1Database;
    Table1Dao table1Dao;
    private LiveData<List<Table1>> listTable1;

    public Table1Repository(Application application) {
        table1Database = Table1Database.getDatabase(application);
        table1Dao = table1Database.table1Dao();
        listTable1 = table1Dao.getTable1();
    }

    public void insertTable1(Table1 table1) {
        Table1Database.databaseWriteExecutor.execute(() -> table1Dao.insert(table1));
    }

    public LiveData<List<Table1>> getAllTable1() {
        return listTable1;
    }
}
