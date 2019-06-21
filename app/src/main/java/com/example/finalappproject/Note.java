package com.example.finalappproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Note implements Serializable {

    // attributes
    private int id;
    private String title;
    private String content;
    private ArrayList<String> arrayTags;
    private String stringTags;
    private int timestamp;

    // convert the string with tags to an array
    public ArrayList<String> getUpdatedArrayTags(String stringTags) {
        this.arrayTags = new ArrayList<>(Arrays.asList(stringTags.split(",")));
        return arrayTags;
    }

    // convert the tags array to a string, seperated with commas
    public String updateStringTags(ArrayList<String> array) {
        this.stringTags = android.text.TextUtils.join(",", array);
        return stringTags;
    }

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

    public void setStringTags(String stringTags) {
        this.stringTags = stringTags;
    }

    public void setArrayTags(ArrayList<String> arrayTags) {
        this.arrayTags = arrayTags;
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

    public ArrayList<String> getArrayTags() {
        return arrayTags;
    }

    public String getStringTags() {
        return stringTags;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
