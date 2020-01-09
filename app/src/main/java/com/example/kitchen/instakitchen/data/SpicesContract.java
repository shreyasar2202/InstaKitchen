package com.example.kitchen.instakitchen.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Namitha on 13-10-2017.
 */

public class SpicesContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SpicesContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.kitchen.instakitchen";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.kitchen.instakitchen/spices/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_SPICES = "spices";

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single spice.
     */

    public static final class SpicesEntry implements BaseColumns {

        /**
         * The content URI to access the spice data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SPICES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPICES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPICES;

        /**
         * Name of database table for pets
         */
        public final static String TABLE_NAME = "spices";

        /**
         * Unique ID number for the spice (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String SPICE_ID = BaseColumns._ID;

        /**
         * Name of the spice.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_SPICE_NAME = "name";

        /**
         * Quantity of the pet.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_SPICE_QUANTITY = "quantity";

    }
}
