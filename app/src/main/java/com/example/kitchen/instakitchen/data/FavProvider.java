package com.example.kitchen.instakitchen.data;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


/**
 * Created by Sindhu on 13-08-2017.
 */

public class FavProvider extends ContentProvider {

    //URI matching code for content URI for the Favourites table
    private static final int FAV = 100;
    //URI matching code for content URI for a single recipe in the Favourites table
    private static final int FAV_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FavContract.CONTENT_AUTHORITY, FavContract.PATH_FAV, FAV);
        sUriMatcher.addURI(FavContract.CONTENT_AUTHORITY, FavContract.PATH_FAV + "/#", FAV_ID);
    }

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = FavProvider.class.getSimpleName();
    //Database helper object
    FavDbHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {

        mDbHelper = new FavDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAV:
                // For the FAV code, query the FAV table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the FAV table.
                cursor = database.query(FavContract.FavEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAV_ID:
                // For the FAV_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.FAV/FAV/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = FavContract.FavEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the FAV table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(FavContract.FavEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri + " 2!!!!");
        }
        //Set notification URI on the cursor
        //so we know what content uri the cursor was created for
        //If the data at this uri changes, then we need to update the cursor

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAV:
                return insertFav(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a recipe into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertFav(Uri uri, ContentValues values) {

        // Check that the name is not null
        String name = values.getAsString(FavContract.FavEntry.COLUMN_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Recipe requires a name");
        }

        // Check that the ingredients is not null
        String ingredients = values.getAsString(FavContract.FavEntry.COLUMN_INGREDIENTS);
        if (name == null) {
            throw new IllegalArgumentException("Recipe requires ingredients");
        }

        // Check that the recipe procedure is not null
        String recipeProcedure = values.getAsString(FavContract.FavEntry.COLUMN_RECIPE);
        if (name == null) {
            throw new IllegalArgumentException("Specify procedure for the recipe");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(FavContract.FavEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //Notify all the listeners that the data has changed for the pet content uri
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAV:
                return updateFav(uri, contentValues, selection, selectionArgs);
            case FAV_ID:
                // For the FAV_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = FavContract.FavEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateFav(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update FAV in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more FAV).
     * Return the number of rows that were successfully updated.
     */
    private int updateFav(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // If the {@link FavEntry#COLUMN_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(FavContract.FavEntry.COLUMN_NAME)) {
            String name = values.getAsString(FavContract.FavEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Recipe requires a name");
            }
        }

        // If the {@link FavEntry#COLUMN_INGREDIENTS} key is present,
        // check that the name value is not null.
        if (values.containsKey(FavContract.FavEntry.COLUMN_INGREDIENTS)) {
            String name = values.getAsString(FavContract.FavEntry.COLUMN_INGREDIENTS);
            if (name == null) {
                throw new IllegalArgumentException("Recipe requires ingredients");
            }
        }

        // If the {@link FavEntry#COLUMN_RECIPE} key is present,
        // check that the name value is not null.
        if (values.containsKey(FavContract.FavEntry.COLUMN_RECIPE)) {
            String name = values.getAsString(FavContract.FavEntry.COLUMN_RECIPE);
            if (name == null) {
                throw new IllegalArgumentException("Specify procedure for recipe");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(FavContract.FavEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }


    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAV:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(FavContract.FavEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAV_ID:
                // Delete a single row given by the ID in the URI
                selection = FavContract.FavEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(FavContract.FavEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows deleted
        return rowsDeleted;
    }


    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {

//        final int match = sUriMatcher.match(uri);
//        switch (match) {
//            case FAV:
//                return FavContract.FavEntry.CONTENT_LIST_TYPE;
//            case FAV_ID:
//                return FavContract.FavEntry.CONTENT_ITEM_TYPE;
//            default:
//                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
//        }
        return null;
    }
}

