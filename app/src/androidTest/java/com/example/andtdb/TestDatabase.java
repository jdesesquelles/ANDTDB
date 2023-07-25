package com.example.andtdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.example.andtdb.data.Table1;
import com.example.andtdb.data.Table1Dao;
import com.example.andtdb.data.Table1Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class TestDatabase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //@Rule
    //public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    static final String TEST_TABLE1_ID = "0";
    static final String TEST_TABLE1_COL1_LINE1 = "test value table1 col1 line1";
    static final String TEST_TABLE1_COL1_LINE2 = "test value table1 col1 line2";
    private Table1Dao table1Dao;
    public Table1Database db;
    Context context;
    ArrayList<String> tableList;

    private Cursor cursor;
    private SupportSQLiteDatabase db_sql;
    String database_name = "table1_database";
    ArrayList<String> table_name_list;
    ArrayList<String> column_name_list;


    @Before
    public void createDb() {
        //String database_name = "table1_database";
        context = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(context, Table1Database.class, database_name).build();
        table1Dao = db.table1Dao();
        db_sql = db.getOpenHelper().getReadableDatabase();
        tableList = new ArrayList<>();

        table_name_list = new ArrayList<>();
        table_name_list.add("table1");
        column_name_list = new ArrayList<>();
        column_name_list.add("col1");

    }

    private HashSet<String> createTable1ColumnsHashSet() {
        HashSet<String> table1_Column_hashset = new HashSet<>();
        table1_Column_hashset.addAll(column_name_list);
        return table1_Column_hashset;
    }

    private HashSet<String> createTablesHashSet() {
        HashSet<String> tables_hashset = new HashSet<>();
        tables_hashset.addAll(table_name_list);
        return tables_hashset;
    }

    @Test
    public void test01DBCreated() {
        assertNotNull("DB Created", db);
    }

    @Test
    public void test02DBOpen() {
        assertNotNull("DB Created", db);
        assertTrue("DB Open", db.isOpen());
    }

    @Test
    public void test03TableCreated() throws Throwable {
        HashSet<String> tablesHashSet = createTablesHashSet();
        String query_table = "name";
        int idx_name;
        cursor = db_sql.query("SELECT name FROM sqlite_master WHERE type='table'");
        assertTrue("Error: The database has not been created correctly",
                cursor.moveToFirst());
        idx_name = cursor.getColumnIndex(query_table);
        do {
            String columnName = cursor.getString(idx_name);
            tablesHashSet.remove(columnName);
        } while (cursor.moveToNext());
        assertTrue("Error: Database :" + database_name + " doesn't contain all of the required tables" + tablesHashSet,
                tablesHashSet.isEmpty());
        cursor.close();
    }

    @Test
    public void test04ColumnCreated() {
        //Cursor cursor;
        for (int i = 0; i < table_name_list.size(); i++) {


            String table_name = table_name_list.get(i);
            HashSet<String> table1_Column_hashset = createTable1ColumnsHashSet();

            // Fail if DB not created
            if (db_sql == null) {
                fail("No Db to Test table creation");
            }
            if (!db_sql.isOpen()) {
                fail("Db is not open to Test table creation");
            }

            // Getting List of columns for table
            cursor = db.query("PRAGMA table_info(" + table_name + ")", null);

            // Fail if query failed
            assertTrue("Error: Unable to query the database for table information.",
                    cursor.moveToFirst());

            // Checking Columns
            int idxColumnName = cursor.getColumnIndex("name");
            do {
                String columnName = cursor.getString(idxColumnName);
                table1_Column_hashset.remove(columnName);
            } while (cursor.moveToNext());

            assertTrue("Error: Table :" + table_name + " doesn't contain all of the required entry columns" + table1_Column_hashset,
                    table1_Column_hashset.isEmpty());
            cursor.close();
        }
    }

    @Test
    public void test04DaoSetup() {
        assertNotNull("Got DAO", table1Dao);
    }

    // TODO : testupsert
    // TODO : testUpsertAll

    @Test
    public void testFindByID(){
        Long rowId = table1Dao.insert(new Table1("testGet By Id"));
        cursor = db_sql.query("select * from table1 where id = " + rowId);
        assertTrue("test GetById", cursor.moveToFirst());
    }
    @Test
    public void testFindByCol1(){
        String testCol1Value = "testGetByCol1";
        Table1 expectedValue = new Table1(testCol1Value);
        Long rowId = table1Dao.insert(expectedValue);
        rowId = table1Dao.insert(expectedValue);
        // Check Insertion and raw select using rowId
        cursor = db_sql.query("select * from table1 where " + column_name_list.get(0) + " = '" + testCol1Value + "'");
        assertTrue("test GetById", cursor.moveToFirst());
        // Test Dao function
        List<Table1> actual_value;
        // = table1Dao.findByCol1(testCol1Value).observe(this, );
        try {
            actual_value= LiveDataTestUtil.getValue(table1Dao.findByCol1(testCol1Value));
            for (Table1 table1 : actual_value){
                assertEquals(table1.col1, testCol1Value);
            }
        }catch (Exception e){
        }
        table1Dao.deleteAll();
    }

    @Test
    public void test05InsertIntoTable1() {
        table1Dao.deleteAll();
        List<Long> inserted_rows = new ArrayList<>();
        if (db == null) {
            fail("No Db to Test table insertion");
        }
        if (!db.isOpen()) {
            fail("Db is not open to Test table insertion");
        }
        String tableName = "table1";
        //Create List Values
        ContentValues contentValues = new ContentValues();
        contentValues.put("col1", TEST_TABLE1_COL1_LINE1);
        contentValues.put("col1", TEST_TABLE1_COL1_LINE2);

        ArrayList<Table1> table1_values = new ArrayList<>();
        table1_values.add(new Table1(TEST_TABLE1_COL1_LINE1));
        table1_values.add(new Table1(TEST_TABLE1_COL1_LINE2));

        inserted_rows.add(table1Dao.insert(table1_values.get(0)));
        try {
            assertTrue("Table - " + tableName + " Inserting into table failed.", cursor.moveToFirst());
            cursor = db_sql.query("select * from table1 where id = " + inserted_rows.get(0).toString() + ")"); // select where parameter
            for (int i = 0; i < table1_values.size(); i++) {
                validateCursor("Error: " + tableName + " Query Validation Failed", cursor, contentValues);
                assertFalse("Table - " + tableName + " validation of inserted data failed: Data found more than once", cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {}
    }

    @Test
    public void test06UpdateTable1(){
        table1Dao.deleteAll();
        List<Long> inserted_rows = new ArrayList<>();
        if (db == null) {fail("No Db to Test table insertion");
        }
        if (!db.isOpen()) {fail("Db is not open to Test table insertion");}
        String tableName = "table1";
        //Create List Values
        ContentValues contentValues = new ContentValues();
        contentValues.put("col1", TEST_TABLE1_COL1_LINE2);

        Table1 table1_value = new Table1(TEST_TABLE1_COL1_LINE1);
        table1_value.setId(table1Dao.insert(table1_value));
        table1_value.col1 = TEST_TABLE1_COL1_LINE2;
        table1Dao.update(table1_value);

        //assertTrue("Table - " + tableName + " Inserting into table failed.", cursor.moveToFirst());
        cursor = db_sql.query("select * from table1 where id = " + table1_value.getId().toString()); // select where parameter
        validateCursor("Error: " + tableName + " Query Validation Failed", cursor, contentValues);
        assertFalse("Table - " + tableName + " validation of updated data failed: Data found more than once", cursor.moveToNext());
        cursor.close();
    }

    @Test
    public void test06DeleteFromTable1() {
        Table1 testValue = new Table1("test Delete");
        Long rowId;
        table1Dao.deleteAll();
        rowId = table1Dao.insert(testValue);
        testValue.setId(rowId);
        cursor = db_sql.query("select * from table1 where id = " + rowId.toString());
        assertTrue("Row inserted", cursor.moveToFirst());
        table1Dao.delete(testValue);
        cursor = db_sql.query("select * from table1 where id = " + rowId);
        assertFalse(cursor.moveToFirst());
    }

    @Test
    public void test06DeleteAll() {
        table1Dao.insert(new Table1("test1"));
        table1Dao.insert(new Table1("test2"));
        table1Dao.insert(new Table1("test3"));
        table1Dao.deleteAll();
        cursor = db_sql.query("select * from table1"); // select where parameter
        assertFalse(cursor.moveToFirst());

    }

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        do{
            validateCurrentRecord(error, valueCursor, expectedValues);
        }while(valueCursor.moveToNext());
        valueCursor.close();
    }

    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();

            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);

            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + valueCursor.getString(idx) +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    @After
    public void closeDb() {
        db.close();
    }
}
