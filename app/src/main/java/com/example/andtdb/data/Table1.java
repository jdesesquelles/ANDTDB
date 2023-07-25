package com.example.andtdb.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table1")
public class Table1 {
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "col1")
    public String col1;

    public Table1(String col1){
        this.col1 = col1;
    }

    @Ignore
    public void setId(Long id){
        this.id = id;
    }

    @Ignore
    public Long getId(){
        return this.id;
    }
}
