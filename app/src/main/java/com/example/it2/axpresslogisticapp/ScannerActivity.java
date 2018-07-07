package com.example.it2.axpresslogisticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.acitvities.CardActivity;
import com.example.it2.axpresslogisticapp.acitvities.SavedCardActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton camerabtn, qrcodebtn, saved_cardbtn, mycardbtn;
    TextView textViewName, textViewAddress;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Card Scanner");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        camerabtn = findViewById(R.id.camerabtn);
        qrcodebtn = findViewById(R.id.qrcodebtn);
        saved_cardbtn = findViewById(R.id.saved_cardbtn);
        mycardbtn = findViewById(R.id.my_cardbtn);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        camerabtn.setOnClickListener(this);
        qrcodebtn.setOnClickListener(this);
        saved_cardbtn.setOnClickListener(this);
        mycardbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camerabtn:
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
                break;
            case R.id.qrcodebtn:
                qrScan.initiateScan();
                break;
            case R.id.saved_cardbtn:
                startActivity(new Intent(getApplicationContext(),SavedCardActivity.class));
                break;
            case R.id.my_cardbtn:
                startActivity(new Intent(getApplicationContext(),CardActivity.class));
                break;
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewName.setText(obj.getString("name"));
                    textViewAddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

