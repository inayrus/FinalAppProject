package com.example.finalappproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.HandwritingRecognitionOperation;
import com.microsoft.projectoxford.vision.contract.HandwritingRecognitionOperationResult;
import com.microsoft.projectoxford.vision.contract.HandwritingTextLine;
import com.microsoft.projectoxford.vision.contract.HandwritingTextWord;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class SelectActivity extends AppCompatActivity {

    // button that enables image selection
    private Button selectImageButton;

    // URI of chosen image
    private Uri selectedImage;

    // bitmap of chosen image
    private Bitmap bitmap;

    // text field with the converted result
    EditText resultText;

    Button convertButton;

    Button addButton;

    private VisionServiceClient client;

    //max retry times to get operation result
    private int retryCountThreshold = 30;

    private Note existingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // unpack existing note
        Intent intent = getIntent();
        this.existingNote = (Note) intent.getSerializableExtra("Existing note");

        // link to the API
        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.subscription_key), getString(R.string.subscription_apiroot));
        }

        selectImageButton = findViewById(R.id.buttonSelectImage);
        resultText = findViewById(R.id.editTextResult);
        convertButton = findViewById(R.id.convertButton);
        addButton = findViewById(R.id.addButton);

        convertButton.setCursorVisible(false);

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

    // adds the recognized text to the note
    public void addClicked(View v) {

        String newText = resultText.getText().toString();
        Note note;

        if (existingNote != null) {
            // add the recognized text to the text of the existing note
            note = existingNote;
            String oldText = note.getContent();
            String combinedText = oldText + "\n" + newText;
            note.setContent(combinedText);
        }
        else {
            // create a new note and put the text in the content
            note = new Note();
            note.setContent(newText);
            note.setTitle("");
        }

        // send the note to the NoteActivity
        Intent intent = new Intent(SelectActivity.this, NoteActivity.class);
        intent.putExtra("Note", note);
        startActivity(intent);
        finish();
    }

    public void selectClicked(View v) {

        // give user access to their gallery
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // being handled by the onActivityResult method
        startActivityForResult(pickPhoto, 0);
    }

    // called when a photo is chosen from the gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        // if image is selected successfully
        if (resultCode == RESULT_OK) {

            // set URI and bitmap
            this.selectedImage = imageReturnedIntent.getData();

            bitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                    selectedImage, getContentResolver());

            // resize the image for the view
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.imageView);
                Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView);

                // make convert button visible
                convertButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void convertClicked(View v) {
        // everything below this is new and based on the Cognitive Vision Android sample

        selectImageButton.setEnabled(false);
        resultText.setText("Analyzing...");

        try {
            new doRequest(this).execute();
        } catch (Exception e) {
            resultText.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    private static class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        private WeakReference<SelectActivity> recognitionActivity;

        public doRequest(SelectActivity activity) {
            recognitionActivity = new WeakReference<SelectActivity>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (recognitionActivity.get() != null) {
                    return recognitionActivity.get().process();
                }
            } catch (Exception e) {
                // Store error
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            if (recognitionActivity.get() == null) {
                return;
            }
            // Display based on error existence
            if (e != null) {
                recognitionActivity.get().resultText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                HandwritingRecognitionOperationResult r = gson.fromJson(data, HandwritingRecognitionOperationResult.class);

                StringBuilder resultBuilder = new StringBuilder();
                //if recognition result status is failed. display failed
                if (r.getStatus().equals("Failed")) {
                    resultBuilder.append("Error: Recognition Failed");
                } else {
                    for (HandwritingTextLine line : r.getRecognitionResult().getLines()) {
                        for (HandwritingTextWord word : line.getWords()) {
                            resultBuilder.append(word.getText() + " ");
                        }
                        resultBuilder.append("\n");
                    }
                    resultBuilder.append("\n");
                }

                recognitionActivity.get().resultText.setText(resultBuilder);
                // set cursor visible
                recognitionActivity.get().resultText.setCursorVisible(true);
                // disable recognize text button
                recognitionActivity.get().convertButton.setVisibility(View.INVISIBLE);
                // make add button visible
                recognitionActivity.get().addButton.setVisibility(View.VISIBLE);
            }
            recognitionActivity.get().selectImageButton.setEnabled(true);
        }
    }

    private String process() throws VisionServiceException, IOException, InterruptedException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray())) {
                //post image and got operation from API
                HandwritingRecognitionOperation operation = this.client.createHandwritingRecognitionOperationAsync(inputStream);

                HandwritingRecognitionOperationResult operationResult;
                //try to get recognition result until it finished.

                int retryCount = 0;
                do {
                    if (retryCount > retryCountThreshold) {
                        throw new InterruptedException("Can't get result after retry in time.");
                    }
                    Thread.sleep(1000);
                    operationResult = this.client.getHandwritingRecognitionOperationResultAsync(operation.Url());
                }
                while (operationResult.getStatus().equals("NotStarted") || operationResult.getStatus().equals("Running"));

                String result = gson.toJson(operationResult);
                Log.d("result", result);

                return result;

            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
