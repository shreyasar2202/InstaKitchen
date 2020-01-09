package com.example.kitchen.instakitchen;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kitchen.instakitchen.data.FavContract;
import com.example.kitchen.instakitchen.data.FavDbHelper;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

import static com.example.kitchen.instakitchen.R.id.dish_name;

public class search_result extends AppCompatActivity implements LoaderCallbacks<Result> {

    FavDbHelper mDbHelper;
    TextView dish_name;


    public String RECIPE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes";
    private static final String app_key = "CQnGsIdg4HmshN1WucigqAEcVWvhp15Nmc9jsnYYlKCxTHD2Eb";
    //"/analyzedInstructions\n";
    String recipeId;
    private static final int RESULT_LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("blah", "HW8");
        super.onCreate(savedInstanceState);
        Log.d("blah", "HW9");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_search_result);
        Log.d("blah", "HW10");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        final TextView dish_recipe = (TextView) findViewById(R.id.dish_recipe);
        ImageView dish_image = (ImageView) findViewById(R.id.dish_img);
        //TextView dish_names = (TextView) findViewById(R.id.dish_name);
        final TextView ing_blah = (TextView) findViewById(R.id.ing_blah);
        ing_blah.setVisibility(View.INVISIBLE);
        final TextView recipe_blah = (TextView) findViewById(R.id.recipe_blah);
        recipe_blah.setVisibility(View.INVISIBLE);
        final TextView dish_ing = (TextView) findViewById(R.id.dish_ingredients);
        dish_name = (TextView) findViewById(R.id.dish_name);

        Bitmap bitmap = null;
        //String filename = getIntent().getStringExtra("image");
        try {
            bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput("myImage"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        byte[] byteArray = getIntent().getByteArrayExtra("imagerecipe");
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        dish_image.setImageBitmap(bitmap);
        recipeId = getIntent().getStringExtra("recipeId");
        final String recipenames = getIntent().getStringExtra("recipename");
        getSupportActionBar().setTitle(recipenames);
        final LoaderManager loaderManager = getLoaderManager(); //loader cannot change thats why final
        loaderManager.initLoader(RESULT_LOADER_ID, null, search_result.this);

        LikeButton likeButton = (LikeButton) findViewById(R.id.fav_button);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mDbHelper = new FavDbHelper(search_result.this);
                dish_name = (TextView) findViewById(R.id.dish_name);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(FavContract.FavEntry.COLUMN_NAME, recipenames);
                values.put(FavContract.FavEntry.COLUMN_INGREDIENTS, dish_ing.getText().toString());
                values.put(FavContract.FavEntry.COLUMN_RECIPE, dish_recipe.getText().toString());

                // Insert a new row for Pesto into the provider using the ContentResolver.
                // Use the {@link FavEntry#CONTENT_URI} to indicate that we want to insert
                // into the Favourites database table.
                // Receithe new content URI that will allow us to access Pesto's data in the future.

                Uri newUri = getContentResolver().insert(FavContract.FavEntry.CONTENT_URI, values);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(true);
            }
        });
    }

//    public void addToFav() {
//
//        mDbHelper = new FavDbHelper(this);
//
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(FavContract.FavEntry.COLUMN_NAME, "Pesto Sandwich");
//        values.put(FavContract.FavEntry.COLUMN_INGREDIENTS, "Bread, Veggies");
//        values.put(FavContract.FavEntry.COLUMN_RECIPE, "abcdefg");
//
//        // Insert a new row for Pesto into the provider using the ContentResolver.
//        // Use the {@link FavEntry#CONTENT_URI} to indicate that we want to insert
//        // into the Favourites database table.
//        // Receithe new content URI that will allow us to access Pesto's data in the future.
//
//        Uri newUri = getContentResolver().insert(FavContract.FavEntry.CONTENT_URI, values);
//
//
//    }

    //@Override
//    public Loader<ListResult> onCreateLoader(int id, Bundle args) {
//        Uri baseUri = Uri.parse(RECIPE_URL);
//
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//        uriBuilder.appendPath(recipeId);
//        uriBuilder.appendPath("analyzedInstructions");
//        uriBuilder.appendQueryParameter("mashape-key", app_key);
//        uriBuilder.appendQueryParameter("stepBreakdown","true");
//        //Log.e("abcdss",uriBuilder.toString());
//        return new ResultLoader(this,uriBuilder.toString());
//    }

    @Override
    public Loader<Result> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(RECIPE_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(recipeId);
        uriBuilder.appendPath("analyzedInstructions");
        uriBuilder.appendQueryParameter("mashape-key", app_key);
        uriBuilder.appendQueryParameter("stepBreakdown", "true");
        //Log.e("abcdss",uriBuilder.toString());
        return new ResultLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<Result> loader, Result data) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        TextView dish_ing = (TextView) findViewById(R.id.dish_ingredients);
        TextView dish_recipe = (TextView) findViewById(R.id.dish_recipe);
        TextView ing_blah = (TextView) findViewById(R.id.ing_blah);
        ing_blah.setVisibility(View.VISIBLE);
        TextView recipe_blah = (TextView) findViewById(R.id.recipe_blah);
        recipe_blah.setVisibility(View.VISIBLE);
        if (data != null) {

            String ingredientsList = "";

            ArrayList<String> ingredients = data.getIngredienList();
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsList += (i + 1) + ". " + ingredients.get(i) + "\n";
            }
            String steps = data.getSteps();

            dish_ing.setText(ingredientsList);
            dish_recipe.setText(steps);
        }

        if (data == null) {
            dish_ing.setVisibility(View.INVISIBLE);
            ing_blah.setVisibility(View.INVISIBLE);
            recipe_blah.setVisibility(View.INVISIBLE);
            //TextView dish_name = (TextView) findViewById(R.id.dish_name);
            dish_name.setText("NO DATA FOUND");
            dish_name.setGravity(View.TEXT_ALIGNMENT_CENTER);
            dish_recipe.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Result> loader) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

}
