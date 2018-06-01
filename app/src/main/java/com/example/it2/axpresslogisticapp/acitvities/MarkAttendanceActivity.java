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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

public class MarkAttendanceActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;

    TextView username,userid,date_time;
    ImageView userImage;
    Button attendance_btn;
    Location location;
    LocationManager locationManager;

    double company_lat = 28.4996670;
    double company_long = 77.0739650;
    double nearbycompany_min_lat = 28.4995000;
    double nearbycompany_max_lat = 28.4997500;
    double nearbycompany_min_long = 28.4996670;
    double nearbycompany_max_long = 77.0741000;

    double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        username = findViewById(R.id.user_nameId);
        userid = findViewById(R.id.user_id);
        date_time = findViewById(R.id.current_date_id);
        userImage = findViewById(R.id.user_imageId);

        attendance_btn = findViewById(R.id.attendance_btnId);

        attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((nearbycompany_max_lat >= lat && lat >= nearbycompany_min_lat) || (nearbycompany_max_long >= lon && lon >= nearbycompany_min_long)) {
                    Toast.makeText(getApplicationContext(),"Attendance Submitted Successfully",Toast.LENGTH_SHORT).show();
                    attendance_btn.setClickable(false);
                    attendance_btn.setText("Attendance Submitted");
                    attendance_btn.setBackgroundColor(R.drawable.grayshade);
                } else {
                    String lat_long = "Latitude = "+ lat +" Longitude = "+ lon;
                    Toast.makeText(getApplicationContext(),lat_long,Toast.LENGTH_SHORT).show();
                }


            }
        });



        }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location != null) {
                lat = location.getLatitude();
                lon =  location.getLongitude();
            }
        }
    }
}
