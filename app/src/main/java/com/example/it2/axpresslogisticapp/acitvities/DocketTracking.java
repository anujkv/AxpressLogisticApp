package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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
    TextView txtDocket_no,txtDocket_date,txtDocket_from,txtDocket_to,txtDocketConsignee,txtDocket_status;
    String strDocket_no,strDocket_date,strDocket_from,strDocket_to,strDocketConsignee,strDocket_status;

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<com.example.it2.axpresslogisticapp.model.DocketTracking> challanList;
    private RecyclerView.Adapter adapter;
    String jsonString;
    JSONObject jObj;
    Intent intent;
    Boolean FLAG = true;
    ProgressBar progressBar;
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
        mList = findViewById(R.id.challanRecyclerView);

        challanList = new ArrayList<>();
        adapter = new DocketTrackingAdaptor(getApplicationContext(),challanList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
    }
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        setJsonDataOnDocketCardView();
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//
//                        com.example.it2.axpresslogisticapp.model.DocketTracking docketTracking = new com.example.it2.axpresslogisticapp.model.DocketTracking();
//                        docketTracking.setChallan_no(jsonObject.getString("challan_no"));
//                        docketTracking.setChallan_date(jsonObject.getString("challan_date"));
//                        docketTracking.setChallan_from(jsonObject.getString("challan_from"));
//                        docketTracking.setChallan_to(jsonObject.getString("challan_to"));
//                        docketTracking.setVehicle_no(jsonObject.getString("vehicle_no"));
//                        docketTracking.setChallan_status(jsonObject.getString("status"));
//
//                        challanList.add(docketTracking);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        progressDialog.dismiss();
//                    }
//                }
//                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Volley", error.toString());
//                progressDialog.dismiss();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonArrayRequest);

    }

    private void setJsonDataOnDocketCardView() {
        strDocket_no = jObj.optString("docket_no");
        strDocket_date = jObj.optString("docket_date");
        strDocketConsignee = jObj.optString("consignee_name");
        strDocket_from = jObj.optString("docket_from");
        strDocket_to = jObj.optString("docket_to");
        strDocket_status = jObj.optString("docket_status");

        txtDocket_no.setText(strDocket_no);
        txtDocketConsignee.setText(strDocketConsignee);
        txtDocket_date.setText(strDocket_date);
        txtDocket_from.setText(strDocket_from);
        txtDocket_to.setText(strDocket_to);
        txtDocket_status.setText(strDocket_status);
    }

    private String saltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}



