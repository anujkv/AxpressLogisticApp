package com.example.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.CameraActivity;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.ScannedDataActivity;

import java.io.File;
import java.io.IOException;

public class CRMActivity extends AppCompatActivity {

    private static final int IMAGE_PERMISSION = 4 ;
    private static final int IMAGE_CAPTURE_REQUEST = 1001;


    private String mCurrentPhotoPath;


    private ProgressBar progressBar;

    private EditText obtainedText;
    private String phoneNumber;
    private String contactName;
    private String contactEmail;

    public static String[] gridViewStrings = {
            "Card Scan",
            "Card Scanner",
            "Saved Cards",
            "My Card Profile",
            "Card",
            "Visit Form"
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_card_scanner,
            R.drawable.icon_scanning,
            R.drawable.icon_qrcode,
            R.drawable.icon_qrcode,
            R.drawable.icon_card_scanner,
            R.drawable.icon_visit,
    };
    GridView gridView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("CRM");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        gridView = findViewById(R.id.gridhrms);

        GridViewHrms gridViewHrms = new GridViewHrms(CRMActivity.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
//                        startCameraActivityIntent();
                        Intent intent_docket = new Intent(CRMActivity.this,
                                CameraActivity.class);
                        // passing array index
//                        intent_docket.putExtra("id", position);
                        startActivity(intent_docket);
                        break;
                    case 1:
                        Intent intent_vea = new Intent(CRMActivity.this,
                                ScannerActivity.class);
                        // passing array index
                        intent_vea.putExtra("id", position);
                        startActivity(intent_vea);
                        break;
                    case 2:
                        Intent intent_addC = new Intent(CRMActivity.this,
                                CustomerVisitFormActivity.class);
                        // passing array index
                        intent_addC.putExtra("id", position);
                        startActivity(intent_addC);
                        break;
                    case 3:
                        Intent intent_qrcode = new Intent(CRMActivity.this,
                                ScannedDataActivity.class);
                        // passing array index
                        intent_qrcode.putExtra("id", position);
                        startActivity(intent_qrcode);
                        break;
                    case 4:
                        Intent intent_card = new Intent(CRMActivity.this,
                                CardActivity.class);
                        // passing array index
                        intent_card.putExtra("id", position);
                        startActivity(intent_card);
                        break;
                    case 5:
                        Intent intent_visit = new Intent(CRMActivity.this,
                                CustomerViewListActivity.class);
                        // passing array index
                        intent_visit.putExtra("id", position);
                        startActivity(intent_visit);
                        break;
                }
            }
        });
    }

    private void startCameraActivityIntent() {
            //Required camera permission
            String[] permissions = {"android.permission.CAMERA"};
            //Intent to startCamera
            Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                    .PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, permissions, IMAGE_PERMISSION);
            }
            else {
                if (startCameraIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = createImageFile();
                    if(photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.it2.axpresslogisticapp.acitvities.FileProvider",
                                photoFile);

                        //For non bitmap full sized images use EXTRA_OUTPUT during Intent
                        startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(startCameraIntent, IMAGE_CAPTURE_REQUEST);
                    }
                }
            }
        }

    private File createImageFile(){
        //Create image filename
        String imageFileName = "JPEG_00";

        //Access storage directory for photos and create temporary image file
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Store file path for usage with intents
        assert image != null;
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    private String convertImageToBase64EncodedString() {
//        File f = new File(mCurrentPhotoPath);
//        String base64EncodedString;
//
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        byte[] buffer = new byte[8192];
//        int bytesRead;
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        try{
//            assert inputStream != null;
//            while((bytesRead = inputStream.read(buffer)) != -1){
//                output.write(buffer, 0, bytesRead);
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//
//        byte[] bytes = output.toByteArray();
//        base64EncodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
//        return base64EncodedString;
//    }

//    private void deleteCapturedImage() {
//        File fileToBeDeleted = new File(mCurrentPhotoPath);
//        if(fileToBeDeleted.exists()){
//            if(fileToBeDeleted.delete()){
//                Log.w("MainActivity", "File Deleted: " + mCurrentPhotoPath);
//            } else {
//                Log.w("MainActivity", "File Not Deleted " + mCurrentPhotoPath);
//            }
//        }
//    }

//    private ArrayList<String> parseResults(String bCardText) {
//        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//        Iterable<PhoneNumberMatch> numberMatches = phoneNumberUtil.findNumbers(bCardText, Locale.US.getCountry());
//        ArrayList<String> data = new ArrayList<>();
//        for(PhoneNumberMatch number : numberMatches){
//            String s = number.rawString();
//            data.add(s);
//        }
//        return data;
//    }
}
