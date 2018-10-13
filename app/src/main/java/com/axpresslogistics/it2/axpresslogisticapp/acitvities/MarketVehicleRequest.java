package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.AddVehicleReq;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SearchVehicleReqAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.VehicleListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.SearchVehicleReqModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.VehicleListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketVehicleRequest extends AppCompatActivity implements View.OnClickListener {

    ImageButton backbtn_toolbar, addbtn_toolbar, searchbtn_toolbar;
    TextView txt_no_data_available, title_toolbar;
    EditText searchedt_toolbar;
    ConstraintLayout no_data_availableLayout;
    ImageView refresh_image;
    List<SearchVehicleReqModel> vehicleReqModelList;
    RecyclerView recyclerVehicleView, search_recyclerView;
    SearchVehicleReqAdaptor setAdapter;
    List<VehicleListModel> modelList;
    VehicleListAdaptor vehicleListAdaptor;
    String employee_id;
    String VEHICLE_LIST_URL = CONSTANT.URL + "Operations/market_vehicle_saved_list";
    String SEARCH_VEHICLE_URL = CONSTANT.URL + "Operations/search_vehicle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_vehicle_request);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(CONSTANT.VEHICLE_APPROVAL_LIST);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        addbtn_toolbar.setVisibility(View.GONE);
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
        backbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);
        employee_id = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);

        recyclerVehicleView = findViewById(R.id.market_vehicle_RecyclerView);
        recyclerVehicleView.setHasFixedSize(true);
        recyclerVehicleView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();

        search_recyclerView = findViewById(R.id.market_vehicle_RecyclerView);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vehicleReqModelList = new ArrayList<>();
        showVehicleList();
    }

    private void showVehicleList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "saved_vehicle_list";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (!modelList.isEmpty()) {
            modelList.clear();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,VEHICLE_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    Log.e("response : ", response);
                    String status = object.optString("status");
                    String apiKeyResponse = object.optString("key");
                    if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                        JSONArray jsonArray = object.getJSONArray("savedvehiclesearch");
                        no_data_availableLayout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            VehicleListModel vehicleModel = new VehicleListModel(
                                    jsonObject.getString("request_code"),
                                    jsonObject.getString("request_date"),
                                    jsonObject.getString("from_branch"),
                                    jsonObject.getString("to_branch"),
                                    jsonObject.getString("broker_name").split(" --")[0],
                                    jsonObject.getString("minimum_rate"));
                            modelList.add(vehicleModel);
                        }
                        vehicleListAdaptor = new VehicleListAdaptor(getApplicationContext(), modelList);
                        recyclerVehicleView.setAdapter(vehicleListAdaptor);
                    } else {
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
                Log.d("response======", "" + error.toString());
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey);
                params.put(CONSTANT.EMPID, employee_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        vehicleReqModelList.clear();
                        modelList.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        hitVehicleSearchAPi(s.toString());
                    }
                });
                break;
            case R.id.refresh_image:
                refresh_page();
                break;
        }
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
        if(modelList.size()>0){
            vehicleReqModelList.clear();
            modelList.clear();
            showVehicleList();
        }
    }

    private void hitVehicleSearchAPi(final String input) {

        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method = "search_broker";
//        refresh();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEARCH_VEHICLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response>>>>", response);
                        modelList.clear();
                        vehicleReqModelList.clear();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("vehicle_search");
                            String status = object.optString("status");
                            String apikeyResponse = object.optString("key");
                            if (status.equals(CONSTANT.TRUE)) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    SearchVehicleReqModel searchVehicleReqModel = new SearchVehicleReqModel(
                                            jsonObject.getString("request_code"),
                                            jsonObject.getString("request_date"),
                                            jsonObject.getString("from_branch"),
                                            jsonObject.getString("to_branch"),
                                            jsonObject.getString("broker_name").split(" --")[0],
                                            jsonObject.getString("minimum_rate"));
                                    vehicleReqModelList.add(searchVehicleReqModel);
                                }
                                modelList.clear();
                                setAdapter = new SearchVehicleReqAdaptor(getApplicationContext(), vehicleReqModelList);
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

}
