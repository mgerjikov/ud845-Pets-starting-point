/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
//    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
//        mDbHelper = new PetDbHelper(this);
//        displayDatabaseInfo();
    }


    // Here we are Overriding the onStart() method, and that means when the activity starts again
    // (on start .. get it ? ). This means after it comes back after the user has clicked
    // Save in the editor activity that the list will refresh with the new pet in the database.
    // So this will allow the row count to the screen to increase.
    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }
    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

//        // Create and/or open a database to read from it
//        //
//        // WE DON'T NEED THIS DATABASE CALL ANYMORE BECAUSE NOW
//        // WE ARE GOING TO USE ContentResolver INSTEAD !!!
//        //
//        // READ THE COMMENT BELOW !
//        /* SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        */

        // Creating a projection with all the COLUMNs in the database
        String[] projection = {
                PetContract.PetEntry._ID, // the id of the pet in the database
                PetContract.PetEntry.COLUMN_PET_NAME, // the name of the pet in the database
                PetContract.PetEntry.COLUMN_PET_BREED, // the breed of the pet
                PetContract.PetEntry.COLUMN_PET_GENDER, // the gender of the pet
                PetContract.PetEntry.COLUMN_PET_WEIGHT // and the weight of the pet
        };


//        // Perform this raw SQL query "SELECT * FROM pets"
//        // to get a Cursor that contains all rows from the pets table.
//        //
//        // Currently in this method we are interacting with the SQLite database
//        // directly. And we've discussed before that this is a BAD PRACTICE!
//        // Instead we should be contacting the database through the ContentResolver
//        // and the PetProvider instead. AND THAT MEANS WE NO LONGER NEED THE DATABASE CALL
//        // ( the commented out code above  "String[] projection" ).
//        //
//        // So we are commenting out this code so we can compare it to the correct code later
//        // OLD CODE :
//        /*Cursor cursor = db.query(
//                PetContract.PetEntry.TABLE_NAME, // The table to query
//                projection, // The columns to return
//                null, // The columns for the WHERE clause
//                null, // The values for the WHERE clause
//                null, // Don't group the rows
//                null, // Don't filter by row groups
//                null // The sort order
//        );
//        */
        // NEW CODE :
        // SO NOW INSTEAD OF DIRECTLY QUERYING THE DATABASE, WE ARE GOING TO
        // CALL TO getContentResolver() and query it with query() ! ! !
        //
        // Perform a query on the provider using ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to access the pet data.
        Cursor cursor = getContentResolver().query(
                PetContract.PetEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null                    // The sort order for the returned rows
        );


        // In order to actually display the pets we use the TextView in activity_catalog.xml
        // We use findViewById and find it
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        // Next we need to modify the try statement
        try {
            // Create a header in the TextView that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop bellow, iterate through the rows of the cursor and
            // display the information from each column in this order.

            displayView.setText("The pets table contains: " + cursor.getCount() + "pets. \n\n");
            displayView.append(
                    PetContract.PetEntry._ID + " - " +
                            PetContract.PetEntry.COLUMN_PET_NAME + " - " +
                            PetContract.PetEntry.COLUMN_PET_BREED + " - " +
                            PetContract.PetEntry.COLUMN_PET_GENDER + " - " +
                            PetContract.PetEntry.COLUMN_PET_WEIGHT + "\n"
            );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                // Display the values of each column of the current row in the cursor in the TextView.
                displayView.append(
                        "\n" +                      // So we can start on new row each time
                        currentID + " - " +         // the id of the current pet
                        currentName + " - " +       // the name of the current pet
                        currentBreed + " - " +      // the breed
                        currentGender + " - " +     // the gender
                        currentWeight               // and of course the weight
                );
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    // LOOK THE CODE AND COMMENTS BELOW ! ! ! THIS IS THE OLD VERSION OF THE METHOD
//    private void insertPet() {
//        // Gets the data repository in write mode
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        // Create a new map of values, where column names are the keys
//        // Create a ContentValues object where column names are the keys,
//        // and Toto's pet attributes are the values.
//        ContentValues values = new ContentValues();
//        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
//        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
//        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
//        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);
//
//        // Insert the new row, returning the primary key value of the new row
//        // Insert a new row for Toto in the database, returning the ID of that new row.
//        // The first argument for db.insert() is the pets table name.
//        // The second argument provides the name of a column in which the framework
//        // can insert NULL in the event that the ContentValues is empty (if
//        // this is set to "null", then the framework will not insert a row when
//        // there are no values).
//        // The third argument is the ContentValues object containing the info for Toto.
//        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
//    }

    /* In the CatalogActivity, when a user clicks on the “Insert Dummy Pet” menu item,
     we will call the ContentResolver insert() method with the pet content URI and a
     ContentValues object (containing info about Toto). Here is the updated version
     of the insertPet() method, which is called from the onOptionsItemSelected() method
      when the menu item is clicked on.
      */
    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}