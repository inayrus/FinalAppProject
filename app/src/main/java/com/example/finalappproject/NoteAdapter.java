package com.example.finalappproject;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

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
        TextView tagsView = view.findViewById(R.id.mainTags);

        // get the value of the title
        int columnIndex = cursor.getColumnIndex("Title");
        String value = cursor.getString(columnIndex);

        // set the value to the titleView
        titleView.setText(value);
    }
}
