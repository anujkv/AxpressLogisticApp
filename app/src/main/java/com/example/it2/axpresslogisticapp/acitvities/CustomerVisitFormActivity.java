package com.example.it2.axpresslogisticapp.acitvities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.Utilities.Preferences;
import com.example.it2.axpresslogisticapp.adaptor.VisitHistoryAdaptor;
import com.example.it2.axpresslogisticapp.model.VisitHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.util.Calendar.getInstance;

public class CustomerVisitFormActivity extends AppCompatActivity implements View.OnClickListener {
    String URL_ADD_NEW = "http://webapi.axpresslogistics.com/api/Operations/customer_visit";
    String URL_FOLLOW = "http://webapi.axpresslogistics.com/api/Operations/customer_search";
    String VISIT_HISTORY_URL = "http://webapi.axpresslogistics.com/api/Operations/show_history";
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
    Intent intent;
    JSONObject jObj;
    Boolean FLAG = false;
    LinearLayout linearLayout;
    String jsonString, emplid, companyUniqueIDF = null;
    RecyclerView search_recyclerView;
    int MAX_LENGTH = 10;

    VisitHistoryAdaptor historyAdaptor;
    RecyclerView visitHistoryrecyclerview;
    List<VisitHistoryModel> visitHistoryModels;

    EmpProfileActivity empProfileActivity;
    CustomerViewListActivity customerViewListActivity;
    //    String empid = empProfileActivity.strEmpCode;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

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
        methodF = intent.getStringExtra("method");
        input = intent.getStringExtra("input");

        if (companyUniqueIDF != null && methodF.equals("customer_visit_search")) {
            pushonDBFollow(companyUniqueIDF, methodF, input);
        } else {
            txt_show_history.setVisibility(View.GONE);
        }

        visitHistoryrecyclerview = findViewById(R.id.historyRecyclerView);
        visitHistoryrecyclerview.setHasFixedSize(true);
        visitHistoryrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        visitHistoryModels = new ArrayList<>();
    }

    private void loadHistory(String companyUniqueIDF) {
        final String method = "show_visit_details";
        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        final String ref_no = companyUniqueIDF;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VISIT_HISTORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
//                    Log.e("History Show",response);

                            String statusResponse = object.optString("status");
                            String methodResponse = object.optString("method");

                            String response_ref_no = object.optString("ref_no");
                            String keyResponse = object.optString("key");
                            if (statusResponse.equals("true") && response_ref_no.equals(ref_no)) {
                                Log.e("History Show", response);
                                JSONArray array = object.getJSONArray("History");
                                for (int i = 0; i < array.length(); i++) {
                                    Log.e("Array lenght :", String.valueOf(array.length()));
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    VisitHistoryModel visitHistoryModel = new VisitHistoryModel(
                                            jsonObject.getString("customer"),
                                            jsonObject.getString("visit_date"),
                                            jsonObject.getString("visit_for"),
                                            jsonObject.getString("visit_type"),
                                            object.getString("ref_no"));
                                    visitHistoryModels.add(visitHistoryModel);
                                }
                                historyAdaptor = new VisitHistoryAdaptor(getApplicationContext(), visitHistoryModels);
                                visitHistoryrecyclerview.setAdapter(historyAdaptor);
                            } else {
                                Toast.makeText(getApplicationContext(), NO_HISTORY_AVAILABLE, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
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
            case R.id.add_new_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.add_exist_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_show_history:
//                if(companyUniqueIDF != null){
//                }else{
//                    Toast.makeText(getApplicationContext(), "No History Available", Toast.LENGTH_SHORT).show();
//                }
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
        calendar = getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(currentTime.getTime());
        showDate(formattedDate);
    }

    private void showDate(String formattedDate) {
        edt_visitdate.setText(formattedDate);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        edt_visitdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
//        str_visitdate = edt_visitdate.getText().toString().trim();
    }

    private void save() {
        getdata(compVisitID, businessType);

    }

    private void pushonDBFollow(final String companyUniqueIDF, final String businessType, final String input) {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String compvisitID = companyUniqueIDF;
        this.businessType = businessType;

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
                                    savebtn_toolbar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pushonDBNew(businessType, companyUniqueIDF);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), dataNotFatched, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "<<<<<" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "<<<<Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("<<<<Response: ", response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response : ", "" + error.toString());
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
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        getdata(companyUniqueIDF, businessType);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_NEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.length() > 0) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.optString("status");
                                String apiKeyResponse = object.optString("key");
                                Log.e("Response : ", response);

                                if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                                    Toast.makeText(getApplicationContext(), saved, Toast.LENGTH_SHORT).show();
                                    //activity finished for return back to CustomervisitListActivity after saving details...
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), notSaved, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "<<<<<" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "<<<<Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("<<<<Response: ", response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "=====" + error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                if (businessType.equals("customer_visit_search") && companyUniqueIDF != null) {
                    Log.e("ref_no", companyUniqueIDF);
                    Log.e("method", "customer_visit_update");
                    Log.e("key", apikey);
                    Log.e("emplid", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));

                    params.put("ref_no", companyUniqueIDF);
                    params.put("method", "customer_visit_update");
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                    params.put("customer", str_customer_name);
                    params.put("visit_date", str_visitdate);
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
                    Log.e("method", businessType);
                    Log.e("key", apikey);
                    Log.e("emplid", Preferences.getPreference(CustomerVisitFormActivity.this, CONSTANT.EMPID));
                    Log.e("customer", str_customer_name);
                    Log.e("visit_date", str_visitdate);
                    Log.e("visit_for", str_visit_for);
                    Log.e("visit_type", str_visit_type);
                    Log.e("contact_person", strContactPerson);
                    Log.e("contact", strContactNo);
                    Log.e("email_id", strEmail);
                    Log.e("address", strAddress);
                    Log.e("product", str_product_name);
                    Log.e("scope", str_scope);
                    Log.e("status", strStatus);
                    Log.e("remark", strRemark);
                    Log.e("other_employee_name", str_other_employee_name);

                    params.put("method", method);
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(CustomerVisitFormActivity.this, CONSTANT.EMPID));
                    params.put("customer", str_customer_name);
                    params.put("visit_date", str_visitdate);
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
