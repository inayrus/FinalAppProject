package com.example.finalappproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private ArrayList<String> tags;

    // the constructor
    public TagsAdapter(Context context, ArrayList<String> tags) {
        this.tags = tags;
    }

    // inflates the item layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the itemview
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String tag = tags.get(position);
        viewHolder.tagView.setText(tag);
    }

    @Override
    public void onViewRecycled(ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;

        // constructor
        ViewHolder(View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tagItem);
        }
    }
}
