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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SearchInputListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.VisitAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.SearchInputListModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.VisitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class CustomerViewListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_no_data_available,title_toolbar;
    EditText searchedt_toolbar;
    String url = URL + "Operations/saved_list";
    String URL_SEARCH = URL + "Operations/search_list";
    ImageButton backbtn_toolbar, addbtn_toolbar,searchbtn_toolbar;
    ImageView refresh_image;
    RecyclerView recyclerViewVisit,search_recyclerView;
    List<VisitModel> visitModelList,tempVisitModelList;
    VisitAdaptor adapter;
    List<SearchInputListModel> inputListModelList;
    SearchInputListAdaptor setAdapter;
    ConstraintLayout no_data_availableLayout;

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
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
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
        if(visitModelList.size()>0){
            visitModelList.clear();
        }else{
            showVisitFormList();
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
                        no_data_availableLayout.setVisibility(View.GONE);
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
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public Boolean check_company_name(String str_customer_name) {
        if(visitModelList.size()>0){
            for(int i = 0; i> visitModelList.size(); i++){
                visitModelList.listIterator(i);
                Log.e("Value of I : ",visitModelList.listIterator(i).toString());
            }
        }
        return true;
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
                        inputListModelList.clear();
                        visitModelList.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        hitSearchAPi(s.toString());
                    }
                });
                break;
            case R.id.refresh_image:
                refresh_page();
                break;
        }
    }

    private void refresh_page() {
        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);
        ((ImageView)findViewById(R.id.refresh_image)).setAnimation(animation);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final String method = "leave_info";
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        try {
            if(inputListModelList.size()>0){
                inputListModelList.clear();
                visitModelList.clear();
                showVisitFormList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
    }

    private void hitSearchAPi(final String input) {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method = "search";
//        refresh();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response>>>>", response);
                        visitModelList.clear();
                        inputListModelList.clear();
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();

    }

    private void refresh() {
        searchedt_toolbar.setText("");
        title_toolbar.setVisibility(View.VISIBLE);
        searchedt_toolbar.setVisibility(View.GONE);
        if(inputListModelList.size()>0){
            inputListModelList.clear();
            visitModelList.clear();
            showVisitFormList();
        }
    }
}
