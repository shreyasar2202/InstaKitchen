package com.example.kitchen.instakitchen;

//import android.content.AsyncTaskLoader;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Namitha on 14-08-2017.
 */


public class ResultLoader extends AsyncTaskLoader<Result> {
    private static final String LOG_TAG =RecipeLoader.class.getName();
    private String murl;
    public ResultLoader(Context context, String url)
    {   super(context);
        murl=url;
    }
    @Override
    public Result loadInBackground() {

        if (murl == null) {
            return null;
        }
        Result result = QueryUtils.fetchResultData(murl);
//        List<Event> recipe=new ArrayList<Event>();
        //Event e=new Event(steps,1,2);
        //      recipe.add(steps);
       // List<Result> resultList=new ArrayList<Result>();
        //resultList.add(result);
        return  result;
    }
    @Override
    protected void onStartLoading()
    {   forceLoad();}

}

