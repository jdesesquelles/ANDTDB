package com.example.andtdb.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface Table1Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Table1.class)
    Long insert(Table1 table1);

   /* @Upsert
    Table1 upsert(Table1 table1);

    @Upsert
    List<Table1> upsertAll(List<Table1> table1_list);*/

    @Update
    void update(Table1 table1);

    @Query("SELECT * from table1 ORDER By col1 Asc")
    LiveData<List<Table1>> getTable1();

    @Query("SELECT * from table1 WHERE col1 = :col1")
    LiveData<List<Table1>> findByCol1(String col1);

    @Query("DELETE from table1")
    void deleteAll();

    @Delete
    void delete(Table1 table1);

}
