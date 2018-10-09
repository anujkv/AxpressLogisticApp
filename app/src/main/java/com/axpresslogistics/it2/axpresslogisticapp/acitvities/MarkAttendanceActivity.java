package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.DEVELOPMENT_URL;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class MarkAttendanceActivity extends AppCompatActivity implements LocationListener {
    static final int REQUEST_LOCATION = 1;
    public static TextView txtUsername, txtUserId, txtDateTime, txtDept, txtBranch, txtDesignation;
    String strUsername, strUserId, strDateTime, strDept, strBranch, strDesignation, formattedDate;
    ImageView userImage;
    Button attendance_btn;
    Location location;
    LocationManager locationManager;
    Intent intent;
    Boolean FLAG = true;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    String locationPref = CONSTANT.INFO_NO,add;
    byte[] image = null;
    //Longitude and latitude Information...\
    double lat= 0.0, lon =0.0,latitude,longitude;
    private String MARK_URl = DEVELOPMENT_URL + "HRMS/Attendance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        //custom toolbar...
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.MARK_ATTENDANCE);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                    5, this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        init();
        getValuesFromPref();
        setValuesInFields();
        getLocationPermissionCheck();
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

    private void markAttendance() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(CONSTANT.GETTING_LOCATION);
        disable_button();
        if(lat != 0.0 && lon!= 0.0) {
            pushAttendance();
        }
        else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(latitude != 0.0 && longitude!= 0.0) {
                        pushAttendance();
                    }else{
                        Toast.makeText(getApplicationContext(), CONSTANT.LOCATION_NOT_GETTING, Toast.LENGTH_SHORT).show();
                        enable_button();
                    }
                }
            }, 10000);
        }
    }

    private void pushAttendance() {
        ApiKey apiKey = new ApiKey();
        final String method = "attendance";
        final String apikey = apiKey.saltStr();
        Log.d("apikey : ", apikey);
        date_time();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MARK_URl, new Response.
                Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("MarkREsponse",response);
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("response");
                    String apiKEYresponse = object.optString("key");

                    if (status.equals("Marked") && apikey.equals(apiKEYresponse)) {
                        Toast.makeText(getApplicationContext(), "Attendance Marked!",
                                LENGTH_SHORT).show();
                        enable_button();
                    } else if (status.equals("Already Marked")) {
                        Toast.makeText(getApplicationContext(), "Attendance Already Marked!",
                                LENGTH_SHORT).show();
                        enable_button();
                    } else if (status.equals("failed")) {
                        Toast.makeText(getApplicationContext(), "Something went wrong, " +
                                "Kindly contact HR Dept.", Toast.LENGTH_LONG).show();
                        enable_button();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    enable_button();
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
                enable_button();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("date_time", formattedDate);
                params.put("longitude", String.valueOf(lat));
                params.put("latitude", String.valueOf(lon));
                params.put("location", String.valueOf(add));

                Log.e("date_time", formattedDate);
                Log.e("longitude", String.valueOf(lon));
                Log.e("latitude", String.valueOf(lat));
                Log.e("location", String.valueOf(location));
                Log.e("employee_id", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void init() {
        txtUsername = findViewById(R.id.user_nameId);
        txtUserId = findViewById(R.id.user_id);
        txtDesignation = findViewById(R.id.txtDesignationId);
        txtBranch = findViewById(R.id.txtBranchId);
        txtDept = findViewById(R.id.txtDeptID);
        userImage = findViewById(R.id.user_imageId);
        attendance_btn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.attendance_progressbarId);

    }

    private void getValuesFromPref() {
        strUsername = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_NAME);
        strUserId = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        strDesignation = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_DESIGNATION.trim());
        strDept = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_DEPT.trim());
        strBranch = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_BRANCH.trim());
        String image_profile = Preferences.getPreference(getApplicationContext(), CONSTANT.USER_IMAGE.trim());
        Log.e("URL : ", image_profile);
        Log.e("strUsername : ", strUsername);
        Log.e("strUserId : ", strUserId);
        Log.e("strDesignation : ", strDesignation);
        Log.e("strDept : ", strDept);
        Log.e("strBranch : ", strBranch);
        Picasso.get().load(image_profile).memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE).into(userImage);
    }

    private void setValuesInFields() {
        txtUsername.setText(strUsername.trim());
        txtUserId.setText(strUserId.trim());
        txtBranch.setText(strBranch.trim());
        txtDesignation.setText(strDesignation.trim());
        txtDept.setText(strDept.trim());
        attendance_btn.setText("Submit Attendance");
    }

    private void date_time() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }

    private void getLocationPermissionCheck() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(getApplicationContext(), Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.
                            ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        String latLong = location.toString();
        Log.e("lat", String.valueOf(lat));
        lat = location.getLatitude();
        lon = location.getLongitude();
        Log.e("lat,long",latLong);
        Log.e("lat", String.valueOf(lat));
        Log.e("ong", String.valueOf(lon));
        getAddress(lat,lon);

    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MarkAttendanceActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address obj = addresses.get(0);
        add = obj.getAddressLine(0);
        String  currentAddress = obj.getSubAdminArea() + ","
                + obj.getAdminArea();
        latitude = obj.getLatitude();
        longitude = obj.getLongitude();
        String currentCity= obj.getSubAdminArea();
        String currentState= obj.getAdminArea();


        Log.e("currentAddress",obj.getSubAdminArea());
        Log.e("obj.getCountryCode()",obj.getCountryCode());
        Log.e("obj.getAdminArea()",obj.getAdminArea());
        Log.e("obj.getPostalCode()",obj.getPostalCode());
        Log.e("obj.getSubAdminArea()",obj.getSubAdminArea());
        Log.e("obj.getLocality()",obj.getLocality());


        Log.e("Address" , add);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MarkAttendanceActivity.this, "Please Enable GPS and Internet",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void disable_button() {
        attendance_btn.setTextColor(getColor(R.color.white));
        progressDialog.show();
        attendance_btn.setClickable(false);
    }

    private void enable_button() {
        attendance_btn.setTextColor(getColor(R.color.black));
        progressDialog.dismiss();
        attendance_btn.setClickable(true);
    }

}
