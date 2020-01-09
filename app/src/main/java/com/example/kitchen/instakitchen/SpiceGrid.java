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
        //RelativeLayout reLay = (RelativeLayout) findViewById(R.id.relative_gridLayout);

        /*ArrayList<ProgressBar> quantity_bar = new ArrayList<ProgressBar>();
        SpicesAdapter spiceAdapter;
        for (int i = 0; i < 6; i++) {
            ProgressBar quantity_obj = new android.widget.ProgressBar(getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
            int progress = (int) java.lang.Math.ceil(100 * java.lang.Math.random());
            quantity_obj.setProgress(progress);
            //     quantity_obj.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
            quantity_bar.add(quantity_obj);
//            (quantity_bar.get(i)).setProgress(prog);
        }
        */

        GridSpicesAdapter spiceAdapter;
        int i = 0;

//        RelativeLayout my_relative = (RelativeLayout) findViewById(R.id.relative_layout);

       /* DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        int screen_width=displayMetrics.widthPixels;    //width of the device screen
        int screen_height=displayMetrics.heightPixels;   //height of device screen

        int view_width=screen_width/3;   //width for relativeview
        int view_height=screen_height/2;   //height for relativeview
        */
        spiceAdapter = new GridSpicesAdapter(getApplicationContext(), R.layout.grid_layout);
        spicesGrid = (GridView) findViewById(R.id.spicesGridView);

//        spicesGrid.getLayoutParams().width = screen_width;
//        spicesGrid.getLayoutParams().height = screen_height;
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

