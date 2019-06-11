package com.example.finalappproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

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
        NoteDatabase db = NoteDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();
        NoteAdapter adapter = new NoteAdapter(getApplicationContext(), cursor);

        // set the adapter to the list
        ListView notesList = findViewById(R.id.notesList);
        notesList.setAdapter(adapter);

        // set an list item listener
        ListItemClickListener listListener = new ListItemClickListener();
        notesList.setOnItemClickListener(listListener);

        // make indiv note list layout
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
            System.out.println(note.getTitle());

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

            startActivity(intent);
        }
    }

    // make new note fab is clicked
    public void newNoteClicked (View v) {

        // send the user to the NoteActivity
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }


    // select photo fab is clicked
    public void selectPhotoClicked(View v) {

        // send user to SelectActivity
        Intent intent = new Intent(MainActivity.this, SelectActivity.class);
        startActivity(intent);
    }
}
