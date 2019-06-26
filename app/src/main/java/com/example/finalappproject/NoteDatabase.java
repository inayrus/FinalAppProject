/* *************************************************************************************************
 * This file contains database related functions.
 * On create, a new database is made. Only one of the can exist at the same time.
 *
 * It also consists of functions that:
 * - select all notes from database.
 * - insert a note
 * - update a note
 * - delete a note
 * - get all the used tags in the database
 * - select all notes with a certain tag
 *
 * by Valerie Sawirja
 * ************************************************************************************************/

package com.example.finalappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    // turn NoteDatabase in a Singleton: only one instance is allowed to exist
    private static NoteDatabase instance;

    public static NoteDatabase getInstance(Context context) {
        if (NoteDatabase.instance == null) {
            NoteDatabase.instance = new NoteDatabase(context);
        }
        return NoteDatabase.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Notes(_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, " +
                "Content TEXT, Tags TEXT, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);");

        // an example entry
        db.execSQL("INSERT INTO Notes(Title, Content, Tags) VALUES('First Note!'," +
                "'An introduction to the app. You can add let the app read handwriting from a " +
                "photo and add the recognized text to a note, you can add tags to your notes, " +
                "and on the Home Screen you can filter on tags :)', 'introduction')");
    }

    // a constructor
    private NoteDatabase(Context context) {
        super(context, "Notes", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // deletes a table and creates a new one
        db.execSQL("DROP TABLE IF EXISTS " + "Notes");
        onCreate(db);
    }

    // select method
    public Cursor selectAll() {
        // open database
        SQLiteDatabase db = getWritableDatabase();

        // select all, return the cursor
        return db.rawQuery("SELECT * FROM Notes",null);
    }

    // insert method
    public void insert(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        // create object that binds values to SQLite columns
        ContentValues newRow = new ContentValues();

        // put entry values in the right columns
        newRow.put("Title", note.getTitle());
        newRow.put("Content", note.getContent());
        newRow.put("Tags", note.getStringTags());

        // insert the column in database
        db.insert("Notes", null, newRow);
    }

    // update method
    public void update(long id, Note note) {
        SQLiteDatabase db = getWritableDatabase();

        // create object that binds values to SQLite columns
        ContentValues updatedRow = new ContentValues();

        // put entry values in the right columns
        updatedRow.put("Title", note.getTitle());
        updatedRow.put("Content", note.getContent());
        updatedRow.put("Tags", note.getStringTags());

        db.update("Notes", updatedRow,
                "_id = ?", new String[] { String.valueOf(id) });
    }

    //delete method
    public void delete(long id) {
        // open database
        SQLiteDatabase db = getWritableDatabase();

        // delete the entry
        db.delete("Notes", "_id = ?", new String[] { String.valueOf(id) });
    }

    // get all tags method
    public CharSequence[] getAllTags() {
        SQLiteDatabase db = getWritableDatabase();
        List<CharSequence> allTags = new ArrayList<>();

        // get a cursor with all the tags
        Cursor cursor = db.rawQuery("SELECT Tags FROM Notes",null);

        // unpack cursor
        if (cursor.moveToFirst()){
            do{
                // get string of tags per row
                String stringTags = cursor.getString(cursor.getColumnIndex("Tags"));

                // split string
                if (stringTags != null) {
                    String[] parts = stringTags.split(",");

                    // don't save duplicates
                    for (String tag : parts) {
                        if (!allTags.contains(tag)) {
                            allTags.add(tag);
                        }
                    }
                }
            }while(cursor.moveToNext());
        }
        cursor.close();

        // return all tags in a charsequence[]
        return allTags.toArray(new CharSequence[allTags.size()]);
    }

    // filters notes on chosen tags
    public Cursor filterTags(String chosenTag) {
        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery("SELECT * FROM Notes WHERE Tags LIKE ?",
                new String[] {"%" + chosenTag + "%"});

    }
}
