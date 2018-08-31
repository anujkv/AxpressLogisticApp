package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.VisitHistoryAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.VisitHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class CustomerVisitFormActivity extends AppCompatActivity implements View.OnClickListener {
    String URL_ADD_NEW = URL + "Operations/customer_visit";
    String URL_FOLLOW = URL +"Operations/customer_search";
    String VISIT_HISTORY_URL = URL+ "Operations/show_history";
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_customer_name, edt_visitdate, edtContactPerson, edtContactNo, edtEmail, edtAddress,
            edt_product_name, edtRemark, edt_other_employee_name;
    String str_search, str_customer_name, str_visitdate, strContactPerson, strContactNo, strEmail, strAddress,
            str_product_name, strStatus, strRemark, str_other_employee_name, str_visit_for,
            str_visit_type, str_scope;
    TextView add_new_card, add_exist_card, txt_show_history;
    LinearLayout show_front_cardLayout, show_back_cardLayout, search_panalLayout;
    Spinner spinner_visit_for, spinner_visit_type, spinner_scope, spinner_status;
    String saved = "Saved", notSaved = "Data Not Saved", dataNotFatched = "Data Not Found", method,
            input, NO_HISTORY_AVAILABLE = "No History Available";
    String businessType, compVisitID, methodF;
    int NEW_FLAG =1, FOLLOW_FLAG = 2,SET_FLAG;
    Intent intent;
    Boolean FLAG = false;
    LinearLayout linearLayout;
    String companyUniqueIDF = null;
    int MAX_LENGTH = 10;

    VisitHistoryAdaptor historyAdaptor;
    RecyclerView visitHistoryrecyclerview;
    List<VisitHistoryModel> visitHistoryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_form);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Customer Visit Form");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));

        init();
        Intent intent = getIntent();
        companyUniqueIDF = intent.getStringExtra("ref_no");
        businessType = intent.getStringExtra("method");
        input = intent.getStringExtra("input");

        if (companyUniqueIDF != null && businessType.equals("customer_visit_search")) {
            pushonDBFollow(companyUniqueIDF, businessType, input);

        } else {
            businessType ="customer_visit_add";
            txt_show_history.setVisibility(View.GONE);
        }
        visitHistoryrecyclerview = findViewById(R.id.historyRecyclerView);
        visitHistoryrecyclerview.setHasFixedSize(true);
        visitHistoryrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        visitHistoryModels = new ArrayList<>();
    }

    private void loadHistory(String companyUniqueIDF) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
//        final String method = "show_visit_details";
        final String method = "show_visit";

        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        final String ref_no = companyUniqueIDF;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VISIT_HISTORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);

                            String statusResponse = object.optString("status");

                            String response_ref_no = object.optString("ref_no");
                            if (statusResponse.equals("true") && response_ref_no.equals(ref_no)) {
                                Log.e("History Show", response);
//                                JSONArray array = object.getJSONArray("History");
                                JSONArray array = object.getJSONArray("History_View");

                                for (int i = 0; i < array.length(); i++) {
                                    Log.e("Array length :", String.valueOf(array.length()));
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    VisitHistoryModel visitHistoryModel = new VisitHistoryModel(
//                                            jsonObject.getString("customer"),
                                            jsonObject.getString("customer_name"),

                                            jsonObject.getString("visit_date"),
                                            jsonObject.getString("visit_for"),
                                            jsonObject.getString("visit_type"),
                                            object.getString("ref_no"));
                                    visitHistoryModels.add(visitHistoryModel);
                                }
                                historyAdaptor = new VisitHistoryAdaptor(CustomerVisitFormActivity.this, visitHistoryModels);
                                visitHistoryrecyclerview.setAdapter(historyAdaptor);
                            } else {
                                Toast.makeText(getApplicationContext(), NO_HISTORY_AVAILABLE, Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", key);
                params.put("ref_no", ref_no);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void init() {
        linearLayout = findViewById(R.id.customerVisitLayout);
        search_panalLayout = findViewById(R.id.layout_search);
        edt_customer_name = findViewById(R.id.edt_customer_name);
        edt_visitdate = findViewById(R.id.edtVisitDate);
        edt_visitdate.setOnClickListener(this);

        edtContactPerson = findViewById(R.id.edtContactPerson);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtContactNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_product_name = findViewById(R.id.edt_product_name);
        edtRemark = findViewById(R.id.edtRemark);
        edt_other_employee_name = findViewById(R.id.edt_other_employee_name);
        txt_show_history = findViewById(R.id.txt_show_history);
        txt_show_history.setOnClickListener(this);

        add_new_card = findViewById(R.id.add_new_card);
        add_exist_card = findViewById(R.id.add_exist_card);

        show_front_cardLayout = findViewById(R.id.show_front_cardLayout);
        show_back_cardLayout = findViewById(R.id.show_back_cardLayout);
        show_front_cardLayout.setOnClickListener(this);
        show_back_cardLayout.setOnClickListener(this);

        spinner_visit_for = findViewById(R.id.spinner_visit_for);
        spinner_visit_type = findViewById(R.id.spinner_visit_type);
        spinner_scope = findViewById(R.id.spinner_scope);
        spinner_status = findViewById(R.id.spinner_status);
    }

    private void getdata(String compVisitID, String businessType) {
        method = businessType;
        this.compVisitID = compVisitID;
        str_customer_name = edt_customer_name.getText().toString().trim();
        str_visitdate = edt_visitdate.getText().toString().trim();
        str_visit_for = spinner_visit_for.getSelectedItem().toString();
        str_visit_type = spinner_visit_type.getSelectedItem().toString();
        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        strStatus = spinner_status.getSelectedItem().toString();
        str_scope = spinner_scope.getSelectedItem().toString();
        strRemark = edtRemark.getText().toString().trim();
        str_other_employee_name = edt_other_employee_name.getText().toString().trim();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                save();
                break;
            case R.id.edtVisitDate:
                callDateDialogBox();
                break;
            case R.id.spinner_visit_for:
                spinner_visit_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_for = parent.getItemAtPosition(position).toString();
                        if(position>0){
                            spinner_visit_for.setBackgroundColor(Color.WHITE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_visit_type:
                spinner_visit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_type = parent.getItemAtPosition(position).toString();
                        if(position>0){
                            spinner_visit_for.setBackgroundColor(Color.WHITE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_scope:
                spinner_scope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_scope = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_status:
                spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        strStatus = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.txt_show_history:
                if (FLAG.equals(false)) {
                    loadHistory(companyUniqueIDF);
                    visitHistoryrecyclerview.setVisibility(View.VISIBLE);
                    txt_show_history.setText("Show History");
                    FLAG = true;
                } else if (FLAG.equals(true)) {
                    visitHistoryrecyclerview.setVisibility(View.GONE);
                    visitHistoryModels.clear();
                    txt_show_history.setText("Hide History");
                    linearLayout.invalidate();
                    FLAG = false;
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callDateDialogBox() {
        edt_visitdate = findViewById(R.id.edtVisitDate);
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String s = df.format(now);
        showDate(s);
        Log.e("DATE : ",s);
    }

    private void showDate(String formattedDate) {
        edt_visitdate.setText(formattedDate);
        Log.e("Date : ",formattedDate);
    }

    private void save() {
//        if(businessType.equals("customer_visit_search")){
            getdata(compVisitID, this.businessType);
            validation();
//            Log.e("search ===comVISIT_ID",companyUniqueIDF);
//            Log.e("search ===businessType",businessType);
//            pushonDBNew(businessType, companyUniqueIDF);
//        } else if (businessType.equals("customer_visit_add")) {
//
////            Log.e("add ===comVISIT_ID",compVisitID);
////            Log.e("add ===businessType",businessType);
//            getdata(compVisitID, this.businessType);
//            validation(this.businessType);
//        }

    }

    private void validation() {
        if(str_customer_name == null || str_customer_name.equals("")){
            edt_customer_name.setHint("Mandatory *");
            edt_customer_name.setHintTextColor(Color.RED);
        }
        if(str_visitdate == null || str_visitdate.equals("")){
            edt_visitdate.setHint("Mandatory *");
            edt_visitdate.setHintTextColor(Color.RED);
        }if(str_visit_for == null || str_visit_for.equals("Select")){
            spinner_visit_for.setBackgroundColor(Color.RED);
        }if(str_visit_type == null || str_visit_type.equals("Select")){
            spinner_visit_type.setBackgroundColor(Color.RED);
        }if(strContactPerson == null || strContactPerson.equals("")){
            edtContactPerson.setHint("Mandatory *");
            edtContactPerson.setHintTextColor(Color.RED);
        }if(strContactNo == null || strContactNo.equals("")){
            edtContactNo.setHint("Mandatory *");
            edtContactNo.setHintTextColor(Color.RED);
        }if(str_product_name == null || strContactNo.equals("")){
            edt_product_name.setHint("Mandatory *");
            edt_product_name.setHintTextColor(Color.RED);
        }if(strRemark == null || strRemark.equals("")){
            edtRemark.setHint("Mandatory *");
            edtRemark.setHintTextColor(Color.RED);
        }else{
            pushonDBNew(businessType,companyUniqueIDF);
        }
    }

    private void pushonDBFollow(final String companyUniqueIDF, final String businessType, final String input) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String compvisitID = companyUniqueIDF;
        this.businessType = businessType;
        progressDialog.setMessage(CONSTANT.LOADING_STATUS);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.length() > 0) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.optString("status");
                                String apiKeyResponse = object.optString("key");
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                Log.e("Response Follow :", response);

                                if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                    setData(object);
                                    progressDialog.dismiss();
                                    savebtn_toolbar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getdata(compVisitID, businessType);
                                            validation();
//                                            pushonDBNew(businessType, companyUniqueIDF);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), dataNotFatched, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "<<<<<" + e.toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "<<<<Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("<<<<Response: ", response.toString());
                            progressDialog.dismiss();
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
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("ref_no", companyUniqueIDF);
                Log.e("method", businessType);
                Log.e("key", apikey);
                Log.e("input", input);

                params.put("method", businessType);
                params.put("key", apikey);
                params.put("ref_no", compvisitID);
                params.put("input", input);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void pushonDBNew(final String businessType, final String companyUniqueIDF) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_NEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.length() > 0) {
                            progressDialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.optString("status");
                                String apiKeyResponse = object.optString("key");
                                Log.e("Response : ", response);

                                if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), saved, Toast.LENGTH_SHORT).show();
                                    //activity finished for return back to CustomervisitListActivity after saving details...
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), notSaved, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "<<<<<" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "<<<<Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("<<<<Response: ", response.toString());
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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                getdata(compVisitID, businessType);
                if(companyUniqueIDF != null){
                    getdata(compVisitID, businessType);
//                if (businessType.equals("customer_visit_search") && companyUniqueIDF != null)  {
                    params.put("ref_no", companyUniqueIDF);
                    params.put("method", "customer_visit_update");
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                    params.put("customer", str_customer_name);
                    params.put("visit_date", "13-08-2018 14:10:32");
                    params.put("visit_for", str_visit_for);
                    params.put("visit_type", str_visit_type);
                    params.put("contact_person", strContactPerson);
                    params.put("contact", strContactNo);
                    params.put("email_id", strEmail);
                    params.put("address", strAddress);
                    params.put("product", str_product_name);
                    params.put("scope", str_scope);
                    params.put("status", strStatus);
                    params.put("remark", strRemark);
                    params.put("other_employee_name", str_other_employee_name);
                    return params;
                } else {
                    Log.e("method",businessType);
                    Log.e("str_customer_name",str_customer_name);
                    Log.e("str_visitdate",str_visitdate.trim());
                    Log.e("str_visit_for",str_visit_for);
                    Log.e("str_visit_type",str_visit_type);
                    Log.e("strContactPerson",strContactPerson);
                    Log.e("strContactNo",strContactNo);
                    Log.e("strEmail",strEmail);
                    Log.e("strAddress",strAddress);
                    Log.e("strStatus",strStatus);
                    Log.e("str_scope",str_scope);
                    Log.e("strRemark",strRemark);
                    Log.e("str_other_employee_name",str_other_employee_name);

                    params.put("method", businessType);
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(CustomerVisitFormActivity.this, CONSTANT.EMPID));
                    params.put("customer", str_customer_name);
                    params.put("visit_date", "13-08-2018 14:10:32");
                    params.put("visit_for", str_visit_for);
                    params.put("visit_type", str_visit_type);
                    params.put("contact_person", strContactPerson);
                    params.put("contact", strContactNo);
                    params.put("email_id", strEmail);
                    params.put("address", strAddress);
                    params.put("product", str_product_name);
                    params.put("scope", str_scope);
                    params.put("status", strStatus);
                    params.put("remark", strRemark);
                    params.put("other_employee_name", str_other_employee_name);
                    return params;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void selectedSearchValue(final String id) {
        ApiKey apiKey = new ApiKey();
        final String method = "customer_visit_follow";
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            setData(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
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
                params.put("key", apikey);
                params.put("customer_visit_search", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setData(JSONObject jsonObject) {
        str_visit_for = jsonObject.optString("visit_for");
        str_visit_type = jsonObject.optString("visit_type");
        strStatus = jsonObject.optString("status");
        str_scope = jsonObject.optString("scope");
        //============================================
        edt_customer_name.setText(jsonObject.optString("customer"));
        edt_visitdate.setText(jsonObject.optString("visit_date"));
//        spinner_visit_for.setSelection(Integer.parseInt(jsonObject.optString("visit_for")));
//        spinner_visit_type.setSelection(Integer.parseInt(jsonObject.optString("visit_type")));

        edtContactPerson.setText(jsonObject.optString("contact_person"));
        edtContactNo.setText(jsonObject.optString("contact"));
        edtEmail.setText(jsonObject.optString("email_id"));
        edtAddress.setText(jsonObject.optString("address"));
        edt_product_name.setText(jsonObject.optString("product"));

//        spinner_status.setSelection(Integer.parseInt(jsonObject.optString("followup_status")));
//        spinner_scope.setSelection(Integer.parseInt(jsonObject.optString("scope")));
        edtRemark.setText(jsonObject.optString("remark"));
        edt_other_employee_name.setText(jsonObject.optString("other_employee_name"));
    }


}
