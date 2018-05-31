package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

public class MarkAttendanceActivity extends AppCompatActivity {

    TextView username,userid,date_time;
    ImageView userImage;
    Button attendance_btn;
    Location location;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        username = findViewById(R.id.user_nameId);
        userid = findViewById(R.id.user_id);
        date_time = findViewById(R.id.current_date_id);
        userImage = findViewById(R.id.user_imageId);

        attendance_btn = findViewById(R.id.attendance_btnId);

        attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Attendance Submitted Successfully",Toast.LENGTH_SHORT).show();
                attendance_btn.setClickable(false);
                attendance_btn.setText("Attendance Submitted");
                attendance_btn.setBackgroundColor(R.drawable.grayshade);
            }
        });



        }
}
