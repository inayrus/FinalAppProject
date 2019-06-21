package com.example.finalappproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoteDatabase db;
    private NoteAdapter notesAdapter;
    private CharSequence[] allTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.mainActToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes Organization");
        toolbar.setTitleTextColor(Color.WHITE);

        // select all notes from the database & set the NoteAdapter
        this.db = NoteDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();
        this.notesAdapter = new NoteAdapter(this, cursor);

        // get all the tags from the database
        this.allTags = db.getAllTags();

        // set the adapter to the list
        ListView notesList = findViewById(R.id.notesList);
        notesList.setAdapter(notesAdapter);

        // set an list item listener
        ListItemClickListener listListener = new ListItemClickListener();
        notesList.setOnItemClickListener(listListener);

        // tags adapter is set in the NoteAdapter Classs
    }

    // when the user returns to this activity
    @Override
    protected void onResume() {
        super.onResume();
        updateData();
        this.allTags = db.getAllTags();
    }

    // if a note in the list is clicked
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // send the user to the noteactivity
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            Note note = new Note();

            // cursor can't be put as extra, so unpack the values and send those
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            int titleIndex = cursor.getColumnIndex("Title");
            String title = cursor.getString(titleIndex);
            note.setTitle(title);

            int contentIndex = cursor.getColumnIndex("Content");
            String content = cursor.getString(contentIndex);
            note.setContent(content);

            int tagsIndex = cursor.getColumnIndex("Tags");
            String tags = cursor.getString(tagsIndex);
            note.setStringTags(tags);

            int idIndex = cursor.getColumnIndex("_id");
            int noteId = cursor.getInt(idIndex);
            note.setId(noteId);

            intent.putExtra("Note", note);
            intent.putExtra("allTags", allTags);

            startActivity(intent);
        }
    }

    // make new note fab is clicked
    public void newNoteClicked (View v) {

        // send the user to the NoteActivity
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("allTags", allTags);
        startActivity(intent);
    }

    private void updateData() {
        // get all the updated data from the database
        Cursor newCursor = db.selectAll();

        // replace the cursor in the adapter
        notesAdapter.swapCursor(newCursor);
    }
}
