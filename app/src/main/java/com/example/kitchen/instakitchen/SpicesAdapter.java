package com.example.kitchen.instakitchen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namitha on 03-07-2017.
 */

public class SpicesAdapter extends ArrayAdapter {

    List spiceList = new ArrayList();

    public SpicesAdapter(@NonNull Context context, @LayoutRes int resource) {
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
        if (convertView == null) {
            LayoutInflater listInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = listInflater.inflate(R.layout.list_rowlayout, parent, false);
            listHandler = new ListDataHandler();
            listHandler.spiceImage = (ImageView) row.findViewById(R.id.spice_image);
            listHandler.spiceName = (TextView) row.findViewById(R.id.spice_name);
            listHandler.spiceQuanTv=(TextView) row.findViewById(R.id.spice_quantity_value);
            listHandler.spiceQuantity = (ProgressBar) row.findViewById(R.id.spice_quantity);
            row.setTag(listHandler);
        } else {
            listHandler = (ListDataHandler) row.getTag();
        }
        SpicesDataProvider spiceDataProvider = (SpicesDataProvider) this.getItem(position);
//        listHandler.spiceImage.setImageResource(spiceDataProvider.getSpice_image_resource());
        Glide.with(getContext()).load(spiceDataProvider.getSpice_image_resource()).into(listHandler.spiceImage);
        listHandler.spiceName.setText(spiceDataProvider.getSpice_name());
        //int progress =  (int) java.lang.Math.ceil(100*java.lang.Math.random());
        int quantity = spiceDataProvider.getQuanTv();
        String quantity_unit = quantity+"g";
        listHandler.spiceQuanTv.setText(quantity_unit);
        listHandler.spiceQuantity.setProgress(spiceDataProvider.getProgressValue());
        int blueColorValue = Color.parseColor("#40C4FF");
        int greenColorValue = Color.parseColor("#76FF03");
//        int ratio = ((spiceDataProvider.getProgressValue())/SpiceList.max_quantity)*100;
        if(spiceDataProvider.getProgressValue()<25)
            listHandler.spiceQuantity.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        else if (spiceDataProvider.getProgressValue()<70)
            listHandler.spiceQuantity.getProgressDrawable().setColorFilter(blueColorValue, PorterDuff.Mode.SRC_ATOP);
        else if (spiceDataProvider.getProgressValue()<100)
            listHandler.spiceQuantity.getProgressDrawable().setColorFilter(greenColorValue, PorterDuff.Mode.SRC_ATOP);
        return row;
    }

    public static class ListDataHandler {
        ImageView spiceImage;
        TextView spiceName;
        ProgressBar spiceQuantity;
        TextView spiceQuanTv;
    }
}