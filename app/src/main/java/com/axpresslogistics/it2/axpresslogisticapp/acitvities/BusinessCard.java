package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BusinessCard extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int GALLERY_REQUEST = 1;
    private static final int QRCODE_REQUEST = 2;
    ImageButton backbtn_toolbar, refreshbtn_toolbar;
    ImageView imageView_camera, imageView_gallery, imageview_qrcode;
    android.app.AlertDialog.Builder builder;
    android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.PROFILE);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        refreshbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        refreshbtn_toolbar.setOnClickListener(this);
        refreshbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });
    }

    private void showDialogBox() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.card_options, null);
        imageView_camera = alertLayout.findViewById(R.id.imageview_camera);
        imageView_gallery = alertLayout.findViewById(R.id.imageview_gallery);
        imageview_qrcode = alertLayout.findViewById(R.id.imageview_qrcode);


        builder = new android.app.AlertDialog.Builder(alertLayout.getContext());
        builder.setTitle("CHOOSE IMAGE FROM");
        builder.setView(alertLayout);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
            }
        });

//        checkEmptyFields();
        dialog = builder.create();
        dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);


//----------------------------------CAMERA----------------------------------------------------------
        imageView_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add camera functionality...
                Toast.makeText(getApplicationContext(), "camera", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusinessCard.this,BusinessCardView.class);
                intent.putExtra("key",CAMERA_REQUEST);
                startActivity(intent);
            }
        });
//----------------------------------GALLERY---------------------------------------------------------
        imageView_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessCard.this,BusinessCardView.class);
                intent.putExtra("key",GALLERY_REQUEST);
                startActivity(intent);
            }
        });
//----------------------------------QR CODE---------------------------------------------------------
        imageview_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add gallery functionality...
                Toast.makeText(getApplicationContext(), "qrcode", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusinessCard.this,BusinessCardView.class);
                intent.putExtra("key",QRCODE_REQUEST);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                refresh();
                break;
        }
    }

    private void refresh() {
        //TODO add refresh list here
        Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_SHORT).show();
    }
}