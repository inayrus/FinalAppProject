package com.example.finalappproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        // make indiv note list layout
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
