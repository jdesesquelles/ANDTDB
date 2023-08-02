package com.example.andtdb.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Table1Contract {
    public static final String CONTENT_AUTHORITY = "com.example.andtdb";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TABLE1 = "table1";
    public static final String PATH_COL1 = "col1";

    public static final class Table1Entry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TABLE1).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TABLE1;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TABLE1;

        public static final String TABLE_NAME = "table1";
        public static final String COLUMN_TABLE1_COL1 = "col1";

        public static Uri buildTable1Uri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
