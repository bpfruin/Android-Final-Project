package com.bignerdranch.android.proFavRestos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by bpfruin on 8/21/13.
 */
public class RestoDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "restos.sqllite";
    private static final String DB_TABLE_NAME = "restaurant_table";
    private static final String COLUMN_ID = "_id";
    private static final int VERSION = 1;
    //could have a problem with the extra semicolon
    private static final String DB_TABLE_CREATE =
            "CREATE TABLE " + DB_TABLE_NAME + " (_id integer primary key, name TEXT, city TEXT, phone TEXT, lat_long TEXT, address TEXT, yelp TEXT, note TEXT, image_blob BLOB)";

    private static final String TABLE_RESTO = "resto";
    private static final String COLUMN_RESTO_NAME = "name";

    public RestoDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

//    public long insertRestaurant(Resto c) {
//        //build new content value i.e. hashmap
//        ContentValues values = new ContentValues();
//        values.put("name", c.getName());
//        values.put("city", c.getCity());
//        values.put("phone", c.getPhone());
//        values.put("lat_long", c.getLatLong());
//        values.put("address", c.getAddress());
//        values.put("yelp", c.getYelp());
//
//        //values.put("image_blob", c.getImgByte());
//
//        return getWritableDatabase().insert("restaurant_table", null, values);
//
//
//        //get database then add contentvalue to image_table
////        SQLiteDatabase database = mHelper.getWritableDatabase();
////        long result = database.insert("restaurant_table", null, values);
////
////        mRestos.add(c);
//        //saveRestos();
//
//        //return -1;
//    }
//
//    public void updateRestaurant(Resto c) {
//        //build new content value i.e. hashmap
//        ContentValues values = new ContentValues();
//        values.put("name", c.getName());
//        values.put("city", c.getCity());
//        values.put("phone", c.getPhone());
//        values.put("lat_long", c.getLatLong());
//        values.put("address", c.getAddress());
//        values.put("yelp", c.getYelp());
//
//
//    }

    public long insertRestaurant(Resto resto) {

        	        // Open a database for reading and writing

        	        SQLiteDatabase database = this.getWritableDatabase();

        	        // Stores key value pairs being the column name and the data
        	        // ContentValues data type is needed because the database
        	        // requires its data type to be passed

        	        ContentValues values = new ContentValues();

        	        values.put("name", resto.getName());
        	        values.put("city", resto.getCity());
        	        values.put("phone", resto.getPhone());
        	        values.put("lat_long", resto.getLatLong());
        	        values.put("address", resto.getAddress());
                    values.put("yelp", resto.getAddress());
                    values.put("note", resto.getNote());
                    values.put("image_blob", resto.getImgByte());


        // Inserts the data in the form of ContentValues into the
        	        // table name provided

        	        database.insert("restaurant_table", null, values);


        	        // Release the reference to the SQLiteDatabase object

        	        database.close();

        return getWritableDatabase().insert("restaurant_table", null, values);

    }

            	    public int updateRestaurant(Resto resto) {

        	        // Open a database for reading and writing

        	        SQLiteDatabase database = this.getWritableDatabase();

        	        // Stores key value pairs being the column name and the data

        	        ContentValues values = new ContentValues();

                        values.put("name", resto.getName());
                        values.put("city", resto.getCity());
                        values.put("phone", resto.getPhone());
                        values.put("lat_long", resto.getLatLong());
                        values.put("address", resto.getAddress());
                        values.put("yelp", resto.getAddress());
                        values.put("note", resto.getNote());
                        values.put("image_blob", resto.getImgByte());

        	        // update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)

        	        return database.update("restaurant_table", values, "_id" + " = ?", new String[] { ((Long)resto.getId()).toString() });
        	    }

            	    // Used to delete a contact with the matching contactId

            	    public void deleteResto(String id) {

        	        // Open a database for reading and writing

        	        SQLiteDatabase database = this.getWritableDatabase();

        	        String deleteQuery = "DELETE FROM  restaurant_table where _id='"+ id +"'";

        	        // Executes the query provided as long as the query isn't a select
        	        // or if the query doesn't return any data

        	        database.execSQL(deleteQuery);
        	    }

            	    public ArrayList<Resto> loadRestos() {

        	        // ArrayList that contains every row in the database
        	        // and each row key / value stored in a HashMap

        	        ArrayList<Resto> restoArrayList;

        	        restoArrayList = new ArrayList<Resto>();

        	        String selectQuery = "SELECT  * FROM restaurant_table";

        	        // Open a database for reading and writing

        	        SQLiteDatabase database = this.getWritableDatabase();

        	        // Cursor provides read and write access for the
        	        // data returned from a database query

        	        // rawQuery executes the query and returns the result as a Cursor

        	        Cursor cursor = database.rawQuery(selectQuery, null);

        	        // Move to the first row

        	        if (cursor.moveToFirst()) {
            	            do {
                	                Resto resto = new Resto(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getBlob(8));

                	                // Store the key / value pairs in a HashMap
                	                // Access the Cursor data by index that is in the same order
                	                // as used when creating the table



                	                restoArrayList.add(resto);
                	            } while (cursor.moveToNext()); // Move Cursor to the next row
            	        }

        	        // return contact list
        	        return restoArrayList;
        	    }
//
//            	    public HashMap<String, String> getContactInfo(String id) {
//        	        HashMap<String, String> contactMap = new HashMap<String, String>();
//
//        	        // Open a database for reading only
//
//        	        SQLiteDatabase database = this.getReadableDatabase();
//
//        	        String selectQuery = "SELECT * FROM contacts where contactId='"+id+"'";
//
//        	        // rawQuery executes the query and returns the result as a Cursor
//
//        	        Cursor cursor = database.rawQuery(selectQuery, null);
//        	        if (cursor.moveToFirst()) {
//            	            do {
//
//                	                contactMap.put("firstName", cursor.getString(1));
//                	                contactMap.put("lastName", cursor.getString(2));
//                	                contactMap.put("phoneNumber", cursor.getString(3));
//                	                contactMap.put("emailAddress", cursor.getString(4));
//                	                contactMap.put("homeAddress", cursor.getString(5));
//
//                	            } while (cursor.moveToNext());
//            	        }
//        	    return contactMap;
//        	    }
}


