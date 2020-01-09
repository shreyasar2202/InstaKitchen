package com.example.kitchen.instakitchen;

import android.app.LoaderManager;

import android.content.ContentUris;
import android.content.CursorLoader;

import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kitchen.instakitchen.data.FavContract;
import com.example.kitchen.instakitchen.data.FavDbHelper;

public class FavouritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FAV_LOADER = 0;

    FavCursorAdapter mCursorAdapter;

    FavDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        ListView favListView = (ListView) findViewById(R.id.list);

        View emptyView =  findViewById(R.id.empty_view);

        favListView.setEmptyView(emptyView);

        mCursorAdapter = new FavCursorAdapter(this, null);
        favListView.setAdapter(mCursorAdapter);

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Create new intent to go to Editor Activity
                Intent intent  = new Intent(FavouritesActivity.this,RecipeActivity.class);

                //Form the content uri that represents the specific pet that was clicked on
                //by appending the "id"(passed into this method)
                //onto the PetEntry#CONTENTURI
                Uri currentPetUri = ContentUris.withAppendedId(FavContract.FavEntry.CONTENT_URI,id);

                //Set the Uri on the data field of the intent
                intent.setData(currentPetUri);

                //Launch the Editor Activity for current pet info
                startActivity(intent);
            }
        });


        //Start loader
        getLoaderManager().initLoader(FAV_LOADER, null, this);
    }

    public void insertRecipe() {

        mDbHelper = new FavDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FavContract.FavEntry.COLUMN_NAME, "Pesto Sandwich");
        values.put(FavContract.FavEntry.COLUMN_INGREDIENTS, "Bread, Veggies");
        values.put(FavContract.FavEntry.COLUMN_RECIPE, "abcdefg");

        // Insert a new row for Pesto into the provider using the ContentResolver.
        // Use the {@link FavEntry#CONTENT_URI} to indicate that we want to insert
        // into the Favourites database table.
        // Receithe new content URI that will allow us to access Pesto's data in the future.

        Uri newUri = getContentResolver().insert(FavContract.FavEntry.CONTENT_URI, values);

//        long rowId = db.insert(FavContract.FavEntry.TABLE_NAME,null,values);
//
//        String rowIdAsString = Long.toString(rowId);
//
//        TextView textview = (TextView)findViewById(R.id.text_view_demo);
//        textview.setText(rowIdAsString);
    }

    /**
     * Helper method to delete all favourites in the database.
     */
    private void deleteAllFavs() {
        int rowsDeleted = getContentResolver().delete(FavContract.FavEntry.CONTENT_URI, null, null);
        Log.v("FavouritesActivity", rowsDeleted + " rows deleted from Favourites database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertRecipe();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllFavs();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Define a projection that specifies the columns from the table that we care about
        String[] projection={
                FavContract.FavEntry._ID,
                FavContract.FavEntry.COLUMN_NAME};
        //This loader will execute the content provider's query method in the bg
        return new CursorLoader(this,
                FavContract.FavEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Update FavCursorAdapter with this new cursor containing updated fav data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}

