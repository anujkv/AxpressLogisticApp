package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.HRMSAdaptor;
import com.example.it2.axpresslogisticapp.adaptor.OperationAdaptor;
import com.example.it2.axpresslogisticapp.model.HRMSModel;
import com.example.it2.axpresslogisticapp.model.OperationModel;

import java.util.ArrayList;
import java.util.List;

public class HrmsActivity extends AppCompatActivity {

    public static String[] gridViewStrings = {
            "Mark Attendance",
            "Leave Info.",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrms);
        gridView = findViewById(R.id.grid);
        gridView = findViewById(R.id.grid);


        GridViewHrms gridViewHrms  = new GridViewHrms(HrmsActivity.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Sending image id to FullScreenActivity
                switch (position) {

                    case 0:
                        Intent intent_opt = new Intent(HrmsActivity.this, OperationActivity.class);
                        // passing array index
                        intent_opt.putExtra("id", position);
                        startActivity(intent_opt);
                        break;
                    case 1:
                        Intent intent_hrms = new Intent(HrmsActivity.this, OperationActivity.class);
                        // passing array index
                        intent_hrms.putExtra("id", position);
                        startActivity(intent_hrms);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "No Activity " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "No Activity " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        });
    }
}