package com.example.it2.axpresslogisticapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

public class ScannedDataActivity extends AppCompatActivity implements View.OnClickListener {
    private Uri mCropImageUri;
    private CropImageView mCropImageView;
    // Activity request codes

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "axpress/camera";
    public static final int MEDIA_TYPE_IMAGE = 1;

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 11;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE2_REQUEST = 2;
    private static String imageStoragePath;
    Bitmap bitmap1, bitmap2;
    String pathToImage;
    StringBuilder stringBuilder;
    ImageButton backbtn_toolbar, savebtn_toolbar, icon_rotate_right, icon_rotate_left,
            icon_rotate_rightFront, icon_rotate_leftFront;
    ImageView imgFrontPreview, imgBackPreview;
    static EditText edtname;
    EditText edtJobTitle;
    EditText edtMobile;
    EditText edtEmail;
    EditText edtAddress;
    EditText edtWebsite;
    EditText edtCompany;
    EditText edtFax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_data);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("My Card Details");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        edtname = findViewById(R.id.edtname);
        edtJobTitle = findViewById(R.id.edtJobTitle);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtCompany = findViewById(R.id.edtCompany);
        edtAddress = findViewById(R.id.edtAddress);
        edtFax = findViewById(R.id.edtFax);

        imgFrontPreview = findViewById(R.id.imageViewFront);
        imgBackPreview = findViewById(R.id.imageViewBack);
        icon_rotate_right = findViewById(R.id.icon_rotate_right);
        icon_rotate_left = findViewById(R.id.icon_rotate_left);

        icon_rotate_rightFront = findViewById(R.id.icon_rotate_rightFront);
        icon_rotate_leftFront = findViewById(R.id.icon_rotate_leftFront);
    }

    @Override
    public void onClick(View v) {

    }
}
