package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ImageUtils;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.VolleySingleton;
import com.axpresslogistics.it2.axpresslogisticapp.VolleySupport.VolleyMultipartRequest;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SlideAdaptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class BusinessCardView extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdaptor slideAdaptor;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int GALLERY_REQUEST = 1;
    private static final int QRCODE_REQUEST = 2;

    Bitmap bitmap;
    byte[] image = null;
    boolean hasImage = false;
    String CARD_IMAGE_UPLOAD_URL = CONSTANT.DEVELOPMENT_URL + "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_view);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Business Card");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager =findViewById(R.id.pager);
        slideAdaptor = new SlideAdaptor(this);
        viewPager.setAdapter(slideAdaptor);

        Intent intent = getIntent();
        String id = intent.getStringExtra("key");

        if(id.equals(GALLERY_REQUEST)){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY_REQUEST);
        }else if(id.equals(CAMERA_REQUEST)) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }else if(id.equals(QRCODE_REQUEST)){
            Log.e("KEY REQUEST : ", String.valueOf(QRCODE_REQUEST));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == GALLERY_REQUEST) {
            if (imageReturnedIntent != null) {
                Uri uri = imageReturnedIntent.getData();
                String path = getRealPathFromURI(uri);
//                Preferences.setPreference(BusinessCard.this, CONSTANT.imagePath,path);
                Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(path);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                image = byteArrayOutputStream.toByteArray();
                Log.e("setimage", path);
//                userImageView.setImageBitmap(bitmap);
                imageFolder();
                hasImage = true;
                UpdateProfile(bitmap, path);
            }
        }
    }

    public Bitmap imageFolder() {
        Bitmap bitmapsave = bitmap;
        return bitmapsave;
    }

    void UpdateProfile(final Bitmap bitmap, final String path) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = CONSTANT.EMPLOYEE_METHOD;
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                CARD_IMAGE_UPLOAD_URL, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                String responseString = new String(response.data);
                Log.d("ERROR", responseString + "ERROR");
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(responseString);
                    //TODO response codeyour respone is on
                    Log.d("TEXT", responseString);
                    String status = object.optString(CONSTANT.STATUS);
                    if (status.equals(CONSTANT.TRUE)) {
                        Toast.makeText(getApplicationContext(), CONSTANT.IMAGE_UPLOADED_SUCCESSFULLY,
                                Toast.LENGTH_SHORT).show();
                        Preferences.setPreference(BusinessCardView.this, CONSTANT.imagePath, path);
//                        userImageView.setImageBitmap(bitmap);
                    }

                } catch (JSONException e) {
                    Log.e("JSONException", e.getMessage());
                    Toast.makeText(getApplicationContext(), CONSTANT.IMAGE_UPLOADED_UNSUCCESSFULL,
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", key);
                params.put("emplid", CONSTANT.EMPID);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (hasImage) {
                    params.put("profile_image", new DataPart("User_profile.jpg", image, "image/jpeg"));
                }
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(BusinessCardView.this).addToRequestQueue(multipartRequest);


    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = BusinessCardView.this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }



}