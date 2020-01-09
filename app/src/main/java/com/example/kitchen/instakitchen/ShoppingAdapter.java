package com.example.kitchen.instakitchen;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;

import static android.content.ContentValues.TAG;
import static com.example.kitchen.instakitchen.ShoppingList.adapter;
import static java.lang.Integer.parseInt;

/**
 * Created by Rohan Simha on 22-06-2017.
 */

public class ShoppingAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ShoppingAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    int k = 0;

    public static class DataHandler {
        TextView item;
        TextView quantity;
        LinearLayout rowl;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View row;
        row = convertView;
        final DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout, parent, false);
            handler = new DataHandler();
            handler.item = (TextView) row.findViewById(R.id.item_name);
            handler.quantity = (TextView) row.findViewById(R.id.quantity);
            handler.rowl = (LinearLayout) row.findViewById(R.id.rowl);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        SLData DataProvider;
        DataProvider = (SLData) this.getItem(position);
        handler.item.setText(DataProvider.getItem());
        handler.quantity.setText(DataProvider.getQuantity());

//        handler.rowl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (k % 2 == 0) {
//                    (handler.item).setPaintFlags((handler.item).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    (handler.quantity).setPaintFlags((handler.quantity).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    k++;
//                } else {
//                    (handler.item).setPaintFlags((handler.item).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                    (handler.quantity).setPaintFlags((handler.quantity).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                    k++;
//                }
//
//            }
//        });

//        handler.rowl.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d(TAG, "Hello 1");
//                final Dialog dialog = new Dialog(getContext().getApplicationContext());
//                dialog.setContentView(R.layout.popup_edit);
//                dialog.setTitle("Edit Quantitiy");
//                Log.d(TAG, "Hello 2");
//                TextView tv1 = (TextView) dialog.findViewById(R.id.item_textView);
//                tv1.setText(handler.item.getText().toString());
//                final EditText et1 = (EditText) dialog.findViewById(R.id.quantity_editText);
//                Log.d(TAG, "Hello 3");
//                et1.setHint(handler.quantity.getText().toString());
//                Button ok = (Button) dialog.findViewById(R.id.edit_button);
//                Log.d(TAG, "Hello 4");
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d(TAG, "Hello 5");
//                        handler.quantity.setText(et1.getText().toString());
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//                Button cancel = (Button) dialog.findViewById(R.id.cancel_button);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d(TAG, "Hello 6");
//                        dialog.cancel();
//                    }
//                });
//                Log.d(TAG, "Hello 55");
//                dialog.show();
//                return true;
//            }});
        return row;

}}