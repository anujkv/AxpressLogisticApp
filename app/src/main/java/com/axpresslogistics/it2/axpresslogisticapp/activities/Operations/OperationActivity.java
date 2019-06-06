package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module.DBDialog;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.GridViewHrms;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.DocketEnquiry.DocketEnquiry;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.PODScan.PODScanner;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleTrackingMap.VehicletrackingMap;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;

import java.util.ArrayList;


public class OperationActivity extends AppCompatActivity {
    public static String[] gridViewStrings = {
            CONSTANT.DOCKET_INVOICE,
            CONSTANT.ADD_BROKER,
            CONSTANT.ADD_VEHICLE_REQ,
            CONSTANT.VEHICLE_APPROVAL,
            CONSTANT.VEHICLE_TRACKING_MAP,
            CONSTANT.CHALLAN_RECIVER,
            CONSTANT.VENDOR_APPROVAL,
            CONSTANT.POD_SCAN
    };
    public static int[] gridViewIcons = {
            R.drawable.docket_invoice_enquiry,
            R.drawable.add_broker,
            R.drawable.add_vehicle,
            R.drawable.vehicle_approval,
            R.drawable.vehicle_tracking,
            R.drawable.challan_recevied,
            R.drawable.vender_approvel,
            R.drawable.pod_scan
    };
    GridView gridView;
    Toolbar toolbar;
    ArrayList<String> list = new ArrayList<String>();
    Boolean connected = false;
    CoordinatorLayout coordinatorLayout;
    String db_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        coordinatorLayout = findViewById(R.id.android_coordinator_layoutId);
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
        checkNetworkconnection();
        gridView = findViewById(R.id.gridhrms);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");
//        list.add("vendor approval");

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
                    case 5:
                        check("challan receive");
//                        startActivity(new Intent(getApplicationContext(), ChallanReciver.class));
                        break;
                    case 6:
                        check("vendor approval");
//                        startActivity(new Intent(getApplicationContext(), VendorListActivity.class));
                        break;
                    case 7:
//                        check("pod scan");
//                        startActivity(new Intent(getApplicationContext(), DBDialog.class));
                        Intent intentPS = new Intent(getApplicationContext(), DBDialog.class);
                        intentPS.putExtra("activity_name", "PODScan");
                        startActivity(intentPS);
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
                Intent intent = new Intent(getApplicationContext(), DBDialog.class);
                intent.putExtra("activity_name", "BrokerList");
                startActivity(intent);

            } else if (call.equals("add vehicle request")) {
                Intent intent = new Intent(getApplicationContext(), DBDialog.class);
                intent.putExtra("activity_name", "AddVehicleReq");
                startActivity(intent);

            } else if (call.equals("vehicle approval")) {

                Intent intent = new Intent(getApplicationContext(), DBDialog.class);
                intent.putExtra("activity_name", "MarketVehicleRequest");
                startActivity(intent);

            } else if (call.equals("vehicle tracking map")) {
                startActivity(new Intent(getApplicationContext(), VehicletrackingMap.class));

            } else if (call.equals("challan receive")) {

                Intent intent = new Intent(getApplicationContext(), DBDialog.class);
                intent.putExtra("activity_name", "ChallanReciver");
                startActivity(intent);
            } else if (call.equals("vendor approval")) {
                Intent intent = new Intent(getApplicationContext(), DBDialog.class);
                intent.putExtra("activity_name", "VendorApproval");
                startActivity(intent);
            }else if (call.equals("pod scan")) {

                Intent intent = new Intent(getApplicationContext(), PODScanner.class);
                intent.putExtra("activity_name", "PODScan");
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), CONSTANT.NO_ACCESS, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkNetworkconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

        } else {
            connected = false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetworkconnection();
                        }
                    });

            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        return connected;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkNetworkconnection();
    }
}