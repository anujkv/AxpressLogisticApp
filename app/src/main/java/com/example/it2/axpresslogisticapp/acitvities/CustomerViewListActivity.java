package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.SavedCardAdaptor;
import com.example.it2.axpresslogisticapp.adaptor.VisitAdaptor;
import com.example.it2.axpresslogisticapp.model.SaveCardModel;
import com.example.it2.axpresslogisticapp.model.VisitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerViewListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_no_data_available;
    String url = "http://webapi.axpresslogistics.com/api/Operations/saved_list";
    ImageButton backbtn_toolbar, addbtn_toolbar;
    RecyclerView recyclerViewVisit;
    List<VisitModel> visitModelList;
    VisitAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Visit Activities");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        addbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        txt_no_data_available = findViewById(R.id.no_data_available);
        backbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));

        recyclerViewVisit = findViewById(R.id.customerRecyclerView);
        recyclerViewVisit.setHasFixedSize(true);
        recyclerViewVisit.setLayoutManager(new LinearLayoutManager(this));
        visitModelList = new ArrayList<>();
        showVisitFormList();
    }

    private void showVisitFormList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        ApiKey apiKey = new ApiKey();
        final String method = "saved_visit_form";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("saved_list");

                    String status = object.optString("status");
                    String apiKeyResponse = object.optString("key");
                    Log.e("Status : ",status);
                    if(status.equals("true") && apiKeyResponse.equals(apikey)){
                        txt_no_data_available.setVisibility(View.GONE);
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            VisitModel visitModel = new VisitModel(
                                    jsonObject.getString("ref_no"),
                                    jsonObject.getString("company_name"),
                                    jsonObject.getString("contact_person"),
                                    jsonObject.getString("mobile"));
                            visitModelList.add(visitModel);
                        }
                        adapter = new VisitAdaptor(getApplicationContext(),visitModelList);
                        recyclerViewVisit.setAdapter(adapter);
                    }
                    else {
                        txt_no_data_available.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("method",method);
                params.put("key",apikey);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                startActivity(new Intent(getApplicationContext(),CustomerVisitFormActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        showVisitFormList();
    }
}
