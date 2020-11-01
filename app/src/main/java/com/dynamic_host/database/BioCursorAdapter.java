package com.dynamic_host.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dynamic_host.database.database.BioContract;

public class BioCursorAdapter extends CursorAdapter {
    public BioCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_bio, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvGender = view.findViewById(R.id.tvGender);
        int nameColumnIndex = cursor.getColumnIndex(BioContract.BioEntry.COLUMN_NAME);
        int genderColumnIndex = cursor.getColumnIndex(BioContract.BioEntry.COLUMN_GENDER);
        String name = cursor.getString(nameColumnIndex);
        String gender = cursor.getString(genderColumnIndex);
        tvName.setText(name);
        tvGender.setText(gender);
    }
}
