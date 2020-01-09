package com.example.kitchen.instakitchen;

/**
 * Created by Shreyas on 26-07-2017.
 */
import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shreyas on 26-07-2017.
 */

public class RecipeLoader extends AsyncTaskLoader<List<Event>> {
    private static final String LOG_TAG =RecipeLoader.class.getName();
    private String murl;
    public RecipeLoader(Context context, String url)
    {   super(context);
        murl=url;
    }
    @Override
    public List<Event> loadInBackground() {

        if (murl == null) {
            return null;
        }
        List<Event> recipe = QueryUtils.fetchEarthquakeData(murl);
//        List<Event> recipe=new ArrayList<Event>();
        //Event e=new Event(steps,1,2);
  //      recipe.add(steps);

        return  recipe;
    }
    @Override
    protected void onStartLoading()
    {   forceLoad();}

}

