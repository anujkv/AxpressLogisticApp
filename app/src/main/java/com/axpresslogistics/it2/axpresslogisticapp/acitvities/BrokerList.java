package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.BrokerAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SearchBrokerListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SearchInputListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.VisitAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.BrokerModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.SearchBrokerListModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.SearchInputListModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.VisitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrokerList extends AppCompatActivity implements View.OnClickListener{
    ImageButton backbtn_toolbar, addbtn_toolbar,searchbtn_toolbar;
    TextView txt_no_data_available,title_toolbar;
    EditText searchedt_toolbar;
    ConstraintLayout no_data_availableLayout;
    ImageView refresh_image;
    List<SearchBrokerListModel> brokerListModelList;
    RecyclerView recyclerBrokerView,search_recyclerView;
    SearchBrokerListAdaptor setAdapter;
    List<BrokerModel> brokerModelList;
    BrokerAdaptor brokerAdaptor;
    String employee_id;
    String BROKER_LIST_URL = CONSTANT.URL +  "Operations/broker";
    String SEARCH_BROKER_URL = CONSTANT.URL + "Operations/searchbroker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(CONSTANT.BROKER_LIST);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
        backbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);
        employee_id = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);

        recyclerBrokerView = findViewById(R.id.brockerRecyclerView);
        recyclerBrokerView.setHasFixedSize(true);
        recyclerBrokerView.setLayoutManager(new LinearLayoutManager(this));
        brokerModelList = new ArrayList<>();

        search_recyclerView = findViewById(R.id.brockerRecyclerView);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        brokerListModelList = new ArrayList<>();
        showBrokerList();
        if(brokerModelList.size()>0){
            brokerModelList.clear();
        }else{
            refresh_page();
        }
    }

    private void showBrokerList() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "savedbrokerlist";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if(!brokerModelList.isEmpty()){
            brokerModelList.clear();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BROKER_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    Log.e("BrokerResponse : ",response);
                    JSONArray jsonArray = object.getJSONArray("broker");
                    String status = object.optString("status");
                    String apiKeyResponse = object.optString("key");
                    if(status.equals("true") && apiKeyResponse.equals(apikey)){
                        no_data_availableLayout.setVisibility(View.GONE);
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            BrokerModel brokerModel = new BrokerModel(
                                    jsonObject.getString("broker_name"),
                                    jsonObject.getString("broker_code"),
                                    jsonObject.getString("branch"));
                            brokerModelList.add(brokerModel);
                        }
                        brokerAdaptor = new BrokerAdaptor(getApplicationContext(),brokerModelList);
                        recyclerBrokerView.setAdapter(brokerAdaptor);
                    }
                    else {
                        no_data_availableLayout.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======",""+error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("method",method);
                params.put("key",apikey);
                params.put(CONSTANT.EMPID, employee_id);

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
                startActivity(new Intent(getApplicationContext(),AddBrokerActivity.class));
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        brokerListModelList.clear();
                        brokerModelList.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        hitBrokerSearchAPi(s.toString());
                    }
                });
                break;
            case R.id.refresh_image:
                refresh_page();
                break;
        }
    }

    private void hitBrokerSearchAPi(final String input) {

        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method = "search_broker";
//        refresh();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEARCH_BROKER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response>>>>", response);
                        brokerModelList.clear();
                        brokerListModelList.clear();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("brokersearch");
                            String status = object.optString("status");
                            String apikeyResponse = object.optString("key");
                            if (status.equals(CONSTANT.TRUE)) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    SearchBrokerListModel brokerListModel = new SearchBrokerListModel(
                                            jsonObject.getString("broker_name"),
                                            jsonObject.getString("broker_code"),
                                            jsonObject.getString("branch"));
                                    brokerListModelList.add(brokerListModel);
                                }
                                brokerModelList.clear();
                                setAdapter = new SearchBrokerListAdaptor(getApplicationContext(), brokerListModelList);
                                search_recyclerView.setAdapter(setAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("input", input);
                params.put(CONSTANT.EMPID,employee_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh_page();

    }

    private void refresh_page() {
        searchedt_toolbar.setText("");
        title_toolbar.setVisibility(View.VISIBLE);
        searchedt_toolbar.setVisibility(View.GONE);
        if(brokerModelList.size()>0){
            brokerListModelList.clear();
            brokerModelList.clear();
            showBrokerList();
        }
    }
}
