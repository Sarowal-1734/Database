package com.dynamic_host.database.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dynamic_host.database.database.BioContract.BioEntry;


public class BioDbHelper extends SQLiteOpenHelper {

    public static final int DATADASE_VERSION = 1;
    public static final String DATADASE_NAME = "bioDB";
    public BioDbHelper(Context context) {
        super(context, DATADASE_NAME, null, DATADASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE "+ BioEntry.TABLE_NAME+
                "("+ BioEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                BioEntry.COLUMN_NAME+" TEXT NOT NULL, "+
                BioEntry.COLUMN_GENDER+" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
