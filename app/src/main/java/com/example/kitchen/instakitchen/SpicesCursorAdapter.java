package com.example.kitchen.instakitchen;

/**
 * Created by Namitha on 15-10-2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kitchen.instakitchen.data.SpicesContract;

/**
 * {@link SpicesCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of spice data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */

public class SpicesCursorAdapter extends CursorAdapter{

    /**
     * Constructs a new {@link SpicesCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    public SpicesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_rowlayout, parent, false);
    }


    /**
     * This method binds the spice data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current spice can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView spiceNameTextView = (TextView) view.findViewById(R.id.spice_name);
        ProgressBar spiceQuantityProgress = (ProgressBar) view.findViewById(R.id.spice_quantity);
        TextView spiceQuantityTextView = (TextView) view.findViewById(R.id.spice_quantity_value);

        // Find the columns of spice attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(SpicesContract.SpicesEntry.COLUMN_SPICE_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(SpicesContract.SpicesEntry.COLUMN_SPICE_QUANTITY);

        // Read the spice attributes from the Cursor for the current pet
        String spiceName = cursor.getString(nameColumnIndex);
        int spiceQuantity = cursor.getInt(quantityColumnIndex);

        int blueColorValue = Color.parseColor("#40C4FF");
        int greenColorValue = Color.parseColor("#76FF03");

//        double quantity = spiceQuantity*1.0;
        int relativeQuantity = (int) (spiceQuantity*100)/500;

        spiceNameTextView.setText(spiceName);
        spiceQuantityProgress.setProgress(relativeQuantity);
        if(relativeQuantity<25)
            spiceQuantityProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        else if (relativeQuantity<70)
            spiceQuantityProgress.getProgressDrawable().setColorFilter(blueColorValue, PorterDuff.Mode.SRC_ATOP);
        else if (relativeQuantity<=100)
            spiceQuantityProgress.getProgressDrawable().setColorFilter(greenColorValue, PorterDuff.Mode.SRC_ATOP);
        spiceQuantityTextView.setText(Integer.toString(spiceQuantity));
    }
}
