package com.dynamic_host.database.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.dynamic_host.database.MainActivity;

public class BioProvider extends ContentProvider {

    BioDbHelper dbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(BioContract.CONTENT_AUTHORITY, BioContract.PATH_BIOS, 0);
        sUriMatcher.addURI(BioContract.CONTENT_AUTHORITY, BioContract.PATH_BIOS +"/#", 1);
    }



    @Override
    public boolean onCreate() {
        dbHelper = new BioDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case 0:
                cursor = db.query(BioContract.BioEntry.TABLE_NAME, projection, selection, selectionArgs, null,null,sortOrder);
                break;
            case 1:
                selection = BioContract.BioEntry._ID + "=?"; //WHERE _ID=?
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(BioContract.BioEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot Query unknown URI"+ uri);
        }
        //Set Notification UR on the cursor
        // If data changed at this URI, we know we need to update the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case 0:
                return insertBio(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
    }

    private Uri insertBio(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long id = db.insert(BioContract.BioEntry.TABLE_NAME, null, values);
        //Notify all listener that the data has changed for the URI 
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match, i;
        match = sUriMatcher.match(uri);
        switch (match){
            case 0:
                i = db.delete(BioContract.BioEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case 1:
                selection = BioContract.BioEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                i = db.delete(BioContract.BioEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for "+ uri);
        }
        //Notify all listener that the data has changed for the URI 
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match, i;
        match = sUriMatcher.match(uri);
        switch (match){
            case 0:
                return 0;
            case 1:
                selection = BioContract.BioEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                i = db.update(BioContract.BioEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
        //Notify all listener that the data has changed for the URI 
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }
}
