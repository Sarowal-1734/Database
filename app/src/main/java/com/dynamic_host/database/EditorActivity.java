package com.dynamic_host.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dynamic_host.database.database.BioContract;

public class EditorActivity extends AppCompatActivity {

    Button btUpdate, btDelete;
    EditText etName, etGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        Uri uri = intent.getData();


        etName = findViewById(R.id.et_Name);
        etGender = findViewById(R.id.et_Gender);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);

        String[] projection = {BioContract.BioEntry.COLUMN_NAME, BioContract.BioEntry.COLUMN_GENDER};
        Cursor cursor = getContentResolver().query(uri, projection,null,null,null);
        int nameColumnIndex = cursor.getColumnIndex(BioContract.BioEntry.COLUMN_NAME);
        int genderColumnIndex = cursor.getColumnIndex(BioContract.BioEntry.COLUMN_GENDER);
        if(cursor.moveToFirst())
        {
            String name = cursor.getString(nameColumnIndex);
            String gender = cursor.getString(genderColumnIndex);
            etName.setText(name);
            etGender.setText(gender);
        }


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String gender = etGender.getText().toString().trim();
                ContentValues values = new ContentValues();
                values.put(BioContract.BioEntry.COLUMN_NAME, name);
                values.put(BioContract.BioEntry.COLUMN_GENDER, gender);

                int rowId = getContentResolver().update(uri, values, null, null);
                if(rowId != 0)
                    Toast.makeText(EditorActivity.this,"Data Updated!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowId = getContentResolver().delete(uri, null, null);
                if(rowId != 0)
                    Toast.makeText(EditorActivity.this,"Data Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}