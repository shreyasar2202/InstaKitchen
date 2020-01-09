package com.example.kitchen.instakitchen;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

import android.app.Fragment;

import static java.lang.Integer.parseInt;

public class ShoppingList extends AppCompatActivity {

    public static ListView mainListView;
    static String[] item;
    static String[] quantity;
    public static ShoppingAdapter adapter;

    public void additem(View v) {
        popup mydialog = new popup();
        mydialog.show(getSupportFragmentManager(), "my_dialog");
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Button addbtn = (Button) findViewById(R.id.addbtn);
        ActionBar actionBar = getSupportActionBar();
        super.onCreate(savedInstanceState);
        actionBar.hide();
        setContentView(R.layout.activity_shopping_list);
        mainListView = (ListView) findViewById(R.id.list);
        adapter = new ShoppingAdapter(getApplicationContext(), R.layout.row_layout);
        int i = 0;
        item = getResources().getStringArray(R.array.item_name);
        quantity = getResources().getStringArray(R.array.quantity);
        for (String items : item) {
            SLData DataProvider = new SLData(item[i], quantity[i]);
            adapter.add(DataProvider);
            i++;
        }
        mainListView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int a[] = new int [100];
        int j;
        for(j=0;j<100;j++)
            a[j]=0;
        mainListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(a[position]%2==0)
                    view.setAlpha(0.3f);
                else
                    view.setAlpha(1f);
                a[position]++;
            }
        });

        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final SLData currentItem = (SLData) parent.getItemAtPosition(position);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShoppingList.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                TextView nameTextView = (TextView) mView.findViewById(R.id.item_textView);
                nameTextView.setText(currentItem.getItem());

                final EditText quantityEditText = (EditText) mView.findViewById(R.id.quantity_editText);
                final int initialQuantity = parseInt(currentItem.getQuantity());

                Button cancelButton = (Button) mView.findViewById(R.id.cancel_button);
                Button editButton = (Button) mView.findViewById(R.id.edit_button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.setTitle("Edit Quantity");
                dialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newQuantity = Integer.parseInt(quantityEditText.getText().toString());
                        if (newQuantity < initialQuantity) {
                            Toast.makeText(getApplicationContext(), "Can't be less than " + initialQuantity, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Quantity updated", Toast.LENGTH_SHORT).show();
                            //go ahead and modify in the list
                            currentItem.setQuantity(Integer.toString(newQuantity));
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                return true;
            }
        });




    }

}
