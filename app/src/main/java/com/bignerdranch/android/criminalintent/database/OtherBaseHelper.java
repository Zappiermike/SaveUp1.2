package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.database.OtherDbSchema.OtherTable;

public class OtherBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "otherBase.db";

    public OtherBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + OtherDbSchema.OtherTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                OtherTable.Cols.UUID + ", " +
                OtherTable.Cols.TITLE + ", " +
                OtherTable.Cols.DATE + ", " +
                OtherTable.Cols.COST +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
