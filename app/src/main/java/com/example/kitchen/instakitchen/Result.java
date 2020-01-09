package com.example.kitchen.instakitchen;

import java.util.ArrayList;

/**
 * Created by Namitha on 14-08-2017.
 */

public class Result {
    private ArrayList<String> mIngredientList = new ArrayList<String>();
    private String mSteps;
    public Result(ArrayList<String>ingredientList,String steps)
    {
        mIngredientList=ingredientList;
        mSteps=steps;
    }
    public ArrayList<String> getIngredienList()
    {return mIngredientList;}

    public String getSteps()
    {return  mSteps;}
}
