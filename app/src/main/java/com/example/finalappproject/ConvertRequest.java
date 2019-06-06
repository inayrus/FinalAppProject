package com.example.finalappproject;

import android.content.Context;

import org.json.JSONArray;

public class ConvertRequest {

        // instance variables
        private Context context;
        private Callback activity;

        // callback interface for the send requests (implemented in highscore activity)
        public interface Callback {
            void gotNote(Note note);
            void gotNoteError(String message);
        }
}
