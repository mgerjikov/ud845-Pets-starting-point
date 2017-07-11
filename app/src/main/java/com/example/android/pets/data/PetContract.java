package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Martin on 7.7.2017 г..
 */

public final class PetContract {


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private PetContract(){}

    /**
     * CONTENT_AUTHORITY
     * In PetContract.java, we set this up as a string constant whose value is
     * the same as that from the AndroidManifest:
     *
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    /**
     * BASE_CONTENT_URI
     * Next, we concatenate the CONTENT_AUTHORITY constant with the scheme “content://”
     * we will create the BASE_CONTENT_URI which will be shared by every URI
     * associated with PetContract
     * To make this a usable URI, we use the parse method
     * which takes in a URI string and returns a Uri.
     *
     *
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    /**
     * PATH_TableName
     * This constants stores the path for each of the tables which
     * will be appended to the base content URI.
     *
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_PETS = "pets";

    // Complete CONTENT_URI
    // Lastly, inside each of the Entry classes in the contract, we create a full URI
    // for the class as a constant called CONTENT_URI.
    // The Uri.withAppendedPath() method appends the BASE_CONTENT_URI
    // (which contains the scheme and the content authority) to the path segment.



    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static class PetEntry implements BaseColumns{

        /**
         * Complete CONTENT_URI
         * Lastly, inside each of the Entry classes in the contract, we create a full URI
         * for the class as a constant called CONTENT_URI.
         * The Uri.withAppendedPath() method appends the BASE_CONTENT_URI
         * (which contains the scheme and the content authority) to the path segment.
         *
         * The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /** Name of database table for pets */
        public static final String TABLE_NAME = "pets";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public static final String COLUMN_PET_NAME = "name";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public static final String COLUMN_PET_BREED = "breed";

        /**
         * Gender of the pet.
         *
         * The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
         * or {@link #GENDER_FEMALE}.
         *
         * Type: INTEGER
         */
        public static final String COLUMN_PET_GENDER = "gender";

        /**
         * Weight of the pet.
         *
         * Type: INTEGER
         */
        public static final String COLUMN_PET_WEIGHT = "weight";

        /**
         * Possible values for the gender of the pet.
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


    }


}
