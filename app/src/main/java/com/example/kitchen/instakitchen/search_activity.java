package com.example.kitchen.instakitchen;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

public class search_activity extends AppCompatActivity implements LoaderCallbacks<List<Event>> {


    public static final String LOG_TAG = search_activity.class.getSimpleName();

    //private static final String USGS_REQUEST_URL ="https://www.textise.net/showText.aspx?strURL=google.com#main";
    //"https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/761774/analyzedInstructions?mashape-key=CQnGsIdg4HmshN1WucigqAEcVWvhp15Nmc9jsnYYlKCxTHD2Eb&stepBreakdown=false

    private static final String app_key = "CQnGsIdg4HmshN1WucigqAEcVWvhp15Nmc9jsnYYlKCxTHD2Eb";
    private String recipe_to_search;
    private static String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search";
    private static final int SEARCH_LOADER_ID = 1;
    boolean search_valid = false;
    boolean searched=false;
    ProgressBar progressBar;

    /** Adapter for the list of search results */
    private SearchActivityAdapter mSearchAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI(findViewById(R.id.rl1));

        // Find a reference to the {@link ListView} in the layout
        ListView searchListView = (ListView) findViewById(R.id.search_list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        searchListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter
        mSearchAdapter = new SearchActivityAdapter(search_activity.this, R.layout.search_list_layout);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        searchListView.setAdapter(mSearchAdapter);

        // Set an item click listener on the ListView, which sends an intent to the search_result activity
        // to display a page with more information about the selected search result.
//        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Find the current search result that was clicked on
//                Event event = (Event) parent.getItemAtPosition(position);
//
//                Log.d("blah", "HW4");
//
//                //Create new intent to look into the search result
//                Intent resultIntent = new Intent(search_activity.this, search_result.class);
//                Log.d("blah", "HW5");
//
//                String recipeId = event.getID();
//                String recipeName = event.getTitle();
//                Bitmap bitmap = event.getBitmap();
//
//                resultIntent.putExtra("recipename",recipeName);
//                resultIntent.putExtra("recipeId", recipeId);
//
//                String fileName = "myImage";//no .png or .jpg needed
//                try {
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
//                    fo.write(bytes.toByteArray());
//                    // remember close file output
//                    fo.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    fileName = null;
//                }
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                (event.getBitmap()).compress(Bitmap.CompressFormat.PNG, 100, stream);
////                byte[] byteArray = stream.toByteArray();
////                i.putExtra("imagerecipe",byteArray);
//                // Send the intent to launch a new activity
//                startActivity(resultIntent);
//            }
//        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Get a reference to the LoaderManager, in order to interact with loaders.
        final LoaderManager loaderManager = getLoaderManager();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            //  WifiManager wifiManager=(WifiManager) getApplicationContext().getSystemService(contec)
            //WifiInfo wifiInfo=networkInfo.getExtraInfo();
            //int speed=wifiInfo.getLinkSpeed();
            loaderManager.initLoader(SEARCH_LOADER_ID, null, search_activity.this);
            mEmptyStateTextView.setVisibility(View.INVISIBLE);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        //final LoaderManager loaderManager = getLoaderManager(); //loader cannot change that's why final

        //To make enter key launch search when pressed
        final EditText searchText = (EditText) findViewById(R.id.search_text);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    recipe_to_search = searchText.getText().toString().trim();
                    searched = true;
                    if (recipe_to_search != "") {
                        mSearchAdapter.clear();
                        mSearchAdapter.notifyDataSetChanged();
                        mEmptyStateTextView.setVisibility(View.GONE);
                        search_valid = true;
                        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
                        progressBar.setVisibility(View.VISIBLE);
                        loaderManager.restartLoader(SEARCH_LOADER_ID, null, search_activity.this);
                        return true;
                    }
                }
                return false;
            }
        });

        ImageButton search_button = (ImageButton) findViewById(R.id.search_icon);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mEmptyStateTextView.setVisibility(View.INVISIBLE);
                EditText search_text = (EditText) findViewById(R.id.search_text);
                recipe_to_search = search_text.getText().toString().trim();
                searched=true;
                if (recipe_to_search!="") {
                    mSearchAdapter.clear();
                    mSearchAdapter.notifyDataSetChanged();
                    mEmptyStateTextView.setVisibility(View.GONE);
                    search_valid = true;
                    progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
                    progressBar.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(SEARCH_LOADER_ID, null, search_activity.this); //For restarting loader after clicking the search
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    //loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, search_activity.this);

                }
            }
        });
    }

    @Override
    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(BASE_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("mashape-key", app_key);
        uriBuilder.appendQueryParameter("query", recipe_to_search);
        //Log.e("abcdss",uriBuilder.toString());
        return new RecipeLoader(this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(android.content.Loader<List<Event>> loader, List<Event> data) {


        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.INVISIBLE);

        if(data.size()==0 &&searched==true) {mEmptyStateTextView=(TextView)findViewById(R.id.empty_view);
            // Set empty state text to display "No results found."
            mEmptyStateTextView.setText(R.string.no_results);
            Log.e("ASD","NO RESULTS");
            //mSearchAdapter.setE
        }

        // Clear the adapter of previous search data
        mSearchAdapter.clear();
        mSearchAdapter.notifyDataSetChanged();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            for(Event searchEvent: data)
            {
                mSearchAdapter.add(searchEvent);
            }
        }
        updateUi(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Event>> loader) {
        // Loader reset, so we can clear out our existing data.
        mSearchAdapter.clear();
    }
    private void updateUi(List<Event> data) {
        // Display the earthquake title in the UI
        if(data.isEmpty())
        {
            // TextView emptytext = (TextView) findViewById(R.id.emptytext);
            //emptytext.setText("NO SEARCH RESULT FOUND");
            return ;
        }

        ListView searchList;
        SearchActivityAdapter searchAdapter = new SearchActivityAdapter(search_activity.this, R.layout.search_list_layout);
        searchList = (ListView) findViewById(R.id.search_list);
        searchList.setAdapter(searchAdapter);
        for(Event recipeEvent: data)
        {
            searchAdapter.add(recipeEvent);
        }
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);
                Log.d("blah", "HW4");
                Intent i = new Intent(search_activity.this, search_result.class);
                Log.d("blah", "HW5");
                String recipeId = event.getID();
                String recipename = event.getTitle();
                Bitmap bitmap = event.getBitmap();
                i.putExtra("recipename",recipename);
                i.putExtra("recipeId", recipeId);
                String fileName = "myImage";//no .png or .jpg needed
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fo.write(bytes.toByteArray());
                    // remember close file output
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fileName = null;
                }
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                (event.getBitmap()).compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                i.putExtra("imagerecipe",byteArray);
                startActivity(i);
            }
        });
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(search_activity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
