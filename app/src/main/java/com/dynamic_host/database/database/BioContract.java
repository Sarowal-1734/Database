package com.dynamic_host.database.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class BioContract {
    public static final String CONTENT_AUTHORITY = "com.dynamic_host.database";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);//content://com.dynamic_host.database
    public static final String PATH_BIOS = "bio";

    private BioContract(){}

    public static final class BioEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BioContract.BASE_CONTENT_URI, PATH_BIOS);
        public final static String TABLE_NAME = "bio";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "Name";
        public final static String COLUMN_GENDER = "Gender";
    }
}
