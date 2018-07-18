package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HrmsActivity extends AppCompatActivity {

    public static String[] gridViewStrings = {
            "Mark Attendance",
            "Leave Information",
            "Apply Leave",
            "Attendence Summary",
            "Pay Slip",
            "Document Scanning",
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_mark_attendance,
            R.drawable.icon_mark,
            R.drawable.icon_crm,
            R.drawable.icon_activities,
            R.drawable.icon_financial,
            R.drawable.icon_scanning,
    };
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    GridView gridView;
    Toolbar toolbar;
    Context context;
    ArrayList arrayList;
    String jsonString;
    JSONObject jObj;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrms);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("HRMS");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        gridView = findViewById(R.id.gridhrms);

        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        GridViewHrms gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewStrings, gridViewIcons);
        gridView.getColumnWidth();
        gridView.getVerticalSpacing();
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        Intent intent_mark = new Intent(HrmsActivity.this,MarkAttendanceActivity.class);
                        intent_mark.putExtra("response", jObj.toString());
                        startActivity(intent_mark);
                        break;
                    case 1:
                        Intent intent_hrms = new Intent(HrmsActivity.this, LeaveInfoActivity.class);
                        // passing array index
                        intent_hrms.putExtra("response", jObj.toString());
                        startActivity(intent_hrms);
                        break;
                    case 2:
                        Intent intent_leaveApply = new Intent(HrmsActivity.this, ApplyLeaveActivity.class);
                        // passing array index
                        intent_leaveApply.putExtra("response", jObj.toString());
                        startActivity(intent_leaveApply);
                        break;
                    case 3:
                        startActivity(new Intent(HrmsActivity.this,AttendanceSummaryActivity.class));
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Document Scanning " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }
}