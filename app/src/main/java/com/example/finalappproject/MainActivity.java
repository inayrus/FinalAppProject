/* *************************************************************************************************
 * The main activity of the Notes app. Displays a list with all the notes,
 * has a toolbar with a tag filter option and shows a floating action button.
 *
 * Clicking a note or the floating action button sends the user to the NoteActivity.
 * Clicking the filter icon will open an AlertDialogue, where a tag can be selected.
 *
 * by Valerie Sawirja
 * ************************************************************************************************/

package com.example.finalappproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private NoteDatabase db;
    private NoteAdapter notesAdapter;
    private CharSequence[] allTags;
    private int selectedFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.mainActToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes Organization");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textOnLight));

        // select all notes from the database & set the NoteAdapter
        this.db = NoteDatabase.getInstance(getApplicationContext());

        // get all the tags from the database
        this.allTags = db.getAllTags();

        Cursor cursor;

        // if phone is being turned
        if (savedInstanceState != null) {
            this.selectedFilter = savedInstanceState.getInt("selectedFilter");

            if (selectedFilter == -1) {
                // select all
                cursor = db.selectAll();
            }
            else {
                // get what tag was filtered on
                cursor = db.filterTags(allTags[selectedFilter].toString());
            }
        }
        // if the activity is newly created
        else {
            // display all the notes
            cursor = db.selectAll();

            // initialize selectedFilters on none
            this.selectedFilter = -1;
        }

        // set the adapter to the list
        this.notesAdapter = new NoteAdapter(this, cursor);
        ListView notesList = findViewById(R.id.notesList);
        notesList.setAdapter(notesAdapter);

        // set an list item listener
        ListItemClickListener listListener = new ListItemClickListener();
        notesList.setOnItemClickListener(listListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // remember if the notes are filtered
        outState.putInt("selectedFilter", selectedFilter);
    }

    // link the action buttons to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_tags_menu, menu);
        return true;
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

            // cursor can't be put as extra, so unpack the values and send those as a Note
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

    // updates the notes shown (used when applying filter or when new note is added)
    private void updateData() {
        // remember if notes were filtered
        Cursor newCursor;

        if (selectedFilter == -1) {
            // get all the updated data from the database
            newCursor = db.selectAll();
        }
        else {
            // get filtered notes
            newCursor = db.filterTags(allTags[selectedFilter].toString());
        }

        // replace the cursor in the adapter
        notesAdapter.swapCursor(newCursor);
    }

    // react to toolbar actions clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_filter:
                // make alert dialogue with all tag options
                AlertDialog alert = tagsListBuild().create();
                alert.show();
                return true;

            default:
                // the user's click action is not recognized
                return super.onOptionsItemSelected(item);
        }
    }

    // build a pop up for the list with all tags
    private AlertDialog.Builder tagsListBuild() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter on tags");
        builder.setCancelable(true);

        // http://www.learn-android-easily.com/2013/06/alertdialog-with-checkbox.html
        builder.setSingleChoiceItems(allTags, selectedFilter,
                new DialogInterface.OnClickListener() {

                    // indexSelected contains the index of item (chosen tag)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedFilter = which;
                        System.out.println(selectedFilter);
                    }
                });

        builder.setPositiveButton(
                "Filter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Cursor newCursor;

                        // call the database to filter
                        if (selectedFilter != -1) {
                            newCursor = db.filterTags(allTags[selectedFilter].toString());
                        }
                        // if none selected, call selectAll
                        else {
                            newCursor = db.selectAll();
                        }

                        // replace the cursor in the adapter
                        notesAdapter.swapCursor(newCursor);

                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Show all",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        selectedFilter = -1;
                        updateData();
                    }
                });

        return builder;
    }
}
