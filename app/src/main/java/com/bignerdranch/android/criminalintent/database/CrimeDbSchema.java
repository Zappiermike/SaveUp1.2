package com.bignerdranch.android.criminalintent.database;

/**
 * Created by Michael on 3/19/2018.
 */

public class CrimeDbSchema {

    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COST = "cost";
        }
    }
}
