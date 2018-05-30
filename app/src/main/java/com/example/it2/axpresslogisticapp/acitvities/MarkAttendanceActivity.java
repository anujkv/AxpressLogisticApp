package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

public class MarkAttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
    }
}
