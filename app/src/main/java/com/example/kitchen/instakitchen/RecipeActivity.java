package com.example.kitchen.instakitchen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kitchen.instakitchen.data.FavContract;

import static android.R.attr.data;

/**
 * Created by Sindhu on 13-08-2017.
 */

public class RecipeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_FAV_LOADER = 0;

    private TextView nameTextView;

    private TextView ingredientsTextView;

    private TextView recipeTextView;

    /**
     * Content URI for the current Fav recipe
     */
    private Uri mCurrentFavUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favrecipe);

        //Examine the intent that was used to launch this activity
        Intent intent = getIntent();
        mCurrentFavUri = intent.getData();

        setTitle(R.string.recipe_activity_title);

        // Initialize a loader to read the Fav data from the database
        // and display the current values in the editor
        getLoaderManager().initLoader(EXISTING_FAV_LOADER, null, this);

        //Find relevant text views
        nameTextView = (TextView) findViewById(R.id.recipe_name);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients);
        recipeTextView = (TextView) findViewById(R.id.recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since this page shows all fav recipe attributes, define a projection that contains
        // all columns from the fav table
        String[] projection = {
                FavContract.FavEntry._ID,
                FavContract.FavEntry.COLUMN_NAME,
                FavContract.FavEntry.COLUMN_INGREDIENTS,
                FavContract.FavEntry.COLUMN_RECIPE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentFavUri,         // Query the content URI for the current fav recipe
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of fav recipe attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(FavContract.FavEntry.COLUMN_NAME);
            int ingredientsColumnIndex = cursor.getColumnIndex(FavContract.FavEntry.COLUMN_INGREDIENTS);
            int recipeColumnIndex = cursor.getColumnIndex(FavContract.FavEntry.COLUMN_RECIPE);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String ingredients = cursor.getString(ingredientsColumnIndex);
            String recipe = cursor.getString(recipeColumnIndex);


            // Update the views on the screen with the values from the database
            nameTextView.setText(name);
            ingredientsTextView.setText("Ingredients:\n");
            ingredientsTextView.append(ingredients);
            recipeTextView.setText("Recipe:\n");
            recipeTextView.append(recipe);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the fields.
        nameTextView.setText("");
        ingredientsTextView.setText("");
        recipeTextView.setText("");
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteFav();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the favourite in the database.
     */
    private void deleteFav() {

        // Call the ContentResolver to delete the favourite at the given content URI.
        // Pass in null for the selection and selection args because the mCurrentFavUri
        // content URI already identifies the fav that we want.
        int rowsDeleted = getContentResolver().delete(mCurrentFavUri, null, null);

        // Show a toast message depending on whether or not the delete was successful.
        if (rowsDeleted == 0) {
            // If no rows were deleted, then there was an error with the delete.
            Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the delete was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                    Toast.LENGTH_SHORT).show();
        }

        // Close the activity
        finish();
    }

}


