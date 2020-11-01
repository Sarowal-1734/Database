package com.dynamic_host.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamic_host.database.database.BioContract;
import com.dynamic_host.database.database.BioDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    BioCursorAdapter adapter;
    EditText etName, etGender;
    Button btSave, btAllDelete;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        btSave = findViewById(R.id.btSave);
        btAllDelete = findViewById(R.id.btAllDelete);

        adapter = new BioCursorAdapter(this, null);
        ListView bioListView = findViewById(R.id.list);
        bioListView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(0, null, this);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String gender = etGender.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(BioContract.BioEntry.COLUMN_NAME, name);
                values.put(BioContract.BioEntry.COLUMN_GENDER, gender);
                getContentResolver().insert(BioContract.BioEntry.CONTENT_URI, values);
                Toast.makeText(MainActivity.this,"Dat Saved to Database!", Toast.LENGTH_SHORT).show();
            }
        });

        btAllDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowId = getContentResolver().delete(BioContract.BioEntry.CONTENT_URI, null, null);
                if(rowId != 0)
                    Toast.makeText(MainActivity.this,"All Data Deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = ContentUris.withAppendedId(BioContract.BioEntry.CONTENT_URI, id);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {BioContract.BioEntry._ID, BioContract.BioEntry.COLUMN_NAME, BioContract.BioEntry.COLUMN_GENDER};
        //This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, BioContract.BioEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);//Load the cursor
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null); //Reset the cursor
    }
}