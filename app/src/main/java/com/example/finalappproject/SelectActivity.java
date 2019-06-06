package com.example.finalappproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void selectClicked(View v) {
        // give user access to their gallery

        // show chosen photo in the imageview
    }

    public void convertClicked(View v) {
        // call ImageHelper to check the dimensions of the photo and resize if necessary

        // call request class to send photo to microsoft API
    }


}
