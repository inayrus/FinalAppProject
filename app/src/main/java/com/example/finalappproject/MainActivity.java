package com.example.finalappproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.datatype.Duration;

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

        // initialize selectedFilters on none
        this.selectedFilter = -1;

        // tags adapter is set in the NoteAdapter Class
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

        // arraylist to keep the selected items
        final String selectedTag;

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

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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

        builder.setNeutralButton(
                "Unselect all",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        selectedFilter = -1;
                        updateData();
                    }
                });

        return builder;
    }
}
