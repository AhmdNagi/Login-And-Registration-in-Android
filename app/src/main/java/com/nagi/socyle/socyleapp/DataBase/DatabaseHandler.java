package com.nagi.socyle.socyleapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nagi.socyle.socyleapp.ModelClass.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contacts";

    // Contacts table name
    private static final String TABLE_CONTACTS = "users";

    // Contacts Table Columns names
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_PHONE = "user_phone";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PHONE + " TEXT," + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    // Adding new contact
    public void addContact(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getUserName()); // Contact Name
        values.put(KEY_EMAIL, contact.getUserEmail()); // Contact Email
        values.put(KEY_PASSWORD, contact.getPassword()); // Contact Password
        values.put(KEY_PHONE, contact.getPhone()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        Log.i("","inserted successfully");
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<User> getAllContacts() {
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User contact = new User();
                contact.setUserName(cursor.getString(0));
                contact.setUserEmail(cursor.getString(1));
                contact.setPassword(cursor.getString(2));
                contact.setPhone(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Deleting single contact
    public void deleteContact(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?", new String[]{String.valueOf(contact.getUserName())});
        db.close();
    }


}
