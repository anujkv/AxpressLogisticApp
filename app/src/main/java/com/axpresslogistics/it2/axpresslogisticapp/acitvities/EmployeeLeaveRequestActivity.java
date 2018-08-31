package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;

public class EmployeeLeaveRequestActivity extends AppCompatActivity {
    String APPROVAL_LEAVE_SHOW = CONSTANT.DEVELOPMENT_URL + "HRMS/approve_leave_show";
    String APPROVAL_LEAVE_UPDATE = CONSTANT.DEVELOPMENT_URL + "HRMS/approve_leave_update";
    TableLayout tableview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_leave_request);
        tableview = findViewById(R.id.tableview);
    }
}
