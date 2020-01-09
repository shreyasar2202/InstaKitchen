package com.example.kitchen.instakitchen.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kitchen.instakitchen.data.SpicesContract.SpicesEntry;
/**
 * Created by Namitha on 14-10-2017.
 */

/**
 * Database helper for InstaKitchen app. Manages database creation and version management.
 */

public class SpicesDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = SpicesDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "instakitchen.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SpicesDbHelper}.
     *
     * @param context of the app
     */

    public SpicesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the spices table
        String SQL_CREATE_SPICES_TABLE =  "CREATE TABLE " + SpicesEntry.TABLE_NAME + " ("
                + SpicesEntry.SPICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SpicesEntry.COLUMN_SPICE_NAME + " TEXT NOT NULL, "
                + SpicesEntry.COLUMN_SPICE_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SPICES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SpicesDbHelper.java", "Database version changed");
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
