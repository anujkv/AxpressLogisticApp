package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.HRMSAdaptor;
import com.example.it2.axpresslogisticapp.adaptor.OperationAdaptor;
import com.example.it2.axpresslogisticapp.model.HRMSModel;
import com.example.it2.axpresslogisticapp.model.OperationModel;

import java.util.ArrayList;
import java.util.List;

public class HrmsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<HRMSModel> hrmsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrms);
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        //  Toast.makeText(OperationActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.hrms_recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hrmsModels = new ArrayList<>();
        dataload();
    }

    private void dataload() {
        HRMSModel opt = new HRMSModel("Attendance Record","R.mipmap.icon_hrms");
        hrmsModels.add(opt);
        setOptadapter(hrmsModels);
    }

    private void setOptadapter(List<HRMSModel> hrmsModels) {
        HRMSAdaptor myAdapter = new HRMSAdaptor(this, hrmsModels) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
}
