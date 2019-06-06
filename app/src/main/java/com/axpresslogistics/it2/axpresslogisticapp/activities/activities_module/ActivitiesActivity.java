package com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitiesActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView tv;
    @BindView(R.id.img_back)
    ImageView iv;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tv.setText("Activities");
        iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_arrow_back));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");

    }
    private void check(String call) {
        if (list.contains(call)) {

            if (call.equals("to do list")) {
                startActivity(new Intent(ActivitiesActivity.this,
                        ToDoList.class));
            }


        }
        else{
            Toast.makeText(getApplicationContext(), "No Access", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.to_do_list})
    public void proceed(View view) {
        switch (view.getId()) {
            case R.id.to_do_list:
                check("to do list");
                break;
        }
    }


    @Override
    public void showSuccess(Object object) {

    }

    @Override
    public void showFailure(String error) {

    }
}