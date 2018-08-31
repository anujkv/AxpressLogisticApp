package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;

import org.json.JSONObject;

public class CRMActivity extends AppCompatActivity {

    private String jsonString;
    JSONObject jObj;
    Intent intent;

    public static String[] gridViewStrings = {
            "Visit Form",
            "Business Card"
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_visit,
            R.drawable.icon_business_card
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

                switch (position) {
                    case 0:
                        startActivity(new Intent(CRMActivity.this,
                                CustomerViewListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(CRMActivity.this,
                                BusinessCard.class));
                        break;
                }
            }
        });
    }
}
