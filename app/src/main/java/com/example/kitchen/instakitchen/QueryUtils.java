package com.example.kitchen.instakitchen;

/**
 * Created by Shreyas on 26-07-2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shreyas on 26-07-2017.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    public static List<Event> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Event> recipes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return recipes;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<Event> extractFeatureFromJson(String earthquakeJSON) {

        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        String recipeTitle="";
        String recipePictureUrl="";
        String recipeID="";

        List<Event> recipeList=new ArrayList<Event>();
// Try to parse the JSON response string. If there's a problem with the way the JSON
// is formatted, a JSONException exception object will be thrown.
// Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse=new JSONObject(earthquakeJSON);
            String baseURI=baseJsonResponse.getString("baseUri");
            JSONArray results = baseJsonResponse.getJSONArray("results");
            JSONObject first;
//JSONArray ingredients=recipe.getJSONArray("ingredientLines");
            int i = 0;
            while (i < results.length()) {
                first = results.getJSONObject(i);
                recipeTitle = first.getString("title");
                recipePictureUrl=baseURI+first.getString("image");
                recipeID=first.getString("id");
                URL url = null;
                try {
                    url = new URL(recipePictureUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap recipePicture = null;
                try {
                    recipePicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recipeList.add(new Event(recipeTitle,recipePicture,recipeID));
                //ing1 = ing1 + "TITLE: " + recipe + "\n";
                i++;
            }



        } catch (JSONException e) {
// If an error is thrown when executing any of the above statements in the "try" block,
// catch the exception here, so the app doesn't crash. Print a log message
// with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

// Return the list of earthquakes
        return recipeList;
    }

    public static Result fetchResultData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        Result result = extractResultsFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return result;
    }

    private static Result extractResultsFromJson(String resultJSON) {

        if (TextUtils.isEmpty(resultJSON)) {
            return null;
        }

        String recipeTitle="";
        String recipePictureUrl="";
        String recipeID="";
        int no_of_steps=0;
        String steps="";
        Result result;
        ArrayList<String>ingredients=new ArrayList<String>();
// Try to parse the JSON response string. If there's a problem with the way the JSON
// is formatted, a JSONException exception object will be thrown.
// Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONArray baseJsonResponse=new JSONArray(resultJSON);
            JSONObject ingredientObject=baseJsonResponse.getJSONObject(0);

            JSONArray stepArray=ingredientObject.getJSONArray("steps");
            no_of_steps=stepArray.length();
            for(int i=0;i<no_of_steps;i++) {
                JSONObject step=stepArray.getJSONObject(i);
                steps+=(i+1)+". "+step.getString("step")+"\n\n";
                JSONArray ingredientsList=step.getJSONArray("ingredients");
                int no_of_ingredients=ingredientsList.length();
                for(int j=0;j<no_of_ingredients;j++) {
                    String ing=ingredientsList.getJSONObject(j).getString("name");
                    if(ingredients.contains(ing))
                    {continue;}
                    ingredients.add(ing);
                }

            }
            result=new Result(ingredients,steps);
//JSONArray ingredients=recipe.getJSONArray("ingredientLines");





        } catch (JSONException e) {
// If an error is thrown when executing any of the above statements in the "try" block,
// catch the exception here, so the app doesn't crash. Print a log message
// with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the steps JSON results", e);
            result=null;
        }

// Return the list of earthquakes
        return result;
    }

    public static String emailEncode(String kitchenID) {
        return kitchenID.replace(".",",");
    }
}
