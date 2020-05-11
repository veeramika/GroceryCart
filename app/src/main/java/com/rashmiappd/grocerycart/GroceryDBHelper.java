package com.rashmiappd.grocerycart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.rashmiappd.grocerycart.GroceryContract.*;
public class GroceryDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "grocerylist.db";
    public static final int DATABASE_VERSION  = 1;
    //, String name, SQLiteDatabase.CursorFactory factory, int version

    /* SQLiteOpenHelper : Create a helper object to create, open, and/or manage a database. This method always returns very quickly.
    The database is not actually created or opened until one of getWritableDatabase() or getReadableDatabase() is called.*/
    public GroceryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*onCreate: Called when the database is created for the first time. This is where the creation of tables and the initial population
     of the tables should happen.*/
    //The CREATE TABLE statement is used to create a new table in a database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GroceryEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                GroceryEntry.COLUMN_TIMESSTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { //If we make changes to our database or update some
        //features then version number increases by 1
    db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME); //Delete the older version
    onCreate(db);   //create newer version
    }
}
