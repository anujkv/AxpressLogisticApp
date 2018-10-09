package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;

import java.util.ArrayList;


public class OperationActivity extends AppCompatActivity {
    public static String[] gridViewStrings = {
            CONSTANT.DOCKET_INVOICE,
            CONSTANT.ADD_BROKER,
            CONSTANT.ADD_VEHICLE_REQ,
            CONSTANT.VEHICLE_APPROVAL,
            CONSTANT.VEHICLE_TRACKING_MAP,
    };
    public static int[] gridViewIcons = {
            R.drawable.icon_operation,
            R.drawable.icon_add_customer,
            R.drawable.icon_vehicale,
            R.drawable.icon_approval,
            R.drawable.icon_vehicale,
    };
    GridView gridView;
    Toolbar toolbar;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.OPERATION);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = findViewById(R.id.gridhrms);
        gridView = findViewById(R.id.gridhrms);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");



        GridViewHrms gridViewHrms = new GridViewHrms(OperationActivity.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {

                    case 0:
                        check("docket/invoice enquiry");
                        break;
                    case 1:
                        check("add broker");
                        break;
                    case 2:
                        check("add vehicle request");
                        break;
                    case 3:
                        check("vehicle approval");
                        break;
                    case 4:
                        check("vehicle tracking map");
                        break;
                }
            }
        });
    }

    private void check(String call) {
        if (list.contains(call)) {

            if (call.equals("docket/invoice enquiry")) {
                startActivity(new Intent(getApplicationContext(), DocketEnquiry.class));

            } else if (call.equals("add broker")) {
                startActivity(new Intent(getApplicationContext(), BrokerList.class));

            } else if (call.equals("add vehicle request")) {
                startActivity(new Intent(getApplicationContext(), AddVehicleReq.class));

            } else if (call.equals("vehicle approval")) {
                startActivity(new Intent(getApplicationContext(), MarketVehicleRequest.class));

            } else if (call.equals("vehicle tracking map")) {
                startActivity(new Intent(getApplicationContext(), VehicletrackingMap.class));
            }
        }
//        else {
//            Toast.makeText(getApplicationContext(), "Sorry, you don't have permission!,contact with IT Department.",
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}