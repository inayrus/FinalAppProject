/* *************************************************************************************************
 * A file that contains functions to place a note's tags in a LinearLayout view.
 *
 * by Valerie Sawirja
 * ************************************************************************************************/

package com.example.finalappproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TagsAdapter {

    private Context context;

    public TagsAdapter(Context context) {
        this.context = context;
    }

    // a function that places the tags on a note in a LinearLayout.
    public void setTags(LinearLayout tagHolder, String stringTags) {

        tagHolder.removeAllViews();

        // add tag TextViews if a note has tags
        if (stringTags != null) {

            // convert the string with tags to an ArrayList<String>
            Note note = new Note();
            ArrayList<String> arrayTags = note.getUpdatedArrayTags(stringTags);

            // make TextViews of the tags and place them in the view
            for (int i = 0, lenTags = arrayTags.size(); i < lenTags; i++) {
                TextView tagView = createTextView(arrayTags.get(i));
                tagHolder.addView(tagView);
            }
        }
    }

    // creates a TextView from a String
    private TextView createTextView(String tag) {
        TextView txtTag = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 5, 5);
        txtTag.setLayoutParams(params);

        // formats the TextView that has the tags in it
        txtTag.setText(tag);
        txtTag.setTag(tag);
        txtTag.setTextSize(12);
        txtTag.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        txtTag.setTextColor(Color.WHITE);
        txtTag.setPadding(15, 7, 15, 7);
        txtTag.setFocusable(false);

        return txtTag;
    }
}
