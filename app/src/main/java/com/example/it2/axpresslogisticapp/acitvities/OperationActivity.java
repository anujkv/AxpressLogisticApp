package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.ScannedDataActivity;

import java.util.ArrayList;

public class OperationActivity extends AppCompatActivity {
    public static String[] gridViewStrings = {
            "Docket / Invoice Enquiry",
            "Vehicle Enquiry",
            "New Customer",
            "QR Code Scanner",
            "Document Scanning",
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_operation,
            R.drawable.icon_vehical,
            R.drawable.icon_add_customer,
            R.drawable.icon_qrcode,
            R.drawable.icon_scanning,
    };
    GridView gridView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Operation");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        gridView = findViewById(R.id.gridhrms);


        GridViewHrms gridViewHrms = new GridViewHrms(OperationActivity.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        Intent intent_docket = new Intent(OperationActivity.this,
                                DocketEnquiry.class);
                        // passing array index
                        intent_docket.putExtra("id", position);
                        startActivity(intent_docket);
                        break;
                    case 1:
                        Intent intent_vea = new Intent(OperationActivity.this,
                                VehicalEnquiryActivity.class);
                        // passing array index
                        intent_vea.putExtra("id", position);
                        startActivity(intent_vea);
                        break;
                    case 2:
                        Intent intent_addC = new Intent(OperationActivity.this,
                                AddCustomerActivity.class);
                        // passing array index
                        intent_addC.putExtra("id", position);
                        startActivity(intent_addC);
                        break;
                    case 3:
//                        Intent intent_qrcode = new Intent(OperationActivity.this,
//                                QRCodeScanningActivity.class);
                        Intent intent_qrcode = new Intent(OperationActivity.this,
                                ScannedDataActivity.class);
                        // passing array index
                        intent_qrcode.putExtra("id", position);
                        startActivity(intent_qrcode);
                        break;
                    case 4:
                        Intent intent_ds = new Intent(OperationActivity.this,
                                DocketEnquiry.class);
                        // passing array index
                        intent_ds.putExtra("id", position);
                        startActivity(intent_ds);
                        break;
                }
            }
        });
    }
}