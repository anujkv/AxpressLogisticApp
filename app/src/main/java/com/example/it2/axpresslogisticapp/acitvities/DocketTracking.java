package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.DocketTrackingAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DocketTracking extends AppCompatActivity {
    //    String url = "http://webapi.axpresslogistics.com/api/Operations/Docket_Invoice";
    TextView txtDocket_no, txtDocket_date, txtDocket_from, txtDocket_to, txtDocketConsignee,
            txtDocket_status,txtDRS_no, txtDRS_date,txtDRS_receiving,txtDRS_received_by,txtDRS_status;

    String strDocket_no, strDocket_date, strDocket_from, strDocket_to, strDocketConsignee,
            strDocket_status, strDRS_no, strDRS_date,strDRS_receiving,strDRS_received_by,strDRS_status;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.example.it2.axpresslogisticapp.model.DocketTracking> challanList;
    String jsonString;
    JSONObject jObj;
    Intent intent;
    int no_of_challan;
    Boolean FLAG = true;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_tracking);
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
        
        recyclerView = findViewById(R.id.challanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        challanList = new ArrayList<com.example.it2.axpresslogisticapp.model.DocketTracking>();
    }

    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        setJsonDataOnDocketCardView();
        setChallanJsonDataCardView();
    }

    private void setChallanJsonDataCardView() {
        Log.d("response>>>> : ", jObj.toString());
        try {
            JSONArray jsonArray = jObj.getJSONArray("Challan");
            no_of_challan = jsonArray.length();
            for (int i = 0; i < no_of_challan; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                com.example.it2.axpresslogisticapp.model.DocketTracking docketTracking = new
                        com.example.it2.axpresslogisticapp.model.DocketTracking(
                        object.getString("challan_no"),
                        object.getString("challan_date"),
                        object.getString("challan_from"),
                        object.getString("challan_to"),
                        object.getString("vehicle_no"),
                        object.getString("status"));
                challanList.add(docketTracking);
            }
            adapter = new DocketTrackingAdaptor(getApplicationContext(), challanList);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setJsonDataOnDocketCardView() {
        strDocket_no = jObj.optString("docket_no");
        strDocket_date = jObj.optString("docket_date");
        strDocketConsignee = jObj.optString("consignee_name");
        strDocket_from = jObj.optString("docket_from");
        strDocket_to = jObj.optString("docket_to");
        strDocket_status = jObj.optString("docket_status");
        
//        strDRS_no = jObj.optString("drs_no");
//        strDRS_date = jObj.optString("drs_date");
//        strDRS_receiving = jObj.optString("drs_receiving_date");
//        strDRS_received_by = jObj.optString("drs_received_by");
//        strDRS_status = jObj.optString("drs_status");

        txtDocket_no.setText(strDocket_no);
        txtDocketConsignee.setText(strDocketConsignee);
        txtDocket_date.setText(strDocket_date);
        txtDocket_from.setText(strDocket_from);
        txtDocket_to.setText(strDocket_to);
        txtDocket_status.setText(strDocket_status);

//        txtDRS_no.setText(strDocket_no);
//        txtDRS_date.setText(strDRS_date);
//        txtDRS_receiving.setText(strDRS_receiving);
//        txtDRS_received_by.setText(strDRS_received_by);
//        txtDRS_status.setText(strDRS_status);
    }
}



