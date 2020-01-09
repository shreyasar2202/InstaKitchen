package com.example.kitchen.instakitchen;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kitchen.instakitchen.data.FavContract;

/**
 * Created by Sindhu on 13-08-2017.
 */

public class FavCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link FavCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public FavCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.fav_list_item, parent, false);
    }

    /**
     * This method binds the favourites data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current recipe can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.dish_name);

        //Find the columns of the attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(FavContract.FavEntry.COLUMN_NAME);

        //Read pet attributes from the cursor for current recipe
        String favName = cursor.getString(nameColumnIndex);

        //Update textview with the attriutes for current recipe
        nameTextView.setText(favName);
    }
}

