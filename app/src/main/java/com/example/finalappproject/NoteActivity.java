package com.example.finalappproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteActivity extends AppCompatActivity implements ConvertRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
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

        // save the note in database


        // send the user to the start screen
        startActivity(new Intent(NoteActivity.this, MainActivity.class));
        finish();
    }
}
