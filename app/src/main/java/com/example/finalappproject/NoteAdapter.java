package com.example.finalappproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

        // add tag buttons if a note has tags
        if (stringTags != null) {
            // convert the string with tags to an ArrayList<String>
            Note note = new Note();
            ArrayList<String> arrayTags = note.getUpdatedArrayTags(stringTags);

            // only add new tags if they were not already set in the view
            if (arrayTags.size() > tagHolder.getChildCount()) {

                // start with the first unplaced tag
                for (int i = tagHolder.getChildCount(), lenTags = arrayTags.size(); i < lenTags; i++) {

                    // add a clickable textview
                    TextView txtTag = new TextView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 0, 5, 5);
                    txtTag.setLayoutParams(params);

                    // format of the text
                    String tag = arrayTags.get(i);
                    formatView(txtTag, tag);

                    // add textView to layout
                    tagHolder.addView(txtTag);
                }
            }
        }
    }

    // formats the TextView that has the tags in it
    private void formatView(TextView txtTag, String tag) {
        txtTag.setText(tag);
        txtTag.setTag(tag);
        txtTag.setTextSize(12);
        txtTag.setBackgroundColor(Color.GRAY);
        txtTag.setTextColor(Color.WHITE);
        txtTag.setPadding(15, 7, 15, 7);
        txtTag.setFocusable(false);
        txtTag.setOnClickListener(new mainTagClicked());
    }

    // onClick method for when a tag in the MainView is clicked
    private class mainTagClicked implements View.OnClickListener {

        public void onClick(View v) {
            TextView tagView = (TextView) v;

            // retrieve what tag is clicked
            String tag = tagView.getText().toString();
            System.out.println("clicked: " + tag);

            // TODO: call database to filter on this tag
        }
    }

}
