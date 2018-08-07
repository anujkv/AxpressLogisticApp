package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.adaptor.SavedCardAdaptor;
import com.example.it2.axpresslogisticapp.adaptor.SearchInputListAdaptor;
import com.example.it2.axpresslogisticapp.adaptor.VisitAdaptor;
import com.example.it2.axpresslogisticapp.model.SaveCardModel;
import com.example.it2.axpresslogisticapp.model.SearchInputListModel;
import com.example.it2.axpresslogisticapp.model.VisitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerViewListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_no_data_available,title_toolbar;
    EditText searchedt_toolbar;
    String url = "http://webapi.axpresslogistics.com/api/Operations/saved_list";
    String URL_SEARCH = "http://webapi.axpresslogistics.com/api/Operations/search_list";
    ImageButton backbtn_toolbar, addbtn_toolbar,searchbtn_toolbar;
    RecyclerView recyclerViewVisit,search_recyclerView;
    List<VisitModel> visitModelList,tempVisitModelList;
    VisitAdaptor adapter;
    List<SearchInputListModel> inputListModelList;
    SearchInputListAdaptor setAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText("Visit Activities");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        txt_no_data_available = findViewById(R.id.no_data_available);
        backbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);

        recyclerViewVisit = findViewById(R.id.customerRecyclerView);
        recyclerViewVisit.setHasFixedSize(true);
        recyclerViewVisit.setLayoutManager(new LinearLayoutManager(this));
        visitModelList = new ArrayList<>();

        search_recyclerView = findViewById(R.id.customerRecyclerView);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inputListModelList = new ArrayList<>();

        showVisitFormList();
    }

    private void  refresh() {
        try {
            if(visitModelList.size()>0 && inputListModelList.size()>0){
                visitModelList.clear();
                inputListModelList.clear();
            } else if(visitModelList.size()>0 || inputListModelList.size()>0) {
                if(visitModelList.size()>0){
                    visitModelList.clear();
                }else {
                    inputListModelList.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showVisitFormList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "saved_visit_form";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if(!visitModelList.isEmpty()){
            visitModelList.clear();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("saved_list");
                    String status = object.optString("status");
                    String apiKeyResponse = object.optString("key");
                    Log.e("response : ",response);
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
            case R.id.newbtn_toolbar:
                startActivity(new Intent(getApplicationContext(),CustomerVisitFormActivity.class));
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        inputListModelList.clear();
                        visitModelList.clear();
                        hitSearchAPi(s.toString());
                    }
                });
                break;
        }
    }

    private void hitSearchAPi(final String input) {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method = "search";
//        refresh();
        visitModelList.clear();
        inputListModelList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response>>>>", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("search");
                            String status = object.optString("status");
                            String apikeyResponse = object.optString("key");
                            if (status.equals("true")) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    SearchInputListModel inputListModel = new SearchInputListModel(
                                            jsonObject.getString("ref_no"),
                                            jsonObject.getString("company_name"),
                                            jsonObject.getString("contact_person"),
                                            jsonObject.getString("mobile_no"));
                                    inputListModelList.add(inputListModel);
                                }
                                visitModelList.clear();
                                setAdapter = new SearchInputListAdaptor(getApplicationContext(), inputListModelList);
                                search_recyclerView.setAdapter(setAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("input", input);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchedt_toolbar.setText("");
        title_toolbar.setVisibility(View.VISIBLE);
        searchedt_toolbar.setVisibility(View.GONE);
    }
}
