package com.axpresslogistic.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.axpresslogistic.it2.axpresslogisticapp.R;
import com.axpresslogistic.it2.axpresslogisticapp.Utilities.CONSTANT;

import org.json.JSONException;
import org.json.JSONObject;

public class HrmsActivity extends AppCompatActivity {

    public static String[] gridViewStrings = {
            "Mark Attendance",
            "Apply Leave",
            "Attendence Summary"
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_mark_attendance,
            R.drawable.icon_mark,
            R.drawable.icon_activities
    };
    GridView gridView;
    Toolbar toolbar;
    String jsonString;
    JSONObject jObj;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrms);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.HRMS);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        gridView = findViewById(R.id.gridhrms);

        GridViewHrms gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewStrings, gridViewIcons);
        gridView.getColumnWidth();
        gridView.getVerticalSpacing();
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(HrmsActivity.this, MarkAttendanceActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(HrmsActivity.this, ApplyLeaveActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HrmsActivity.this, AttendanceSummaryActivity.class));
                        break;
                }
            }
        });
    }
}