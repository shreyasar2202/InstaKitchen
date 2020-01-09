package com.example.kitchen.instakitchen;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitchen.instakitchen.data.SpicesContract;
import com.example.kitchen.instakitchen.data.SpicesContract.SpicesEntry;
import com.example.kitchen.instakitchen.data.SpicesDbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpiceList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the pet data loader
     */
    private static final int SPICES_LOADER = 0;

    /**
     * Content URI for the existing spice (null if it's a new pet)
     */
    private Uri mCurrentSpiceUri;

    private FirebaseUser user;
    public int[] spice_quantity = new int[100];
    public String[] spice_name = new String[10];
    //    public ArrayList<Integer> quadrant = new ArrayList<Integer>();

    //    ImageView grid = (ImageView) findViewById(R.id.icon_grid);
    //    ImageView list = (ImageView) findViewById(R.id.icon_list);
    ProgressBar mProgressBar;

    /**
     * Database helper object
     */
    private SpicesDbHelper mDbHelper;

    // Variable to store the number of rows in the table to make a decision to insert or update rows
    long numberOfRows;

    private LinearLayout mHeader;

    private TextView mMaxQuantity;

    /**
     * Adapter for the ListView
     */
    SpicesCursorAdapter mCursorAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spice_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //final ArrayList<Integer> quadrant = new ArrayList<Integer>();

//        mSpiceAdapter = new SpicesAdapter(getApplicationContext(), R.layout.list_rowlayout);

        // Find the ListView which will be populated with the spice data
        ListView spicesListView = (ListView) findViewById(R.id.spicesListView);

        // Get a reference to the ConnectivityManager to check state of network connectivity

        final ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.

        TextView emptyView = (TextView) findViewById(R.id.spice_list_empty_view);
        emptyView.setVisibility(View.INVISIBLE);

//        spicesListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.

        mCursorAdapter = new SpicesCursorAdapter(this, null);
        spicesListView.setAdapter(mCursorAdapter);

        mProgressDialog = new ProgressDialog(this);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Drawer 1 - Spices");

//        mProgressBar = (ProgressBar) findViewById(R.id.spice_loading_indicator);
//        mProgressBar.setVisibility(View.VISIBLE);

        //Check why not working...
        mProgressDialog.setMessage("Fetching Your Data...");
        mProgressDialog.show();

        mHeader = (LinearLayout) findViewById(R.id.spiceHeader);
        mMaxQuantity = (TextView) findViewById(R.id.maxQuantity);

        mHeader.setVisibility(View.INVISIBLE);
        mMaxQuantity.setVisibility(View.INVISIBLE);

        String userID = user.getUid();

//        Get readable database
//        final SQLiteDatabase database = mDbHelper.getReadableDatabase();
//
//        numberOfRows = DatabaseUtils.queryNumEntries(database, SpicesContract.SpicesEntry.TABLE_NAME);


        if(networkInfo!=null && networkInfo.isConnected()) {

            /*
             * To create a reference to the file to be operated upon.
             * A reference can be thought of as a pointer to a file in the cloud
             */

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Pantry");
            myRef = myRef.child("mykitchen").child("Drawer0");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mDbHelper = new SpicesDbHelper(getApplicationContext());
                    SQLiteDatabase database = mDbHelper.getReadableDatabase();
                    numberOfRows = DatabaseUtils.queryNumEntries(database, SpicesContract.SpicesEntry.TABLE_NAME);
                    DataSnapshot mySnapshot1 = dataSnapshot.child("IngredientList");
                    Iterable<DataSnapshot> children1 = mySnapshot1.getChildren();
                    int i = 0, count = 0;
                    for (DataSnapshot child : children1) {
                        count++;
                        spice_name[i++] = child.getValue(String.class);
                    }
                    DataSnapshot mySnapshot2 = dataSnapshot.child("QuantityList");
                    Iterable<DataSnapshot> children2 = mySnapshot2.getChildren();
                    i = 0;
                    for (DataSnapshot child : children2) {
                        spice_quantity[i++] = child.getValue(Integer.class);
                    }
                    if (numberOfRows == 0)
                        insertSpices(spice_name, spice_quantity, count);
                    else
                        updateSpices(spice_name, spice_quantity, count);
//                updateUI(spice_quantity,spice_name,count);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
            if(numberOfRows==0) {
                emptyView.setVisibility(View.VISIBLE);
                emptyView.setText("Please connect to the internet to fetch data");
            }

//        list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent listIntent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(listIntent);
//            }
//        });
//        grid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gridIntent = new Intent(MainActivity.this, gridview.class);
//                startActivity(gridIntent);
//            }
//        });

        final DatabaseReference spiceReference = FirebaseDatabase.getInstance().getReference("Pantry").child("mykitchen").child("Drawer0").child("IngredientList");
        spicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Inflate dialog box
//                spiceReference.child("0").setValue("police");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SpiceList.this);
                View editView = getLayoutInflater().inflate(R.layout.edit_spice_dialog_layout, null);

//                To get the original name of the spice before editing
                TextView spiceName = (TextView) view.findViewById(R.id.spice_name);
                final String originalName = spiceName.getText().toString();

//                Get the quantity of the spice being edited
                TextView spiceQuantity = (TextView) view.findViewById(R.id.spice_quantity_value);
                String quantity = spiceQuantity.getText().toString();
                quantity = quantity + "g";

                final long itemNumber = id-1;
                TextView quantityDisplay = (TextView) editView.findViewById(R.id.quantity_display);
                quantityDisplay.setText(quantity);

                final EditText spiceEditName = (EditText) editView.findViewById(R.id.spice_editName);
                spiceEditName.setHint(originalName);

                Button cancelButton = (Button) editView.findViewById(R.id.cancel_button);
                Button editButton = (Button) editView.findViewById(R.id.save_button);

                mBuilder.setView(editView);
                final AlertDialog dialog = mBuilder.create();
                dialog.setTitle("Edit Spice Name");
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
                        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
                        if(activeNetworkInfo!=null && activeNetworkInfo.isConnected()){
                            String newName = spiceEditName.getText().toString().trim();
                            if(newName.equals("")){
                                Toast.makeText(getApplicationContext(), "Cannot have empty spice name", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                spiceReference.child(Long.toString(itemNumber)).setValue(newName);
                                 dialog.cancel();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Please connect to the internet to change the spice name", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
//                        String newName = spiceEditName.getText().toString().trim();
//                        spiceReference.child(Long.toString(itemNumber)).setValue(newName);
//                        dialog.cancel();
                    }
                });
//                spiceName.setText("yoooooooooo"+originalName);

//                Toast.makeText(getApplicationContext(), "Original name is " + originalName, Toast.LENGTH_LONG).show();
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(SPICES_LOADER, null, this);
    }

    void insertSpices(String[] spice_name, int[] spice_quantity, int count) {
        // Create a ContentValues object where column names are the keys,
        // and spice attributes are the values.
        int i;
        for (i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(SpicesEntry.COLUMN_SPICE_NAME, spice_name[i]);
            values.put(SpicesEntry.COLUMN_SPICE_QUANTITY, spice_quantity[i]);

            // Insert a new row for each spice into the provider using the ContentResolver.
            // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
            // into the spices database table.
            // Receive the new content URI that will allow us to access Toto's data in the future.
            Uri newUri = getContentResolver().insert(SpicesEntry.CONTENT_URI, values);
        }
//        getLoaderManager().initLoader(SPICES_LOADER, null, this);
    }

    void updateSpices(String[] spice_name, int[] spice_quantity, int count) {
        int i;
        for (i = 0; i < count; i++) {
            mCurrentSpiceUri = ContentUris.withAppendedId(SpicesEntry.CONTENT_URI, i + 1);
            ContentValues values = new ContentValues();
            values.put(SpicesEntry.COLUMN_SPICE_NAME, spice_name[i]);
            values.put(SpicesEntry.COLUMN_SPICE_QUANTITY, spice_quantity[i]);
            int rowsAffected = getContentResolver().update(mCurrentSpiceUri, values, null, null);

            // Show a toast message depending on whether or not the insertion was successful.
            if (rowsAffected == 0) {
                // If the new content URI is null, then there was an error with insertion.
                Log.e("SpiceList.java", "Error updating spice" + (i + 1));
            }
        }
    }

/*    public void updateUI(int [] quad, String [] spice_name, int count)
    {

        mProgressDialog.dismiss();
//        mProgressBar.setVisibility(View.INVISIBLE);
        mHeader.setVisibility(View.VISIBLE);
        mMaxQuantity.setVisibility(View.VISIBLE);

//        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.black_pepper);

        int[] spice_image_resource = {R.drawable.black_pepper, R.drawable.cardamom, R.drawable.cinnamon, R.drawable.clove, R.drawable.coriander_seeds, R.drawable.cumin};
        String[] spice_names = getResources().getStringArray(R.array.spices);
//        String[] quanquan = {"100","100","100","100","100","100"};



        ArrayList<ProgressBar> quantity_bar = new ArrayList<ProgressBar>();
        //SpicesAdapter spiceAdapter;
        for (int i = 0; i < count; i++) {
            ProgressBar quantity_obj = new android.widget.ProgressBar(getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);

            quantity_obj.setProgress(quad[i]);
            //     quantity_obj.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
            quantity_bar.add(quantity_obj);
           //      (quantity_bar.get(i)).setProgress(prog);
        }

        SpicesAdapter spicesAdapter = new SpicesAdapter(getApplicationContext(), R.layout.list_rowlayout);
        ListView spicesList = (ListView) findViewById(R.id.spicesListView);
        spicesList.setAdapter(spicesAdapter);

        int i;

        for (i=0;i<count;i++) {
            Log.e("valuevalue:",quad[i]+"");
//            SpicesDataProvider spicesDataProvider = new SpicesDataProvider(spice_name[i], quantity_bar.get(i),quad[i]);
            SpicesDataProvider spicesDataProvider = new SpicesDataProvider(spice_image_resource[i], spice_name[i], quantity_bar.get(i),quad[i]);
            spicesAdapter.add(spicesDataProvider);
        }
        ImageView grid=(ImageView) findViewById(R.id.icon_grid);
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SpiceList.this,SpiceGrid.class);
                startActivity(i);
            }
        });
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the adapter shows all pet attributes, define a projection that contains
        // all columns from the spices table
        String[] projection = {
                SpicesEntry.SPICE_ID,
                SpicesEntry.COLUMN_SPICE_NAME,
                SpicesEntry.COLUMN_SPICE_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,       // Parent activity context
                SpicesEntry.CONTENT_URI,    // Provider content URI to query
                projection,                 // Columns to include in the resulting Cursor
                null,                       // No selection clause
                null,                       // No selection arguments
                null);                      // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mProgressDialog.dismiss();
//        mProgressBar.setVisibility(View.INVISIBLE);
        mHeader.setVisibility(View.VISIBLE);
        mMaxQuantity.setVisibility(View.VISIBLE);

        // Update {@link SpicesCursorAdapter} with this new cursor containing updated spices data
        mCursorAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}