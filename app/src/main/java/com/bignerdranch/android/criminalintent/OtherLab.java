package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.OtherBaseHelper;
import com.bignerdranch.android.criminalintent.database.OtherDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OtherLab {
    private static OtherLab sOtherLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public void deleteOther(Other c){
        mDatabase.delete(OtherDbSchema.OtherTable.NAME,
                OtherDbSchema.OtherTable.Cols.UUID + " = ?", new String[] { c.getId().toString() });
    }

    public void addOther(Other c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(OtherDbSchema.OtherTable.NAME, null, values);
    }

    public static OtherLab get(Context context) {
        if (sOtherLab == null) {
            sOtherLab = new OtherLab(context);
        }

        return sOtherLab;
    }

    private OtherLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new OtherBaseHelper(mContext).getWritableDatabase();
    }

    public List<Other> getOthers() {
        List<Other> others = new ArrayList<>();
        OtherCursorWrapper cursor = queryOthers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                others.add(cursor.getOther());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return others;
    }

    public Other getOther(UUID id) {
        OtherCursorWrapper cursor = queryOthers (
                OtherDbSchema.OtherTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getOther();
        } finally {
            cursor.close();
        }
    }

    public void updateOther(Other other) {
        String uuidString = other.getId().toString();
        ContentValues values = getContentValues(other);

        mDatabase.update(OtherDbSchema.OtherTable.NAME, values,
                OtherDbSchema.OtherTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private OtherCursorWrapper queryOthers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                OtherDbSchema.OtherTable.NAME,
                null, //colums - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null,   //having
                null    //orderBy
        );
        return new OtherCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Other other) {
        ContentValues values = new ContentValues();
        values.put(OtherDbSchema.OtherTable.Cols.UUID, other.getId().toString());
        values.put(OtherDbSchema.OtherTable.Cols.TITLE, other.getTitle());
        values.put(OtherDbSchema.OtherTable.Cols.DATE, other.getDate().getTime());
        values.put(OtherDbSchema.OtherTable.Cols.COST, other.getCost());
        return values;
    }
}
