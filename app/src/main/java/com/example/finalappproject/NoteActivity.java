package com.example.finalappproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity implements ConvertRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // get the note from the intent
        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("Note");

        // unpack note if note is being edited (not newly made)
        if (note != null) {
            TextView titleView = findViewById(R.id.editTitle);
            titleView.setText(String.valueOf(note.getTitle()));

            TextView contentView = findViewById(R.id.editContent);
            contentView.setText(String.valueOf(note.getContent()));
            System.out.println(note.getContent());

            // GOTDAMN TAGS
        }

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.noteActToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);

        // Get a support ActionBar for this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // link the action buttons to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_act_menu, menu);
        return true;
    }

    // react to toolbar actions clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // return a pop up, asking if they're sure, then delete the note from the database

                Toast.makeText(this, "Delete click", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_tags:
                // implement action provider to let the user select tags
                // https://developer.android.com/training/appbar/action-views

                Toast.makeText(this, "Tags click", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_share:
                // use the ShareActionProvider widget: action provider to share information with other apps

                return true;

            default:
                // the user's click action is not recognized
                return super.onOptionsItemSelected(item);
        }
    }


    // horizontal scroll view for the tags
        // needs an adapter


    @Override
    public void gotNote(Note note) {

    }

    @Override
    public void gotNoteError(String message) {

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        // save the note in database unless the fields are empty
        Note note = getNoteFromView();

        if (!note.getContent().equals("") || !note.getTitle().equals("")) {
            NoteDatabase db = NoteDatabase.getInstance(getApplicationContext());
            db.insert(note);
        }

        // send the user to the start screen
        finish();
    }

    private Note getNoteFromView() {
        TextView titleView = findViewById(R.id.editTitle);
        TextView contentView = findViewById(R.id.editContent);

        // HOW TO RETRIEVE THE TAGS??

        Note note = new Note();
        note.setTitle(titleView.getText().toString());
        note.setContent(contentView.getText().toString());
        // PUT TAGS

        return note;
    }
}
