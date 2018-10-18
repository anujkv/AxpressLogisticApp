package com.axpresslogistics.it2.axpresslogisticapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.BusinessCard;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.CRMActivity;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.GridViewHrms;

import java.util.ArrayList;

public class Activities extends AppCompatActivity {
    public static String[] gridViewStrings = {
            "To Do List",
            "Daily Sheet"
    };
    public static int[] gridViewIcons = {
            R.drawable.todolist_icon,
            R.drawable.icon_activities
    };
    GridView gridView;
    Toolbar toolbar;
    Boolean connected = false;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        coordinatorLayout = findViewById(R.id.android_coordinator_layoutId);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.ACTIVITIES);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        GridViewHrms gridViewHrms = new GridViewHrms(Activities.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(Activities.this,
                                ToDoList.class));
                        break;
                    case 1:
//                        startActivity(new Intent(Activities.this,
//                                BusinessCard.class));
                        break;
                }
            }
        });
    }
}
