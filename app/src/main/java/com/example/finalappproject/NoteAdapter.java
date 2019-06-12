package com.example.finalappproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ResourceCursorAdapter {

    public NoteAdapter(Context context, Cursor c) {
        // constructor, new version needs flags
        super(context, R.layout.list_note_item, c, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    // method that puts the data in your recycled view,
    // where the cursor points to a row in the db
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // get the layout elements
        TextView titleView = view.findViewById(R.id.mainTitle);
        RecyclerView recyclerView = view.findViewById(R.id.mainTags);

        // get the value of the title
        int titleIndex = cursor.getColumnIndex("Title");
        String title = cursor.getString(titleIndex);

        // set the value to the titleView
        titleView.setText(title);

        // get the string with the tags
        int tagsIndex = cursor.getColumnIndex("Tags");
        String stringTags = cursor.getString(tagsIndex);

        // if the note has tags
        if (stringTags != null) {

            // convert the string with tags to an ArrayList<String>
            Note note = new Note();
            ArrayList<String> arrayTags = note.getUpdatedArrayTags(stringTags);
            System.out.println(arrayTags);

            // set an adapter for the tags
            LinearLayoutManager horizontalLLM =
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                            false);
            recyclerView.setLayoutManager(horizontalLLM);
            TagsAdapter tagsAdapter = new TagsAdapter(context, arrayTags);
            recyclerView.setAdapter(tagsAdapter);
        }


    }
}
