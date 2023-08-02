package com.example.andtdb;

import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.andtdb.data.Table1;
import com.example.andtdb.data.Table1Contract;
import com.example.andtdb.data.Table1Contract.Table1Entry;
import com.example.andtdb.data.Table1Dao;
import com.example.andtdb.data.Table1Database;
import com.example.andtdb.data.Table1Provider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class TestContentProvider {

    Context mContext;
    private Table1Dao table1Dao;
    public Table1Database db;
    String database_name = "table1_database";


    @Before
    public void setup(){
        mContext = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(mContext, Table1Database.class, database_name).build();
        table1Dao = db.table1Dao();
    }

    // Contract : Test URI Build
    /*@Test
    public void testBuildTable1WithCol1(){
        Uri locationUri
    }*/

    //testTable1Queries

    @Test
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                Table1Provider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            assertEquals("Error: Provider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + Table1Contract.CONTENT_AUTHORITY,
                    providerInfo.authority, Table1Contract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: Provider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    @Test
    public void testGetType() {
        // content://.../table1/
        String type = mContext.getContentResolver().getType(Table1Entry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the Table1Entry CONTENT_URI should return Table1Entry.CONTENT_TYPE",
                Table1Entry.CONTENT_TYPE, type);

        Long testId = table1Dao.insert(new Table1("test Value"));
        // content://com.example.android.sunshine.app/weather/94074
        type = mContext.getContentResolver().getType(
                Table1Entry.buildTable1Uri(testId));
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the Table1Entry CONTENT_URI with location should return Table1Entry.CONTENT_TYPE",
                Table1Entry.CONTENT_ITEM_TYPE, type);
    }

    @Test
    public void testGetTable1Query() {
        TestUtils.deleteAll(table1Dao);
        ContentValues contentValues = new ContentValues();
        contentValues.put("col1", TestConstant.TEST_TABLE1_COL1_LINE1);
        contentValues.put("col1", TestConstant.TEST_TABLE1_COL1_LINE2);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mContext.getDatabasePath(database_name).getPath(),null,SQLiteDatabase.OPEN_READWRITE);
        long table1RowId = db.insert(Table1Entry.TABLE_NAME, null, contentValues);
        assertTrue("Unable to Insert Table1Entry into the Database", table1RowId != -1);
        db.close();
        Cursor table1Cursor = mContext.getContentResolver().query(
                Table1Entry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtils.validateCursor("testBasicTable1Query", table1Cursor, contentValues);
    }

    @Test
    public void testTable1Contract(){

    }

    @After
    public void Terminate(){

    }

}
