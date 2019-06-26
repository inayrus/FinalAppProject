/* *************************************************************************************************
 * The NoteAdapter takes a cursor with Notes from the database,
 * and places them in the ListView of MainActivity.
 *
 * by Valerie Sawirja
 * ************************************************************************************************/

package com.example.finalappproject;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class NoteAdapter extends ResourceCursorAdapter {

    public NoteAdapter(Context context, Cursor c) {
        // constructor, new version needs flags
        super(context, R.layout.list_note_item, c, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    // method that puts the data in the recycled view,
    // the cursor points to a row in the db
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // get the layout elements
        TextView titleView = view.findViewById(R.id.mainTitle);

        // get the value of the title
        int titleIndex = cursor.getColumnIndex("Title");
        String title = cursor.getString(titleIndex);

        // set the value to the titleView
        titleView.setText(title);

        // get the string with the tags
        int tagsIndex = cursor.getColumnIndex("Tags");
        String stringTags = cursor.getString(tagsIndex);

        // programmatically adding tag buttons
        LinearLayout tagHolder = view.findViewById(R.id.tagsHolder);
        TagsAdapter tagsAdapter = new TagsAdapter(context);
        tagsAdapter.setTags(tagHolder, stringTags);
    }
}
