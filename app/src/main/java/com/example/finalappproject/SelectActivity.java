package com.example.finalappproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class SelectActivity extends AppCompatActivity implements ConvertRequest.Callback {

    // private attribute
    private Uri selectedImage;

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
            this.selectedImage = imageReturnedIntent.getData();

            // resize the image for the view
            Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView);
//            imageView.setImageURI(selectedImage);
            imageView.setVisibility(View.VISIBLE);

            // make convert button visible
            findViewById(R.id.convertButton).setVisibility(View.VISIBLE);
        }
    }

    public void convertClicked(View v) {

        // call ImageHelper to check the dimensions of the photo and resize if necessary

        // call request class to send photo to microsoft API
        ConvertRequest x = new ConvertRequest(this);

        try {
            File imageFile = new File(getRealPathFromURI(selectedImage));
            System.out.println(imageFile);
            x.convertToText(this, imageFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("AHHHHHHHHHHH");
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

        // got the note from the microsoft API
    @Override
    public void gotNote(Note note) {
        // is it possible to edit a textfield in the activity below this one?
        // so if this activity is an extra layer above the other one?

        // is it possible to give the string with new text to the onResume of the NoteActivity?
    }

    @Override
    public void gotNoteError(String message) {

    }
}
