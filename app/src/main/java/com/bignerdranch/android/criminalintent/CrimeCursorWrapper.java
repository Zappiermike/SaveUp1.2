package com.bignerdranch.android.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Michael on 3/19/2018.
 */

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        String cost = getString(getColumnIndex(CrimeTable.Cols.COST));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setCost(cost);

        return crime;
    }
}
