package com.example.finalappproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    // attributes
    private Note retrievedNote;
    private NoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // save the database
        this.db = NoteDatabase.getInstance(getApplicationContext());

        // display note in UI
        displayNote();

        // hide keyboard until an edittext is clicked
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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

    // retrieves a note from intent and displays it in the Activity
    private void displayNote() {

        // get the note from the intent
        Intent intent = getIntent();
        this.retrievedNote = (Note) intent.getSerializableExtra("Note");

        // unpack note if note is being edited (not newly made)
        if (retrievedNote != null) {
            TextView titleView = findViewById(R.id.editTitle);
            titleView.setText(String.valueOf(retrievedNote.getTitle()));

            TextView contentView = findViewById(R.id.editContent);
            contentView.setText(String.valueOf(retrievedNote.getContent()));

            String stringTags = retrievedNote.getStringTags();
            TagsAdapter tagsAdapter = new TagsAdapter(this);
            tagsAdapter.setTags((LinearLayout)findViewById(R.id.tagsNoteAct), stringTags);

        }
    }

    // link the action buttons to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_act_menu, menu);
        return true;
    }

    // horizontal scroll view for the tags
        // needs an adapter

    // save note
    public void doneClicked(View v) {

        Note editedNote = getNoteFromView();

        // check if either title field or content field is filled
        if (!editedNote.getContent().equals("") || !editedNote.getTitle().equals("")) {
            NoteDatabase db = NoteDatabase.getInstance(getApplicationContext());

            // check if note is new or being edited
            if (retrievedNote == null) {
                // new: insert
                db.insert(editedNote);
            } else {
                // edited: update
                db.update(editedNote.getId(), editedNote);
            }
        }
        // send the user to the start screen
        finish();
    }

    // return to previous screen
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        // send the user to the start screen
        finish();
    }

    // turn the screentext into a Note
    private Note getNoteFromView() {
        TextView titleView = findViewById(R.id.editTitle);
        TextView contentView = findViewById(R.id.editContent);

        // HOW TO RETRIEVE THE TAGS??

        Note note;

        // if note is new
        if (retrievedNote == null) {
            note = new Note();
        }
        // if note is edited
        else {
            note = retrievedNote;
        }

        note.setTitle(titleView.getText().toString());
        note.setContent(contentView.getText().toString());

        // PUT TAGS

        return note;
    }

    // react to toolbar actions clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:

                // check if the note is new or being edited
                if (retrievedNote != null) {
                    // return a pop up, asking if they're sure, then delete the note from the database
                    AlertDialog alert = alertBuild().create();
                    alert.show();
                }
                else {
                    // close activity
                    finish();
                }
                return true;

            case R.id.action_tags:
                // implement action provider to let the user select tags
                // https://developer.android.com/training/appbar/action-views

                Toast.makeText(this, "Tags click", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_share:
                // use the ShareActionProvider widget: action provider to share information with other apps

                return true;
            case R.id.action_add:
                // send user to SelectActivity
                Intent intent = new Intent(NoteActivity.this, SelectActivity.class);
                intent.putExtra("Existing note", getNoteFromView());
                startActivity(intent);

            default:
                // the user's click action is not recognized
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("yoo we're back");
        setIntent(intent);
        //now getIntent() should always return the last received intent
        displayNote();
    }

    // build the pop up for delete confirmation
    private AlertDialog.Builder alertBuild() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sure you want to delete this note?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // call the database delete function
                        db.delete(retrievedNote.getId());
                        finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder;
    }
}
