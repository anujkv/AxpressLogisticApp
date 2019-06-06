package com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.GridViewHrms;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HrmsActivity extends AppCompatActivity {
//    String ATTENDANCE_PERMISSION_LIST_URL = URL +"HRMS/Attendance_Permission";
    String ATTENDANCE_PERMISSION_LIST_URL = "https://api.myjson.com/bins/byhka";
    List<String> permission_list = new ArrayList<String>();
    public static String[] gridViewStrings = {
            "Mark Attendance",
            "Apply Leave",
            "Attendence Summary",
//            "Upload Documents"
    };
    public static int[] gridViewIcons = {
            R.drawable.ic_location_on_red_24dp,
            R.drawable.ic_applyleave_red_24dp,
            R.drawable.ic_attendance_red_24dp,
//            R.drawable.ic_cloud_upload_red_24dp
    };

    public static String[] gridViewMgrStrings = {
            "Mark Attendance",
            "Apply Leave",
            "Attendance Summary",
            "Leave Approval",
//            "Upload Documents"
    };
    public static int[] gridViewMgrIcons = {
            R.drawable.mark_attendance,
            R.drawable.leave_apply,
            R.drawable.attendance_summery,
            R.drawable.leave_approval,
//            R.drawable.ic_cloud_upload_red_24dp
    };
    GridView gridView;
    Toolbar toolbar;
    String dept;
    JSONObject jObj;
    Intent intent;
    GridViewHrms gridViewHrms;
    ArrayList<String> list = new ArrayList<String>();
    Boolean connected = false;
    CoordinatorLayout coordinatorLayout;String emplid;

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
        dept = Preferences.getPreference(getApplicationContext(),CONSTANT.EMPLOYEE_DEPT);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");
        if(list.contains("leave approval")){
            Log.e("emplid : ",Preferences.getPreference(HrmsActivity.this,CONSTANT.EMPID));
            gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewMgrStrings, gridViewMgrIcons);
        }else{
            gridViewHrms = new GridViewHrms(HrmsActivity.this, gridViewStrings, gridViewIcons);
        }
        emplid = Preferences.getPreference(HrmsActivity.this,CONSTANT.EMPID);
        getPermissionList();
        gridView.getColumnWidth();
        gridView.getVerticalSpacing();
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {

                    case 0:
                        check("mark attendance");
                        break;
                    case 1:
                        check("apply leave");
                        break;
                    case 2:
                        check("attendance summary");
                        break;
                    case 3:
                        check("leave approval");
                        break;
                }
            }
        });
    }

    private void check(String call) {
        if (list.contains(call)) {

            if (call.equals("mark attendance")) {
                startActivity(new Intent(getApplicationContext(), MarkAttendanceActivity.class));

            } else if (call.equals("apply leave")) {
                startActivity(new Intent(getApplicationContext(), ApplyLeaveActivity.class));

            } else if (call.equals("attendance summary")) {
                startActivity(new Intent(getApplicationContext(), AttendanceSummaryActivity.class));

            } else if (call.equals("leave approval")) {
                startActivity(new Intent(getApplicationContext(), EmployeeLeaveRequestActivity.class));

            }
        }
        else{
            Toast.makeText(getApplicationContext(),CONSTANT.NO_ACCESS,Toast.LENGTH_SHORT).show();
        }
    }

    private void getPermissionList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ATTENDANCE_PERMISSION_LIST_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String method = jsonObject.getString("method");
                            if(method.equals("attendance_permission_list")){
                                JSONArray jsonArray = jsonObject.getJSONArray("permission_list");
                                if(jsonArray!=null){
                                    for(int i = 0; i<jsonArray.length();i++){
                                        permission_list.add(jsonArray.getString(i));
                                    }
                                }
                                Log.e("Lsit", Arrays.toString(new List[]{permission_list}));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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