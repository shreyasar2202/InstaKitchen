package com.example.kitchen.instakitchen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namitha on 03-07-2017.
 */

public class GridSpicesAdapter extends ArrayAdapter {

    List spiceList = new ArrayList();



    public GridSpicesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        spiceList.add(object);
    }

    @Override
    public int getCount() {
        return this.spiceList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.spiceList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ListDataHandler listHandler;
        /*DisplayMetrics displayMetrics=getContext().getResources().getDisplayMetrics();
        int screen_width=displayMetrics.widthPixels;    //width of the device screen
        int screen_height=displayMetrics.heightPixels;   //height of device screen
        int view_width=screen_width/3;   //width for imageview
        int view_height=screen_height/2;   //height for imageview
        */
        if (convertView == null) {
            LayoutInflater listInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = listInflater.inflate(R.layout.grid_layout, parent, false);
            listHandler = new ListDataHandler();

            RelativeLayout reLay = (RelativeLayout) row.findViewById(R.id.relative_grid_layout);
//            int mHeight = ((View) convertView.getParent()).getMeasuredHeight();
//            int mWidth = ((View) convertView.getParent()).getMeasuredWidth();
            DisplayMetrics displayMetrics=getContext().getResources().getDisplayMetrics();
            int screen_width=displayMetrics.widthPixels;    //width of the device screen
            int screen_height=displayMetrics.heightPixels;   //height of device screen

            int view_width=screen_width/3;   //width for relativeview
            int view_height=screen_height/2;   //height for relativeview
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(view_width,view_height);
            reLay.setLayoutParams(layoutParams);
            //int mHeight = ((View) convertView.getParent()).getMeasuredHeight();
            //int mWidth = ((View) convertView.getParent()).getMeasuredWidth();
            //row.setLayoutParams(new GridView.LayoutParams(view_width,view_height));

            listHandler.spiceImage = (ImageView) row.findViewById(R.id.spice_image);
            listHandler.spiceName = (TextView) row.findViewById(R.id.spice_name);
            row.setTag(listHandler);
        }
        else {
            listHandler = (ListDataHandler) row.getTag();
        }
        GridSpicesDataProvider gridSpicesDataProvider = (GridSpicesDataProvider) this.getItem(position);
        listHandler.spiceImage.setImageResource(gridSpicesDataProvider.getSpice_image_resource());
        listHandler.spiceName.setText(gridSpicesDataProvider.getSpice_name());
        Log.d("GridAdaper", "whyyy");

        //int progress =  (int) java.lang.Math.ceil(100*java.lang.Math.random());
        // listHandler.spiceQuantity.setProgress(spiceDataProvider.getProgressValue());
        //row.setLayoutParams(new GridView.LayoutParams(view_width,view_height));

        return row;
    }

    public static class ListDataHandler {
        ImageView spiceImage;
        TextView spiceName;
    }
}