package com.example.kitchen.instakitchen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.start;

/**
 * Created by Rohan Simha on 30-07-2017.
 */

public class SearchActivityAdapter extends ArrayAdapter implements Serializable{

    List searchList = new ArrayList();

    public SearchActivityAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        searchList.add(object);
    }

    @Override
    public int getCount() {
        return this.searchList.size();
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return (Event) this.searchList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        SearchDataHandler searchHandler;
        if (convertView == null) {
            LayoutInflater listInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = listInflater.inflate(R.layout.search_list_layout, parent, false);
            searchHandler = new SearchDataHandler();
            searchHandler.recipeImage = (ImageView) row.findViewById(R.id.recipe_image);
            searchHandler.recipeName = (TextView) row.findViewById(R.id.recipe_name);
            searchHandler.row_recipe = (LinearLayout) row.findViewById(R.id.recipe_row);
            row.setTag(searchHandler);
        }
        else{
            searchHandler = (SearchDataHandler) row.getTag();
        }
        Event recipeEvent = (Event) this.getItem(position);
        Bitmap bmp = recipeEvent.getBitmap();
        searchHandler.recipeImage.setImageBitmap(bmp);
        searchHandler.recipeName.setText(recipeEvent.getTitle());
        searchHandler.ID = recipeEvent.ID;

//        searchHandler.row_recipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), search_result.class);
//
//            }
//        });
        return row;
    }


    public static class SearchDataHandler implements  Serializable{
        ImageView recipeImage;
        TextView recipeName;
        LinearLayout row_recipe;
        String ID;
    }
}
