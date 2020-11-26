package com.example.kitchen.instakitchen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import static com.example.kitchen.instakitchen.R.array.spices;

public class SpiceGrid extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_spice_grid);
        GridView spicesGrid;
        int[] spice_image_resource = {R.drawable.black_pepper, R.drawable.cardamom, R.drawable.cinnamon, R.drawable.clove, R.drawable.coriander_seeds, R.drawable.cumin};
        String[] spice_names = getResources().getStringArray(spices);

        GridSpicesAdapter spiceAdapter;
        int i = 0;

        spiceAdapter = new GridSpicesAdapter(getApplicationContext(), R.layout.grid_layout);
        spicesGrid = (GridView) findViewById(R.id.spicesGridView);

        spicesGrid.setAdapter(spiceAdapter);

        for (String spices : spice_names) {
            GridSpicesDataProvider gridSpicesDataProvider = new GridSpicesDataProvider(spice_image_resource[i], spices);
            spiceAdapter.add(gridSpicesDataProvider);
            i++;
        }

        ImageView list_icon=(ImageView) findViewById(R.id.list_icon);
        list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SpiceGrid.this,SpiceList.class);
                startActivity(i);
            }
        });
    }
}

