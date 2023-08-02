package com.example.andtdb.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Table1Provider extends ContentProvider {
    private Table1Dao table1Dao;
    private SQLiteDatabase mOpenHelper;
    public Table1Database db;
    String database_name = "table1_database";

    static final int TABLE1 = 100;
    static  final int TABLE1_ITEM = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Table1Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, Table1Contract.PATH_TABLE1, TABLE1);
        matcher.addURI(authority, Table1Contract.PATH_TABLE1 + "/*", TABLE1_ITEM);
        // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = Room.databaseBuilder(getContext(), Table1Database.class, database_name).allowMainThreadQueries().build();
        mOpenHelper =  SQLiteDatabase.openDatabase(getContext().getDatabasePath(database_name).getPath(),null,SQLiteDatabase.OPEN_READWRITE);
       // (SQLiteDatabase) mOpenHelper = db.getOpenHelper().getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            // "weather/*/*"
            case TABLE1:
            {
                retCursor = getTable1();
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getTable1() {

        return sTable1QueryBuilder.query(mOpenHelper,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private static final SQLiteQueryBuilder sTable1QueryBuilder;

    static{
        sTable1QueryBuilder = new SQLiteQueryBuilder();
        sTable1QueryBuilder.setTables(
                Table1Contract.Table1Entry.TABLE_NAME);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
// Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case TABLE1:
                return Table1Contract.Table1Entry.CONTENT_TYPE;
            case TABLE1_ITEM:
                return Table1Contract.Table1Entry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
