package com.example.kitchen.instakitchen.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sindhu on 13-08-2017.
 */

public final class FavContract {

    private FavContract(){
    }

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
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_FAV = "Favourites";

    /**
     * Inner class that defines constant values for the Favourites database table.
     * Each entry in the table represents a single recipe.
     */

    public static final class FavEntry implements BaseColumns{

        /**
         * The content URI to access the fav data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAV);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of favourites.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single fav.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

        public final static String TABLE_NAME = "Favourites";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_INGREDIENTS = "ingredients";
        public final static String COLUMN_RECIPE = "recipe";

    }
}

//package com.example.kitchen.instakitchen.data;
//
//        import android.net.Uri;
//        import android.provider.BaseColumns;
//
///**
// * Created by Shreyas on 11-07-2017.
// */
//
//public final class FavContract {
//    // To prevent someone from accidentally instantiating the contract class,
//// give it an empty constructor.
//    private FavContract() {}
//    public static final String CONTENT_AUTHORITY = "com.example.kitchen.instakitchen";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//    public static final String PATH_FAV = "favourites";
//
//
//    public static final class FavEntry implements BaseColumns {
//        public final static String TABLE_NAME = "favs";
//        public final static String _ID = BaseColumns._ID;
//        public final static String COLUMN_NAME ="name";
//        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAV);
//        public final static String COLUMN_INGREDIENTS = "ingredients";
//        public final static String COLUMN_RECIPE= "recipe";
//        public final static String RECIPE_ID="recipeID";
//
////        public final static String COLUMN_PET_GENDER = "gender";
////
////        public final static String COLUMN_PET_WEIGHT = "weight";
////
////        public static final int GENDER_UNKNOWN = 0;
////        public static final int GENDER_MALE = 1;
////        public static final int GENDER_FEMALE = 2;
//
////        public static boolean isValidGender(int gender) {
////            if (gender == PetEntry.GENDER_UNKNOWN || gender == PetEntry.GENDER_MALE || gender == PetEntry.GENDER_FEMALE) {
////                return true;
////            }
////            return false;
////        }
//    }
//
//}
//
//
