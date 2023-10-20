package com.example.lab_4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "todo_database.db";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "TODO_LIST";
    public final static String COL_ID = "ID";
    public final static String COL_TASK = "TASK";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TASK + " text);";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getTasks(){
        List<String> taskNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_TASK};
        Cursor results = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (results != null) {
            while (results.moveToNext()) {
                @SuppressLint("Range") String taskName = results.getString(results.getColumnIndex(COL_TASK));
                taskNames.add(taskName);
            }
            results.close();
        }

        db.close();
        return taskNames;
    }

    public void insertTask(String newItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK, newItem);

        long newRowId = db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void deleteTask(String taskNameDelete) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = COL_TASK + " = ?";
        String[] selectionArgs = { taskNameDelete };

        int deleteRows = db.delete(TABLE_NAME, select, selectionArgs);

        db.close();
    }

    public void printCursor(Cursor c) {
        SQLiteDatabase db = this.getReadableDatabase();
        int dbVersion = db.getVersion();
        Log.d("Database Info", "Database Version: " + dbVersion);

        int numColumns = c.getColumnCount();
        Log.d("Cursor Info", "Number of Columns: " + numColumns);

        String[] columnNames = c.getColumnNames();
        StringBuilder columnInfo = new StringBuilder("Column Names: ");
        for (String columnName : columnNames) {
            columnInfo.append(columnName).append(", ");
        }
        Log.d("Cursor Info", columnInfo.toString());

        int numResults = c.getCount();
        Log.d("Cursor Info", "Number of Results: " + numResults);

        if (c.moveToFirst()) {
            do {
                StringBuilder rowInfo = new StringBuilder("Row: ");
                for (int i = 0; i < numColumns; i++) {
                    rowInfo.append(c.getString(i)).append(", ");
                }
                Log.d("Cursor Info", rowInfo.toString());
            } while (c.moveToNext());
        }
    }
}
