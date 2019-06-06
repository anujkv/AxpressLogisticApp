package com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.uploadDocumentResponse.UploadDocumentsResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.UploadDoc.UploadDocumentsPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageConverter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.UploadDocument.UploadDocumentsView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadDocumentation extends AppCompatActivity implements View.OnClickListener, UploadDocumentsView {
    @BindView(R.id.app_bar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView lable;
    @BindView(R.id.backbtn_toolbar)
    ImageView backbtn_toolbar;
    CardView aadharcardCardView, pancardCardView, photographyCardView;
    ProgressBar progressBarAdharcard, progressBarPancard, progressBarPhotogarph;
    ProgressDialog progressDialog;

    int SELECT_IMAGE = 1;
    int SELECT_IMAGE_PDF_ADHAR = 2;
    int SELECT_IMAGE_PDF_PAN = 3;
    File sourceFile;
    int totalSize = 0;
    String FILE_UPLOAD_URL = "http://www.example.com/upload/UploadToServer.php";
    Bitmap bitmapAadhar, bitmapPancard, bitmapPhoto;
    ImageConverter convertImage;
    MainPresenter presenter;

    String imagepath, aadharpath, pancardpath;
    @BindView(R.id.aadharcardImage)
    ImageView aadharcardImage;
    @BindView(R.id.pancardImage)
    ImageView pancardImage;
    @BindView(R.id.photographImage)
    ImageView photographImage;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documentation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        lable.setText(CONSTANT.UPLOAD_DOCUMENTS);
        backbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_arrow_back));
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init() {
        aadharcardCardView = findViewById(R.id.aadharcardCardView);
        pancardCardView = findViewById(R.id.pancardCardView);
        photographyCardView = findViewById(R.id.photographyCardView);
        progressBarAdharcard = findViewById(R.id.progressBarAdharcard);
        progressBarPancard = findViewById(R.id.progressBarPancard);
        progressBarPhotogarph = findViewById(R.id.progressBarPhotogarph);
        submitBtn = findViewById(R.id.submitBtn);
        aadharcardCardView.setOnClickListener(this);
        pancardCardView.setOnClickListener(this);
        photographyCardView.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aadharcardCardView:
                Toast.makeText(getApplicationContext(), "Uploading..", Toast.LENGTH_SHORT).show();
                Intent intentAadhar = new Intent();
//                intentAadhar.setType("application/pdf");
                intentAadhar.setType("image/*");
                intentAadhar.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentAadhar, "Select Aadharcard"), SELECT_IMAGE_PDF_ADHAR);
                break;

            case R.id.pancardCardView:
                Toast.makeText(getApplicationContext(), "Uploading..", Toast.LENGTH_SHORT).show();
                Intent intentPan = new Intent();
                intentPan.setType("image/*");
                intentPan.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentPan, "Select Pancard"), SELECT_IMAGE_PDF_PAN);
                break;

            case R.id.photographyCardView:
                Toast.makeText(getApplicationContext(), "Uploading..", Toast.LENGTH_SHORT).show();
                Intent intentPhoto = new Intent();
                intentPhoto.setType("image/*");
                intentPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentPhoto, "Select Picture"), SELECT_IMAGE);
                break;
            case R.id.submitBtn:
                if(isValid()){
                    presenter = new UploadDocumentsPresenterImpl(this);
                    presenter.init();
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage(CONSTANT.LOADING_STATUS);
                    progressDialog.show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"images not selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isValid() {
        if(bitmapAadhar!=null && bitmapPancard!=null && bitmapPhoto!=null){
            return true;
        }else{
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Uri selectedImageUri = data.getData();
                        imagepath = getPath(selectedImageUri);
                        bitmapPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        if (bitmapPhoto != null) {
                            Toast.makeText(getApplicationContext(), "image loaded", Toast.LENGTH_SHORT).show();
//                            new UploadFileToServer().execute();
//                            photographImage.setImageBitmap(bitmapPhoto);
                            photographImage.setImageResource(R.drawable.ic_cloud_done_green_24dp);
                        } else {
                            Toast.makeText(getApplicationContext(), "can't loaded image,try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SELECT_IMAGE_PDF_ADHAR) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Uri selectedImageUri = data.getData();
                        aadharpath = getPath(selectedImageUri);
                        bitmapAadhar = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        if (bitmapAadhar != null) {
                            Toast.makeText(getApplicationContext(), "pdf loaded", Toast.LENGTH_SHORT).show();
//                            new UploadDocument.UploadFileToServer().execute();
//                            aadharcardImage.setImageBitmap(bitmapAadhar);
                            aadharcardImage.setImageResource(R.drawable.ic_cloud_done_green_24dp);

                        } else {
                            Toast.makeText(getApplicationContext(), "can't loaded pdf,try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SELECT_IMAGE_PDF_PAN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Uri selectedImageUri = data.getData();
                        pancardpath = getPath(selectedImageUri);
                        bitmapPancard = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        if (bitmapPancard != null) {
                            Toast.makeText(getApplicationContext(), "pdf loaded", Toast.LENGTH_SHORT).show();
//                            new UploadDocument.UploadFileToServer().execute();
//                            pancardImage.setImageBitmap(bitmapPancard);
                            pancardImage.setImageResource(R.drawable.ic_cloud_done_green_24dp);

                        } else {
                            Toast.makeText(getApplicationContext(), "can't loaded pdf,try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getAadharcardImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapAadhar.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

//        return new Gson().toJson(b);
        return Base64.encodeToString(b,Base64.DEFAULT);
//        return convertImage.convertBitmapToBase(bitmapAadhar);
    }

    @Override
    public String getPancardImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapPancard.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
//        return new Gson().toJson(b);
        return Base64.encodeToString(b,Base64.DEFAULT);
//        return convertImage.convertBitmapToBase(bitmapPancard);
    }

    @Override
    public String getPhotographImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapPhoto.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
//        return new Gson().toJson(b);
        return Base64.encodeToString(b,Base64.DEFAULT);
//        return convertImage.convertBitmapToBase(bitmapPhoto);
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof UploadDocumentsResponse) {
            progressDialog.dismiss();
            UploadDocumentsResponse response = (UploadDocumentsResponse) object;
            Log.e("response", new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), "Documentation Uploaded!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Documentation not Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {

    }

//    private class UploadFileToServer extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            HttpURLConnection.setFollowRedirects(false);
//            HttpURLConnection connection = null;
//            String fileName = sourceFile.getName();
//
//            try {
//                connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
//                connection.setRequestMethod("POST");
//                String boundary = "---------------------------boundary";
//                String tail = "\r\n--" + boundary + "--\r\n";
//                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//                connection.setDoOutput(true);
//
//                String metadataPart = "--" + boundary + "\r\n"
//                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
//                        + "" + "\r\n";
//
//                String fileHeader1 = "--" + boundary + "\r\n"
//                        + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
//                        + fileName + "\"\r\n"
//                        + "Content-Type: application/octet-stream\r\n"
//                        + "Content-Transfer-Encoding: binary\r\n";
//
//                long fileLength = sourceFile.length() + tail.length();
//                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
//                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
//                String stringData = metadataPart + fileHeader;
//
//                long requestLength = stringData.length() + fileLength;
//                connection.setRequestProperty("Content-length", "" + requestLength);
//                connection.setFixedLengthStreamingMode((int) requestLength);
//                connection.connect();
//
//                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                out.writeBytes(stringData);
//                out.flush();
//
//                int progress = 0;
//                int bytesRead = 0;
//                byte buf[] = new byte[1024];
//                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
//                while ((bytesRead = bufInput.read(buf)) != -1) {
//                    // write output
//                    out.write(buf, 0, bytesRead);
//                    out.flush();
//                    progress += bytesRead; // Here progress is total uploaded bytes
//                    publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
//                }
//
//                // Write closing boundary and close stream
//                out.writeBytes(tail);
//                out.flush();
//                out.close();
//
//                // Get server response
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line = "";
//                StringBuilder builder = new StringBuilder();
//                while((line = reader.readLine()) != null) {
//                    builder.append(line);
//                }
//
//
//            } catch (Exception e) {
//                // Exception
//            } finally {
//                if (connection != null) connection.disconnect();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // setting progress bar to zero
//            progressBarPhotogarph.setProgress(0);
//            progressBarPhotogarph.setVisibility(View.VISIBLE); // Showing the stylish material progressbar
//            sourceFile = new File(imagepath);
//            totalSize = (int)sourceFile.length();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(String... progress) {
//            Log.d("PROG", progress[0]);
//            progressBarPhotogarph.setProgress(Integer.parseInt(progress[0])); //Updating progress
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.e("Response", "Response from server: " + s);
//            super.onPostExecute(s);
//        }
//    }
}
