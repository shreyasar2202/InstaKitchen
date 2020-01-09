package com.example.kitchen.instakitchen.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sindhu on 13-08-2017.
 */

public class FavDbHelper extends SQLiteOpenHelper {

    //Name of the database file
    private static final String DATABASE_NAME = "favourites.db";
    //Database Version
    private static final int DATABASE_VERSION = 1;

    public FavDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table pets
        //Create a string to contain SQL statement to create statement
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + FavContract.FavEntry.TABLE_NAME + "("
                + FavContract.FavEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavContract.FavEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + FavContract.FavEntry.COLUMN_INGREDIENTS + " TEXT NOT NULL, "

                + FavContract.FavEntry.COLUMN_RECIPE + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
