package com.example.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class MarkAttendanceActivity extends AppCompatActivity {
    private String url = "http://webapi.axpresslogistics.com/api/HRMS/Attendance";
    static final int REQUEST_LOCATION = 1;
    public static TextView txtUsername, txtUserId, txtDateTime, txtDept, txtBranch, txtDesignation;
    String strUsername, strUserId, strDateTime, strDept, strBranch, strDesignation, formattedDate;
    ImageView userImage;
    Button attendance_btn;
    Location location;
    LocationManager locationManager;
    String jsonString;
    JSONObject jObj;
    Intent intent;
    Boolean FLAG = true;
    ProgressBar progressBar;

    //Longitude and latitude Information...
    double company_lat = 28.4995993;
    double company_long = 77.0738609;
    double nearbycompany_min_lat = 28.4991650;
    double nearbycompany_max_lat = 28.4997600;
    double nearbycompany_min_long = 77.0753330;
    double nearbycompany_max_long = 77.0739430;

    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        set_and_bind();

        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);
            getValuesFromAPI();
            setValuesInFields();
            date_time();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        if (FLAG.equals(true)) {
            attendance_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    markAttendance();
                    FLAG = false;
                }
            });
        }
    }

    private void date_time() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
    }

    private void setValuesInFields() {
        txtUsername.setText(strUsername.trim());
        txtUserId.setText(strUserId.trim());
        txtBranch.setText(strBranch.trim());
        txtDesignation.setText(strDesignation.trim());
        attendance_btn.setText("Submit Attendance");
        txtDept.setText(strDept.trim());
    }

    private void markAttendance() {
        progressBar.setVisibility(View.VISIBLE);

        if ((nearbycompany_max_lat >= lat && lat >= nearbycompany_min_lat) || (nearbycompany_max_long >= lon && lon >= nearbycompany_min_long)) {
            final String method = "Attendance";
            final String apikey = saltStr();
            Log.d("apikey : ", apikey);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        String status = object.optString("response");
                        String apiKEYresponse = object.optString("key");

                        if (status.equals("Marked") && apikey.equals(apiKEYresponse)) {
                            Toast.makeText(getApplicationContext(), "Attendance Marked!", LENGTH_SHORT).show();
                        } else if (status.equals("Already Marked")) {
                            Toast.makeText(getApplicationContext(), "Attendance Already Marked!", LENGTH_SHORT).show();
                        } else if (status.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Something went wrong, Kindly contact HR Dept.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("employee_id", jObj.optString("Emplid"));
                    params.put("date_time", formattedDate);
                    params.put("method", method);
                    params.put("key", apikey.trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            disable_button();

        } else {
            String lat_long = "Latitude = " + lat + " Longitude = " + lon;
            Toast.makeText(getApplicationContext(), lat_long, Toast.LENGTH_SHORT).show();
        }
//        String lat_long = "LOGLatitude = " + lat + " LOGLongitude = " + lon;
//        Toast.makeText(getApplicationContext(), lat_long, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void disable_button() {
        attendance_btn.setTextColor(getColor(R.color.white));
        attendance_btn.setClickable(false);
    }

    private String saltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void getValuesFromAPI() {
        strUsername = jObj.optString("Employee_Name");
        strUserId = jObj.optString("Emplid");
        strDesignation = jObj.optString("Employee_Designation");
        strDept = jObj.optString("Employee_Department");
        strBranch = jObj.optString("Employee_Branch");
    }

    private void set_and_bind() {
        txtUsername = findViewById(R.id.user_nameId);
        txtUserId = findViewById(R.id.user_id);
        txtDesignation = findViewById(R.id.txtDesignationId);
        txtBranch = findViewById(R.id.txtBranchId);
        txtDept = findViewById(R.id.txtDeptID);
        userImage = findViewById(R.id.user_imageId);
        attendance_btn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.attendance_progressbarId);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            try {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    Toast.makeText(getApplicationContext(),"GPS "+lat+" " +lon,LENGTH_SHORT).show();
                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
//                        Toast.makeText(getApplicationContext(),"NETWORK "+lat+" " +lon,LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
