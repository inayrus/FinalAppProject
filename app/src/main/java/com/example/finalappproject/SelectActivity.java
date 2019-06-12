package com.example.finalappproject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

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
        Intent pickedPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // being handled by the onActivityResult method
        startActivityForResult(pickedPhoto, 0);
    }

    // called when a photo is chosen from the gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        ImageView imageView = findViewById(R.id.imageView);

        if (resultCode == RESULT_OK) {
            // show chosen photo in the imageview
            Uri selectedImage = imageReturnedIntent.getData();
            System.out.println(selectedImage);
            imageView.setImageURI(selectedImage);
            imageView.setVisibility(View.VISIBLE);

            // make convert button visible
            findViewById(R.id.convertButton).setVisibility(View.VISIBLE);
        }
    }

    public void convertClicked(View v) {


        // call ImageHelper to check the dimensions of the photo and resize if necessary

        // call request class to send photo to microsoft API
    }
}
