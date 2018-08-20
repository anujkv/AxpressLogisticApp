package com.axpresslogistics.it2.axpresslogisticapp.acitvities;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.DocketTrackingAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DocketTracking extends AppCompatActivity implements View.OnClickListener {
    TextView txtDocket_no, txtDocket_date, txtDocket_from, txtDocket_to, txtDocketConsignee,
            txtDocket_status, txtDRS_no, txtDRS_date, txtDRS_receiving, txtDRS_received_by, txtDRS_status;
    //TextView seprate for firstListofItems details...
    TextView txt_challenFirst_no_ID, txt_challenFirst_dateID, txt_challanFirst_fromID,
            txt_challanFirst_toID, txt_vehicalFirst_noID, txt_vehicalFirst_statusID, show_more_click_link;

    String strDocket_no, strDocket_date, strDocket_from, strDocket_to, strDocketConsignee,
            strDocket_status, strDRS_no, strDRS_date, strDRS_receiving, strDRS_received_by, strDRS_status;
    //TextView seprate for firstListofItems details...
    String str_challenFirst_no_ID, str_challenFirst_dateID, str_challanFirst_fromID,
            str_challanFirst_toID, str_vehicalFirst_noID, str_vehicalFirst_statusID;
    Button mapview_clickbtn;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.axpresslogistics.it2.axpresslogisticapp.model.DocketTracking> challanList;
    String jsonString;
    JSONObject jObj;
    Intent intent;
    int no_of_challan, no_of_drs;
    Boolean FLAG = false;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    CardView challanCardView;
    LinearLayout linearLayout;
    ImageButton backbtn_toolbar, mapbtn_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_tracking);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.DOCKET_TRACKING_DETAILS);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        mapbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        mapbtn_toolbar.setOnClickListener(this);
        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);
            declareVariable();
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void declareVariable() {
        linearLayout = findViewById(R.id.docketLinearLayoutId);
        txtDocket_no = findViewById(R.id.txt_docketID);
        txtDocketConsignee = findViewById(R.id.txt_consigneeID);
        txtDocket_date = findViewById(R.id.txt_docketDateID);
        txtDocket_from = findViewById(R.id.txt_fromID);
        txtDocket_to = findViewById(R.id.txt_toID);
        txtDocket_status = findViewById(R.id.txt_statusID);

        txtDRS_no = findViewById(R.id.txt_drs_challan_noID);
        txtDRS_date = findViewById(R.id.txt_drsDateID);
        txtDRS_receiving = findViewById(R.id.txt_receiving_dateID);
        txtDRS_received_by = findViewById(R.id.txt_drs_received_byID);
        txtDRS_status = findViewById(R.id.txt_drs_statusID);

        txt_challenFirst_no_ID = findViewById(R.id.txt_challenFirst_no_ID);
        txt_challenFirst_dateID = findViewById(R.id.txt_challenFirst_dateID);
        txt_challanFirst_fromID = findViewById(R.id.txt_challanFirst_fromID);
        txt_challanFirst_toID = findViewById(R.id.txt_challanFirst_toID);
        txt_vehicalFirst_noID = findViewById(R.id.txt_vehicalFirst_noID);
        txt_vehicalFirst_statusID = findViewById(R.id.txt_vehicalFirst_statusID);

        show_more_click_link = findViewById(R.id.show_more_click_link);
        challanCardView = findViewById(R.id.challenCardView);

        recyclerView = findViewById(R.id.challanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        challanList = new ArrayList<com.axpresslogistics.it2.axpresslogisticapp.model.DocketTracking>();
    }

    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(CONSTANT.LOADING_STATUS);
        progressDialog.show();

        setJsonDataOnDocketCardView();
        setJsonDataOnDRSCardView();
        setChallanJsonDataCardView();
        setDATA();

        show_more_click_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLAG.equals(false)) {
                    recyclerView.setVisibility(View.VISIBLE);
                    show_more_click_link.setText(CONSTANT.SHOW_LESS);
                    FLAG = true;
                } else if (FLAG.equals(true)) {
                    recyclerView.setVisibility(View.GONE);
                    show_more_click_link.setText(CONSTANT.SHOW_MORE);
                    linearLayout.invalidate();
                    FLAG = false;
                }
            }
        });
    }


    private void setJsonDataOnDocketCardView() {
        strDocket_no = jObj.optString(CONSTANT.DOCKET_NO);
        strDocket_date = jObj.optString(CONSTANT.DOCKET_DATE);
        strDocketConsignee = jObj.optString(CONSTANT.CONSIGNEE_NAME);
        strDocket_from = jObj.optString(CONSTANT.DOCKET_FROM);
        strDocket_to = jObj.optString(CONSTANT.DOCKET_TO);
        strDocket_status = jObj.optString(CONSTANT.DOCKET_STATUS);
    }

    private void setJsonDataOnDRSCardView() {
        try {
            JSONArray jsonArray = jObj.getJSONArray(CONSTANT.DRS);
            no_of_drs = jsonArray.length();
            for (int i = 0; i < no_of_drs; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                strDRS_date = object.optString(CONSTANT.DRS_DATE);
                strDRS_receiving = object.optString(CONSTANT.DRS_RECIVED_DATE);
                strDRS_received_by = object.optString(CONSTANT.DRS_RECIVED_BY);
                strDRS_status = object.optString(CONSTANT.DRS_STATUS);
                strDRS_no = object.optString(CONSTANT.DRS_NO);
            }
            progressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setChallanJsonDataCardView() {
        try {
            JSONArray jsonArray = jObj.getJSONArray(CONSTANT.CHALLAN);
            no_of_challan = jsonArray.length();
            for (int i = 0; i < no_of_challan; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (i == 0) {
                    str_challenFirst_no_ID = object.getString(CONSTANT.CHALLAN_NO);
                    str_challenFirst_dateID = object.getString(CONSTANT.CHALLAN_DATE);
                    str_challanFirst_fromID = object.getString("challan_from");
                    str_challanFirst_toID = object.getString("challan_to");
                    str_vehicalFirst_noID = object.getString("vehicle_no");
                    str_vehicalFirst_statusID = object.getString("status");
                } else {
                    com.axpresslogistics.it2.axpresslogisticapp.model.DocketTracking docketTracking = new
                            com.axpresslogistics.it2.axpresslogisticapp.model.DocketTracking(
                            object.getString("challan_no"),
                            object.getString("challan_date"),
                            object.getString("challan_from"),
                            object.getString("challan_to"),
                            object.getString("vehicle_no"),
                            object.getString("status"));

                    challanList.add(docketTracking);
                }

            }
            adapter = new DocketTrackingAdaptor(getApplicationContext(), challanList);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDATA() {
        txtDocket_no.setText(strDocket_no);
        txtDocketConsignee.setText(strDocketConsignee);
        txtDocket_date.setText(strDocket_date);
        txtDocket_from.setText(strDocket_from);
        txtDocket_to.setText(strDocket_to);
        txtDocket_status.setText(strDocket_status);

        txt_challenFirst_no_ID.setText(str_challenFirst_no_ID);
        txt_challenFirst_dateID.setText(str_challenFirst_dateID);
        txt_challanFirst_fromID.setText(str_challanFirst_fromID);
        txt_challanFirst_toID.setText(str_challanFirst_toID);
        txt_vehicalFirst_noID.setText(str_vehicalFirst_noID);
        txt_vehicalFirst_statusID.setText(str_vehicalFirst_statusID);

        txtDRS_no.setText(strDRS_no);
        txtDRS_date.setText(strDRS_date);
        txtDRS_receiving.setText(strDRS_receiving);
        txtDRS_received_by.setText(strDRS_received_by);
        txtDRS_status.setText(strDRS_status);
    }

    @Override
    public void onClick(View v) {
        String status_delivered = "delivered";
        String status_at_rest = "at rest";
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                Log.d("strDocket_status",strDocket_status);
                if (status_delivered.equals(strDocket_status.toLowerCase()) ||
                        status_at_rest.equals(strDocket_status.toLowerCase())) {
//                    showDialogbox();
                    Intent intent = new Intent(DocketTracking.this, MapsActivity.class);
                    intent.putExtra("docket", strDocket_no);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DocketTracking.this, MapsActivity.class);
                    intent.putExtra("docket", strDocket_no);
                    startActivity(intent);
                }
        }
    }

    private void showDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Docket : "+ strDocket_no);
        builder.setMessage("Docket has been "+ strDocket_status + ",Location disabled");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.support.v7.app.AlertDialog b = builder.create();
        b.show();
    }
}



