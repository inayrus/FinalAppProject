package com.example.finalappproject;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.selectActToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Photo");
        toolbar.setTitleTextColor(Color.WHITE);

        // Get a support ActionBar for this toolbar
        ActionBar actionBar = getSupportActionBar();

        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void selectClicked(View v) {
        // give user access to their gallery

        // show chosen photo in the imageview
        // make convert button visible
        findViewById(R.id.convertButton).setVisibility(View.VISIBLE);
    }

    public void convertClicked(View v) {
        // call ImageHelper to check the dimensions of the photo and resize if necessary

        // call request class to send photo to microsoft API
    }


}
