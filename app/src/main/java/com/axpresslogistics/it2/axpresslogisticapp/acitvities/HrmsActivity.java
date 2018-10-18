package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static String[] gridViewMgrStrings = {
            "Mark Attendance",
            "Apply Leave",
            "Attendence Summary",
            "Employee Leave Request"
    };
    public static int[] gridViewMgrIcons = {
            R.drawable.icon_mark_attendance,
            R.drawable.icon_mark,
            R.drawable.icon_activities,
            R.drawable.icon_tickets
    };
    GridView gridView;
    Toolbar toolbar;
    String jsonString;
    JSONObject jObj;
    Intent intent;
    GridViewHrms gridViewHrms;
    ArrayList<String> list = new ArrayList<String>();
    Boolean connected = false;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrms);
        coordinatorLayout = findViewById(R.id.android_coordinator_layoutId);
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
        checkNetworkconnection();
        gridView = findViewById(R.id.gridhrms);
        String id = Preferences.getPreference(HrmsActivity.this,CONSTANT.EMPID);
        String supervisior_id = Preferences.getPreference(HrmsActivity.this,CONSTANT.SUPERVISER_ID);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");
        if(list.contains("employee leave request")){
            Log.e("emplid : ",Preferences.getPreference(HrmsActivity.this,CONSTANT.EMPID));
            gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewMgrStrings, gridViewMgrIcons);
        }else{
            gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewStrings, gridViewIcons);
        }
        gridView.getColumnWidth();
        gridView.getVerticalSpacing();
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {
                    case 0:
                        if(CONSTANT.EMPLOYEE_BRANCH.toUpperCase().equals("CR") ||
                                CONSTANT.EMPLOYEE_DEPT.toUpperCase().equals("MK")){
                            startActivity(new Intent(HrmsActivity.this, MarkAttendanceActivity.class));

                        }else{
                            Toast.makeText(getApplicationContext(),CONSTANT.CONDITIONS_MARK_ATTENDANCE,
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        startActivity(new Intent(HrmsActivity.this, ApplyLeaveActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HrmsActivity.this, AttendanceSummaryActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HrmsActivity.this, EmployeeLeaveRequestActivity.class));
                        break;
                }
            }
        });
    }

    private boolean checkNetworkconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

        } else{
            connected = false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetworkconnection();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            snackbar.show();
        }
        return connected;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkNetworkconnection();
    }
}