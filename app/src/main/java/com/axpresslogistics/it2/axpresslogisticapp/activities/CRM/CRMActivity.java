package com.axpresslogistics.it2.axpresslogisticapp.activities.CRM;

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
import com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.BusinessCard.BusinessCardList;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.GridViewHrms;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;

import java.util.ArrayList;

public class CRMActivity extends AppCompatActivity {

    public static String[] gridViewStrings = {
            "Visit Form",
            "Business Card"
    };
    public static int[] gridViewIcons = {
            R.drawable.visit_form,
            R.drawable.business_card
    };
    Intent intent;
    GridView gridView;
    Toolbar toolbar;
    ArrayList<String> list = new ArrayList<String>();
    Boolean connected = false;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);
        coordinatorLayout = findViewById(R.id.android_coordinator_layoutId);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("CRM");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkNetworkconnection();
        gridView = findViewById(R.id.gridhrms);
        GridViewHrms gridViewHrms = new GridViewHrms(CRMActivity.this, gridViewStrings, gridViewIcons);
        gridView.setAdapter(gridViewHrms);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {
                    case 0:
                        check("visit form");
                        break;
                    case 1:
                        check("business card");
//                        startActivity(new Intent(CRMActivity.this,
//                                BusinessCard.class));
                        break;
                }
            }
        });
    }

    private void check(String call) {
        if (list.contains(call)) {

            if (call.equals("visit form")) {
                Intent intent = new Intent(getApplicationContext(),DBDialog.class);
                intent.putExtra("activity_name","CustomerViewListActivity");
                startActivity(intent);
//                startActivity(new Intent(CRMActivity.this,
//                        CustomerViewListActivity.class));
            }
            else if (call.equals("business card")) {
                startActivity(new Intent(CRMActivity.this,
                        BusinessCardList.class));
            }
        }
        else{
            Toast.makeText(getApplicationContext(),CONSTANT.NO_ACCESS,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkNetworkconnection() {
        Snackbar snackbar = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetworkconnection();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
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
