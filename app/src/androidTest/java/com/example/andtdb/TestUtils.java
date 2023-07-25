package com.example.andtdb;

import com.example.andtdb.data.Table1;

public class TestUtils {
    static final String TEST_TABLE1_ID = "0";
    static final String TEST_TABLE1_COL1 = "test value table1 col1";

    public static Table1 createTable1(String col1){
        Table1 table1 = new Table1(col1);
        return table1;
    }

   /* public static ContentValues createTable1ListValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", TEST_TABLE1_ID);
        contentValues.put("col1", TEST_TABLE1_COL1);
        return contentValues;
    }
    public static void testTableInsertion(ContentValues testValues, String tableName, String selectionParam, SQLiteDatabase db) {
        Cursor cursor;
        if (db == null) {fail("No Db to Test table insertion");}
        if (!db.isOpen()) {fail("Db is not open to Test table insertion");}
        
        cursor = db.query(
                tableName,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        assertTrue("Table - " + tableName + " Inserting into table failed.", cursor.moveToFirst());
        validateCursor("Error: " + tableName + " Query Validation Failed", cursor, testValues);
        assertFalse("Table - " + tableName + " validation of inserted data failed: Data found more than once", cursor.moveToNext());
        cursor.close();

        int rowDeleted = db.delete(tableName, selectionParam + "=?", new String[] {testValues.getAsString(selectionParam)});
        assertEquals("Could not delete the row from table :" + tableName, 1, rowDeleted);
    }

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
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
    public static void testTableCreation(String tableName, HashSet<String> ColumnHashSet, SQLiteDatabase db) {
        Cursor cursor;
        if (db == null) {
            fail("No Db to Test table creation");
        }

        if (!db.isOpen()) {
            fail("Db is not open to Test table creation");
        }

        cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")",
                null);

        assertTrue("Error: Unable to query the database for table information.",
                cursor.moveToFirst());

        int columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            ColumnHashSet.remove(columnName);
        } while (cursor.moveToNext());

        assertTrue("Error: Table :" + tableName + " doesn't contain all of the required entry columns" + ColumnHashSet.toString(),
                ColumnHashSet.isEmpty());
        cursor.close();

    }

*/
}
