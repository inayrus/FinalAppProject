package com.example.finalappproject;

import java.util.ArrayList;

public class Note {

    // attributes
    private int id;
    private String title;
    private String content;
    private ArrayList<String> tags;
    private int timestamp;

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
