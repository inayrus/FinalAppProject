/* *************************************************************************************************
 * A file that implements the functionality of the NoteActivity.
 * It contains:
 * - a function that shows a note in the UI
 * - a function that stores the information from the UI in a note
 * - a function that saves/updates a note in the database and sends the user back to MainActivity
 * - toolbar actions: 1) Delete
 *                    2) Add Tags
 *                    3) Add Recognized Handwriting
 *
 * The Delete and Add Tags options both open an AlertDialogue.
 * The Add Recognized Handwriting function sends the user to SelectActivity
 *
 * by Valerie Sawirja
 * ************************************************************************************************/


package com.example.finalappproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NoteActivity extends AppCompatActivity {

    // attributes
    private Note retrievedNote;
    private NoteDatabase db;
    private CharSequence[] allTags;
    private ArrayList<String> noteTags;
    private String stringTags;
    private Note convertedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // save the database
        this.db = NoteDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        // save all the tags
        this.allTags = intent.getCharSequenceArrayExtra("allTags");

        // display note in UI
        displayNote(intent);

        // hide keyboard until an edittext is clicked
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.noteActToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textOnLight));

        // Get a support ActionBar for this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // retrieves a note from intent and displays it in the Activity
    private void displayNote(Intent intent) {

        Note note;

        // get the note from the intent (from main: database)
        this.retrievedNote = (Note) intent.getSerializableExtra("Note");
        // from SelectActivity (converted from photo to text with API)
        this.convertedNote = (Note) intent.getSerializableExtra("Recognized");

        // unpack note if note is being edited or has just been converted
        if (retrievedNote != null || convertedNote != null) {

            if (convertedNote != null) {
                note = convertedNote;
            }
            else {
                note = retrievedNote;
            }

            TextView titleView = findViewById(R.id.editTitle);
            titleView.setText(String.valueOf(note.getTitle()));

            TextView contentView = findViewById(R.id.editContent);
            contentView.setText(String.valueOf(note.getContent()));

            this.stringTags = note.getStringTags();
            TagsAdapter tagsAdapter = new TagsAdapter(this);
            tagsAdapter.setTags((LinearLayout)findViewById(R.id.tagsNoteAct), stringTags);

            // convert the string with tags to an ArrayList<String>
            if (stringTags != null) {
                this.noteTags = note.getUpdatedArrayTags(stringTags);
            }
        }
    }

    // link the action buttons to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_act_menu, menu);
        return true;
    }

    // save note
    public void doneClicked(View v) {

        Note editedNote = getNoteFromView();


        // check if either title field or content field is filled
        if (!editedNote.getContent().equals("") || !editedNote.getTitle().equals("")) {
            NoteDatabase db = NoteDatabase.getInstance(getApplicationContext());

            // check if note is new or being edited
            if (retrievedNote == null && convertedNote == null) {
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

        Note note;

        // if note is new
        if (retrievedNote == null & convertedNote == null) {
            note = new Note();
        }
        // if note contains converted text
        else if (convertedNote != null) {
            note = convertedNote;
        }
        // if note is edited normally
        else {
            note = retrievedNote;
        }

        note.setTitle(titleView.getText().toString());
        note.setContent(contentView.getText().toString());
        note.setStringTags(stringTags);

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
                // pop up
                AlertDialog alert = tagsListBuild().create();
                alert.show();
                return true;

            case R.id.action_add:
                // send user to SelectActivity
                Intent intent = new Intent(NoteActivity.this, SelectActivity.class);
                intent.putExtra("Existing note", getNoteFromView());
                startActivity(intent);

            default:
                // any other click on the toolbar is not recognized as an action
                return super.onOptionsItemSelected(item);
        }
    }

    // called when user returns from SelectActivity
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        displayNote(intent);
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

    // build a pop up that contains a list with checkboxes for all the tags.
    private AlertDialog.Builder tagsListBuild() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add tags");
        builder.setCancelable(true);

        // link to the layout made for the dialogue
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogue_add_tags, null);
        builder.setView(dialogView);

        // https://stackoverflow.com/questions/30982403/android-checkbox-gets-unchecked-on-keyboard-show-up
        // get the edittext where a new tag is gonna be put in
        final EditText editText = dialogView.findViewById(R.id.newTag);
        final CheckBox addTagBox = dialogView.findViewById(R.id.addTagCheckBox);

        // list with which tags are on the note (false: tag is not selected, true: tag is selected)
        final boolean[] checked = new boolean[allTags.length];
        Arrays.fill(checked, false);

        if (noteTags != null) {
            for (String tag : noteTags) {
                int index = Arrays.asList(allTags).indexOf(tag);
                checked[index] = true;
            }
        }

        // http://www.learn-android-easily.com/2013/06/alertdialog-with-checkbox.html
        builder.setMultiChoiceItems(allTags, checked,
                new DialogInterface.OnMultiChoiceClickListener() {

                    // change boolean if the user selected a tag for the note
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            checked[indexSelected] = true;
                        }
                        else {
                            checked[indexSelected] = false;
                        }
                    }
                });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setPositiveButton(
                "Apply",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // overwrite noteTags
                        noteTags = new ArrayList<>();

                        for (int i = 0; i < checked.length; i++) {
                            // get strings of the tags that are selected (have checked checkboxes)
                            if (checked[i]) {
                                noteTags.add(allTags[i].toString());
                            }
                        }

                        // check if the edittext and the checkbox are filled in
                        String newTag = editText.getText().toString();
                        if (addTagBox.isChecked() & !newTag.isEmpty()) {
                            // add the tag to the Note
                            noteTags.add(newTag);

                            // add the tag to allTags
                            ArrayList<CharSequence> tempArray = new ArrayList<>();
                            Collections.addAll(tempArray, allTags);
                            tempArray.add(newTag);
                            allTags = tempArray.toArray(new CharSequence[tempArray.size()]);

                        }

                        // convert chosen tags to a string if tags are chosen
                        if (!noteTags.isEmpty()) {
                            stringTags = android.text.TextUtils.join(",", noteTags);
                        }
                        // no tags are selected
                        else {
                            stringTags = null;
                        }

                        // set a new adapter to add the chosen tags to the view
                        TagsAdapter tagsAdapter = new TagsAdapter(NoteActivity.this);
                        tagsAdapter.setTags((LinearLayout) findViewById(R.id.tagsNoteAct), stringTags);

                        dialog.cancel();
                    }
                });

        return builder;
    }
}
