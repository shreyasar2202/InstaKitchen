package com.example.kitchen.instakitchen;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Rohan Simha on 23-06-2017.
 */

public class popup extends DialogFragment {
    LayoutInflater inflater;
    View v;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText item_n = (EditText) v.findViewById(R.id.etitem);
                EditText qty = (EditText) v.findViewById(R.id.etquantity);
                String item = item_n.getText().toString();
                String quantity = qty.getText().toString();
                SLData DataProvider = new SLData(item, quantity);
                ShoppingList.adapter.add(DataProvider);
                ShoppingList.adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), item + " " + quantity, Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }

}
