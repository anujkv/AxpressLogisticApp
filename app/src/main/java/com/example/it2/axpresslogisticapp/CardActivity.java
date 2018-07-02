package com.example.it2.axpresslogisticapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.example.it2.axpresslogisticapp.CardScanner.BITMAP_SAMPLE_SIZE;
import static com.example.it2.axpresslogisticapp.CardScanner.IMAGE_EXTENSION;
import static com.example.it2.axpresslogisticapp.CardScanner.KEY_IMAGE_STORAGE_PATH;

public class CardActivity extends AppCompatActivity implements View.OnClickListener{

    private Uri mCropImageUri;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 11;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE2_REQUEST = 2;
    private static String imageStoragePath;
    Bitmap bitmap1,bitmap2;
    String pathToImage;
    TextView txt_front_view,txt_back_view;
    StringBuilder stringBuilder;
    ImageButton backbtn_toolbar,savebtn_toolbar,icon_rotate_right,icon_rotate_left,
            icon_rotate_rightFront,icon_rotate_leftFront;
    ImageView imgFrontPreview,imgBackPreview;
    EditText edtname,edtJobTitle,edtMobile,edtEmail,edtAddress,edtWebsite,edtCompany,edtFax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Card Scan");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));

        init();
        icon_rotate_right.setOnClickListener(this);
        icon_rotate_left.setOnClickListener(this);
        icon_rotate_leftFront.setOnClickListener(this);
        icon_rotate_rightFront.setOnClickListener(this);

        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }

        imgFrontPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFrontPreview.setVisibility(View.VISIBLE);
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, PICK_IMAGE_REQUEST);
            }
        });
        restoreFromBundle(savedInstanceState);

        imgBackPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBackPreview.setVisibility(View.VISIBLE);
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, PICK_IMAGE2_REQUEST);
            }
        });
        restoreFromBundle(savedInstanceState);

        //init image
//        imagefront = BitmapFactory.decodeResource(getResources(), R.drawable.cardforntimage);
    }

    private void init() {
        edtname =  findViewById(R.id.edtname);
        edtJobTitle =  findViewById(R.id.edtJobTitle);
        edtMobile =  findViewById(R.id.edtMobile);
        edtEmail =  findViewById(R.id.edtEmail);
        edtWebsite =  findViewById(R.id.edtWebsite);
        edtCompany =  findViewById(R.id.edtCompany);
        edtAddress =  findViewById(R.id.edtAddress);
        edtFax = findViewById(R.id.edtFax);

        imgFrontPreview = findViewById(R.id.imageViewFront);
        imgBackPreview = findViewById(R.id.imageViewBack);
        icon_rotate_right = findViewById(R.id.icon_rotate_right);
        icon_rotate_left = findViewById(R.id.icon_rotate_left);

        icon_rotate_rightFront = findViewById(R.id.icon_rotate_rightFront);
        icon_rotate_leftFront = findViewById(R.id.icon_rotate_leftFront);
    }

    public void processImage(){
        String OCRresult = stringBuilder.toString();
        System.out.println(" OUTPUT>>>" +stringBuilder.toString());
        extractName(OCRresult);
        extractEmail(OCRresult);
        extractPhone(OCRresult);
        extractWebsite(OCRresult);
        extractJobTitle(OCRresult);
        extractCompany(OCRresult);
        extractAddress(OCRresult);
    }

    public void extractName(String str){
        System.out.println("Getting the Name");
//        edtname.setText("");
        final String NAME_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m =  p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
            edtname.setText(m.group().trim());
            System.out.println(edtname);
        }
    }

    public void extractJobTitle(String str){
        System.out.println("Getting the Job Title");
//        edtJobTitle.setText("");
        final String NAME_REGEX = "([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m =  p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
            edtJobTitle.setText(m.group().trim());
            System.out.println(edtJobTitle);
        }
    }

    public void extractEmail(String str) {
        System.out.println("Getting the email");
//        edtEmail.setText("");
        final String EMAIL_REGEX = "(\\w+\\@\\w+\\.\\w+)";
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.MULTILINE);
        Log.e("EMAIL_PATTERN : ",p.toString());
        Matcher m = p.matcher(str);   // get a matcher object
        Log.e("EMAIL_MATCHER : ",m.toString());
        if(m.find()){
            System.out.println(m.group());
            edtEmail.setText(m.group().trim());
            System.out.println(edtEmail);
        }
    }

    public void extractPhone(String str){
        Log.e("STRING : ",str);
        System.out.println("Getting Phone Number");
//        edtMobile.setText("");
        final String PHONE_REGEX = "(\\+?\\d+\\-?\\d+)";
        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
        Log.e("PHONE_PATTERN : ",p.toString());
        Matcher m = p.matcher(str);   // get a matcher object
        Log.e("PHONE" +
                "_MATCHER : ",m.toString());
        if(m.find()){
            System.out.println(m.group());
            edtMobile.setText(m.group().trim());
            System.out.println(edtMobile);
        }
    }

    public void extractWebsite(String str){
        System.out.println("Getting Website");
//        edtWebsite.setText("");
        final String WEBSITE_REGEX = "(www\\.?\\w+\\.?\\w+)";
        Pattern p = Pattern.compile(WEBSITE_REGEX, Pattern.MULTILINE);
        Log.e("WEBSITE_PATTERN : ",p.toString());
        Matcher m = p.matcher(str);   // get a matcher object
        Log.e("WEBSITE_MATCHER : ",m.toString());
        if(m.find()){
            System.out.println(m.group());
            edtWebsite.setText(m.group().trim());
            System.out.println(edtWebsite);
        }
    }

    public void extractCompany(String str){
        System.out.println("Getting the Company");
//        edtCompany.setText("");
        final String NAME_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m =  p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
            edtCompany.setText(m.group().trim());
            System.out.println(edtCompany);
        }
    }

    public void extractAddress(String str){
        System.out.println("Getting the Address");
//        edtAddress.setText("");
        final String NAME_REGEX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGEX, Pattern.MULTILINE);
        Matcher m =  p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
            edtAddress.setText(m.group().trim());
            System.out.println(edtAddress);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backbtn_toolbar:
                finish();
                break;
                
            //for save on click...
            case R.id.mapbtn_toolbar:
                break;
                                
            case R.id.icon_rotate_right:
                rotation_right(PICK_IMAGE2_REQUEST);
                break;

            case R.id.icon_rotate_left:
                rotation_left(PICK_IMAGE2_REQUEST);
                break;


            case R.id.icon_rotate_rightFront:
                rotation_right(PICK_IMAGE_REQUEST);
                break;

            case R.id.icon_rotate_leftFront:
                rotation_left(PICK_IMAGE_REQUEST);
                break;
        }
    }

    private void rotation_left(int val) {
        if(val==1){
            if(bitmap1==null){
                Toast.makeText(getApplicationContext(), "Image photo is not yet set", Toast.LENGTH_LONG).show();
            }
            else {
                Matrix matrix = new Matrix();
                imgFrontPreview.setScaleType(ImageView.ScaleType.MATRIX);   //required
                matrix.postRotate(-90,imgFrontPreview.getDrawable().getBounds().width()/2,imgFrontPreview.getDrawable().getBounds().height()/2);
                Bitmap bmp=Bitmap.createBitmap(bitmap1, 0, 0,bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                bitmap1.recycle();
                bitmap1=bmp;
                imgFrontPreview.setImageBitmap(bitmap1);
                textRecognizer(bitmap1);
            }
        } else if (val ==2){
            if(bitmap2==null){
                Toast.makeText(getApplicationContext(), "Image photo is not yet set", Toast.LENGTH_LONG).show();
            }
            else {
                Matrix matrix = new Matrix();
                imgBackPreview.setScaleType(ImageView.ScaleType.MATRIX);   //required
                matrix.postRotate(-90,imgBackPreview.getDrawable().getBounds().width()/2,imgBackPreview.getDrawable().getBounds().height()/2);
                Bitmap bmp=Bitmap.createBitmap(bitmap2, 0, 0,bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
                bitmap2.recycle();
                bitmap2=bmp;
                imgBackPreview.setImageBitmap(bitmap2);
                textRecognizer(bitmap2);
            }
        }

    }

    private void rotation_right(int val) {
        if(val==1){
            if(bitmap1==null){
                Toast.makeText(getApplicationContext(), "Image photo is not yet set", Toast.LENGTH_LONG).show();
            }
            else {
                Matrix matrix = new Matrix();
                imgFrontPreview.setScaleType(ImageView.ScaleType.MATRIX);   //required
                matrix.postRotate(90,imgFrontPreview.getDrawable().getBounds().width()/2,imgFrontPreview.getDrawable().getBounds().height()/2);
                Bitmap bmp=Bitmap.createBitmap(bitmap1, 0, 0,bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                bitmap1.recycle();
                bitmap1=bmp;
                imgFrontPreview.setImageBitmap(bitmap1);
                textRecognizer(bitmap1);
            }
        } else if (val ==2){
            if(bitmap2==null){
                Toast.makeText(getApplicationContext(), "Image photo is not yet set", Toast.LENGTH_LONG).show();
            }
            else {
                Matrix matrix = new Matrix();
                imgBackPreview.setScaleType(ImageView.ScaleType.MATRIX);   //required
                matrix.postRotate(90,imgBackPreview.getDrawable().getBounds().width()/2,imgBackPreview.getDrawable().getBounds().height()/2);
                Bitmap bmp=Bitmap.createBitmap(bitmap2, 0, 0,bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
                bitmap2.recycle();
                bitmap2=bmp;
                imgBackPreview.setImageBitmap(bitmap2);
                textRecognizer(bitmap2);
            }
        }
    }
//
//    private void checkFile(File dir) {
//        //directory does not exist, but we can successfully create it
//        if (!dir.exists()&& dir.mkdirs()){
//            copyFiles();
//        }
//        //The directory exists, but there is no data file in it
//        if(dir.exists()) {
//            String datafilepath = datapath+ "/tessdata/eng.traineddata";
//            File datafile = new File(datafilepath);
//            if (!datafile.exists()) {
//                copyFiles();
//            }
//        }
//    }
//
//    private void copyFiles() {
//        try {
//            //location we want the file to be at
//            String filepath = datapath + "/tessdata/eng.traineddata";
//
//            //get access to AssetManager
//            AssetManager assetManager = getAssets();
//
//            //open byte streams for reading/writing
//            InputStream instream = assetManager.open("tessdata/eng.traineddata");
//            OutputStream outstream = new FileOutputStream(filepath);
//
//            //copy the file to the location specified by filepath
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = instream.read(buffer)) != -1) {
//                outstream.write(buffer, 0, read);
//            }
//            outstream.flush();
//            outstream.close();
//            instream.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addToContacts(){
//
//        // Creates a new Intent to insert a contact
//        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
//        // Sets the MIME type to match the Contacts Provider
//        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//
//        //Checks if we have the name, email and phone number...
//        if(displayName.getText().length() > 0 && ( displayPhone.getText().length() > 0 || displayEmail.getText().length() > 0 )){
//            //Adds the name...
//            intent.putExtra(ContactsContract.Intents.Insert.NAME, displayName.getText());
//
//            //Adds the email...
//            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, displayEmail.getText());
//            //Adds the email as Work Email
//            intent .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
//
//            //Adds the phone number...
//            intent.putExtra(ContactsContract.Intents.Insert.PHONE, displayPhone.getText());
//            //Adds the phone number as Work Phone
//            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
//
//            //starting the activity...
//            startActivity(intent);
//        }else{
//            Toast.makeText(getApplicationContext(), "No information to add to contacts!", Toast.LENGTH_LONG).show();
//        }
//
//
//    }

    /**
     * Restoring store image path from saved instance state
     */
    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }


    /**
     * Requesting permissions using Dexter library
     */
    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Capturing Camera Image will launch camera app requested image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Saving stored image path to saved instance state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    /**
     * Activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode ==PICK_IMAGE_REQUEST && data != null) {
            Uri selectedImage = data.getData();
//            saveImage( );
            imgFrontPreview.setImageURI(selectedImage);
            pathToImage = selectedImage.getPath();
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                textRecognizer(bitmap1);
                flush_edt_fields();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //stuff to do on click button upload cover??
        }
        else if (requestCode ==PICK_IMAGE2_REQUEST && data != null) {
            Uri selectedImage = data.getData();
//            saveImage( );
            imgBackPreview.setImageURI(selectedImage);
            pathToImage = selectedImage.getPath();
            //stuff to do on click button upload cover??
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                textRecognizer(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void flush_edt_fields() {
        edtname.setText("");
        edtJobTitle.setText("");
        edtMobile.setText("");
        edtWebsite.setText("");
        edtEmail.setText("");
        edtFax.setText("");
        edtCompany.setText("");
        edtAddress.setText("");
    }

    /**
     * Display image from gallery
     */
    private void previewCapturedImage() {
        try {
//            imageView.setVisibility(View.VISIBLE);

            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            imgFrontPreview.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alert dialog to navigate to app settings
     * to enable necessary permissions
     */
    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(getApplicationContext());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void textRecognizer(Bitmap selectedImage){
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(getApplicationContext(),"could not get the Text",Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(selectedImage).build();

            SparseArray<TextBlock> sparseArray =textRecognizer.detect(frame);
            stringBuilder = new StringBuilder();

            for(int i = 0; i<sparseArray.size();i++){
                TextBlock textBlock = sparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            processImage();

//            output.setText(stringBuilder.toString());
        }
    }

    //    public static void saveImage(Bitmap bitmap) {
//        OutputStream output;
//        String recentImageInCache;
//        File filepath = Environment.getExternalStorageDirectory();
//
//        // Create a new folder in SD Card
//        File dir = new File(filepath.getAbsolutePath()
//                + "/YOUR_APP/profile");
//        dir.mkdirs();
//
//        // Create a name for the saved image
//        File file = new File(dir, username+".jpg");
//        try {
//
//            output = new FileOutputStream(file);
//
//            // Compress into png format image from 0% - 100%
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
//            output.flush();
//            output.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }

}
