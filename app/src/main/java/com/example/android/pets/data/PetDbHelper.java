package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// WE IMPORT THIS so we can use "PetEntry.SOMETHING_CONSTANT" , because otherwise
// we have to write it like that -> PetContract.Petentry.SOMETHING_CONSTANT"
// For example SOMETHING_CONSTANT may refer to COLUMN_PET_NAME or COLUMN_PET_WEIGHT etc.
import com.example.android.pets.data.PetContract.PetEntry;
/**
 * Created by Martin on 7.7.2017 г..
 */

public class PetDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "shelter.db";

    /** Database version. If you change the database schema, you must
     * increment the database version.  */
    private static final int DATABASE_VERSION = 1;



    public PetDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Here we create a String that contains the SQL statement to create the pets table
        // The SQL statement we used in Lesson 1:
        // CREATE TABLE pets (_id INTEGER PRIMARY KEY  AUTOINCREMENT, name TEXT NOT NULL,
        // breed TEXT , gender INTEGER NOT NULL , weight INTEGER NOT NULL DEFAULT 0);
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + "(" + // ALLWAYS
                PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + // CHECK
                PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL ," + // THE WHITE SPACES
                PetEntry.COLUMN_PET_BREED + " TEXT ," + // BECAUSE
                PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL ," + // YOU HAD TO DEBUG THIS
                PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0 " + // 10 TIMES !!!
                ")";

    sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
