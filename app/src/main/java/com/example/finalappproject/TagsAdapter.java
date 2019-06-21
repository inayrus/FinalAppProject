package com.example.finalappproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TagsAdapter {

    private Context context;
    private CharSequence[] allTags;

    public TagsAdapter(Context context) {
        this.context = context;
    }

    public void setTags(LinearLayout tagHolder, String stringTags) {



        // add tag buttons if a note has tags
        if (stringTags != null) {
            // convert the string with tags to an ArrayList<String>
            Note note = new Note();
            ArrayList<String> arrayTags = note.getUpdatedArrayTags(stringTags);
            System.out.println("entered the tagsadapter in: " + context);
            System.out.println("tags to put in there: " + stringTags);

            int childCount = tagHolder.getChildCount();

            // if there are tags, but the amounts don't match up: delete all views and start again
            if ((arrayTags.size() != childCount) & childCount != 0) {

                tagHolder.removeAllViews();

            }
            // place tags
            if ((arrayTags.size() != childCount)) {
                // childCount == 0: add all the tags
                for (int i = 0, lenTags = arrayTags.size(); i < lenTags; i++) {

                    TextView tagView = createTextView(arrayTags.get(i));

                    // add textView to layout
                    tagHolder.addView(tagView);

                }
            }
            // else (count and size are same and not 0) check if content matches up.
            else {
                // remember what tags need to be added
                ArrayList<String> toAdd = new ArrayList<>(arrayTags);

                int index = 0;
                while (index < tagHolder.getChildCount()) {
                    TextView tagView = (TextView)tagHolder.getChildAt(index);

                    if (!arrayTags.contains(tagView.getText())) {
                        // remove view from UI if it's not in arrayTags
                        tagHolder.removeView(tagView);
                    }
                    else {
                        // if tag is in arrayTags, delete it from the toAdd list
                        toAdd.remove(tagView.getText());
                        index++;
                    }
                }

                // if a view is removed, add the missing tag
                if (toAdd.size() != 0) {
                    for (String tag: toAdd) {

                        TextView tagView = createTextView(tag);
                        // add textView to layout
                        tagHolder.addView(tagView);
                    }
                }
            }
        }
    }

    private TextView createTextView(String tag) {
        // add a clickable textview
        TextView txtTag = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 5, 5);
        txtTag.setLayoutParams(params);

        // formats the TextView that has the tags in it
        txtTag.setText(tag);
        txtTag.setTag(tag);
        txtTag.setTextSize(12);
        txtTag.setBackgroundColor(Color.GRAY);
        txtTag.setTextColor(Color.WHITE);
        txtTag.setPadding(15, 7, 15, 7);
        txtTag.setFocusable(false);
        txtTag.setOnClickListener(new mainTagClicked());

        return txtTag;
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
