package com.bignerdranch.android.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.database.OtherDbSchema;

import java.util.Date;
import java.util.UUID;

public class OtherCursorWrapper extends CursorWrapper {

    public OtherCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Other getOther() {
        String uuidString = getString(getColumnIndex(OtherDbSchema.OtherTable.Cols.UUID));
        String title = getString(getColumnIndex(OtherDbSchema.OtherTable.Cols.TITLE));
        long date = getLong(getColumnIndex(OtherDbSchema.OtherTable.Cols.DATE));
        String cost = getString(getColumnIndex(OtherDbSchema.OtherTable.Cols.COST));

        Other crime = new Other(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setCost(cost);

        return crime;
    }

}

