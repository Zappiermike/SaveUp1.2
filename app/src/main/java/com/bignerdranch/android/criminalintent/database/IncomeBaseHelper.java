package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomeBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "incomeBase.db";

    public IncomeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ IncomeDbSchema.IncomeTable.NAME+ "("+
                " _id integer primary key autoincrement, " +
                IncomeDbSchema.IncomeTable.Cols.UUID + "," +
                IncomeDbSchema.IncomeTable.Cols.TITLE+ ","+
                IncomeDbSchema.IncomeTable.Cols.DATE + "," +
                IncomeDbSchema.IncomeTable.Cols.AMOUNT +")"
        );
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
