package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddBrokerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_branch, edtbrokerName, edtContactNo, edtContactAltNo, edtEmail, edtAddress,
            edt_bank_account_no, edt_bank_name, edt_pan_no, edt_name_on_pancard, edtBankIFSC,
            edtCreatedBy, edtCreatedOn;
    String str_branch, strbrokerName, strContactNo, strContactAltNo, strEmail, strAddress,
            str_bank_account_no, str_bank_name, str_pan_no, str_name_on_pancard, strBankIFSC,
            strCreatedBy, strCreatedOn, strbroker_status,str_broker_code,function_method = null;
    int selectedId;
    String broker_code_from_broker_list = null;
    String method, input, branch_code;
    RadioButton radio_active_id, radio_inactive_id, radio_btn_id;
    RadioGroup radio_group_id;
    int MAX_LENGTH = 10;

    String ADD_NEW_BROKER_URL = CONSTANT.DEVELOPMENT_URL+ "Operations/broker_add";
    String FETCH_BROKER_DETAILS_URL = CONSTANT.DEVELOPMENT_URL+"Operations/broker_fetch";
    String UPDATE_BROKER_DETAILS_URL = CONSTANT.DEVELOPMENT_URL+"Operations/brokerupdate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_broker);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.ADD_BROKER);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        init();

        Intent intent = getIntent();
        broker_code_from_broker_list = intent.getStringExtra("broker_code");
        function_method = intent.getStringExtra("method");

        if(broker_code_from_broker_list!=null && !broker_code_from_broker_list.equals(CONSTANT.BLANK));{
            fetch_broker_details(broker_code_from_broker_list);
        }
    }

    private void addbroker() {
        strbrokerName = edtbrokerName.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strContactAltNo = edtContactAltNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_bank_account_no = edt_bank_account_no.getText().toString().trim();
        str_bank_name = edt_bank_name.getText().toString().trim();
        str_pan_no = edt_pan_no.getText().toString().trim();
        str_name_on_pancard = edt_name_on_pancard.getText().toString().trim();
        strBankIFSC = edtBankIFSC.getText().toString().trim();
        strCreatedBy = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        strCreatedOn = currentDate();
        Log.e("current time :", strCreatedOn);
    }

    public String currentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    private void init() {
        edt_branch = findViewById(R.id.edt_branch);
        str_branch = Preferences.getPreference(AddBrokerActivity.this, CONSTANT.EMPLOYEE_BRANCH);
        edt_branch.setText(str_branch);
        strCreatedBy = Preferences.getPreference(AddBrokerActivity.this, CONSTANT.EMPID);
        edtbrokerName = findViewById(R.id.edtbrokerName);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtContactNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtContactAltNo = findViewById(R.id.edtContactAltNo);
        edtContactAltNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_bank_account_no = findViewById(R.id.edt_bank_account_no);
        edt_bank_name = findViewById(R.id.edt_bank_name);
        edt_pan_no = findViewById(R.id.edt_pan_no);
        edt_name_on_pancard = findViewById(R.id.edt_name_on_pancard);
        edtBankIFSC = findViewById(R.id.edtBankIFSC);
        edtCreatedBy = findViewById(R.id.edtCreatedBy);
        edtCreatedOn = findViewById(R.id.edtCreatedOn);
        radio_active_id = findViewById(R.id.radiobtn_active_id);
        radio_inactive_id = findViewById(R.id.radiobtn_inactive_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                mandatoryCheck();
//                save();
//                add_function_api_call();
                break;
        }
    }

    private void mandatoryCheck() {
        addbroker();

        if (strbroker_status == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.CHOOSE_THE_STATUS_TYPE,
                    Toast.LENGTH_SHORT).show();
        }
        if (strbrokerName == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_BROKER_NAME,
                    Toast.LENGTH_SHORT).show();
            edtbrokerName.setHint(CONSTANT.MANDATORY);
            edtbrokerName.setHintTextColor(Color.RED);
        }
        if (strContactNo == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CONTACT_NO,
                    Toast.LENGTH_SHORT).show();
            edtContactNo.setHint(CONSTANT.MANDATORY);
            edtContactNo.setHintTextColor(Color.RED);
        }if(strContactNo!=null){
            if(strContactNo.length()<MAX_LENGTH){
                Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CORRECT_CONTACT_NO,
                    Toast.LENGTH_SHORT).show();
                edtContactNo.setHintTextColor(Color.RED);
            }
        }
        if (strEmail == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_EMAIL,
                    Toast.LENGTH_SHORT).show();
            edtEmail.setHint(CONSTANT.MANDATORY);
            edtEmail.setHintTextColor(Color.RED);
        }if(strEmail!=null){
            if(!strEmail.contains("@")){
                Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CORRECT_EMAIL,
                        Toast.LENGTH_SHORT).show();
                edtContactNo.setHintTextColor(Color.RED);
            }
        }
        if (strAddress == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_ADDRESS,
                    Toast.LENGTH_SHORT).show();
            edtAddress.setHint(CONSTANT.MANDATORY);
            edtAddress.setHintTextColor(Color.RED);
        } else {
            save();
        }
    }

    private void save() {
        if(function_method.equals("update_broker")){
            update_function_api_call();
        }else{
            add_function_api_call();

        }
    }

    private void add_function_api_call() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        method = "add_broker";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_NEW_BROKER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response Broker;",response);

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String apikeyresponse = object.getString("key");

                            if(status.equals(CONSTANT.TRUE) &&
                                    apikeyresponse.equals(key)){
                                Toast.makeText(getApplicationContext(),CONSTANT.ADD_BROKER_SUCCESSFULLY,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }else if(status.equals(CONSTANT.ALREADY_EXISTS)){
                                Toast.makeText(getApplicationContext(),CONSTANT.ALREADY_EXISTS,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),CONSTANT.UNSUCCESSFULLY,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("JSONException",e.getMessage());
                            Toast.makeText(getApplicationContext(),CONSTANT.UNSUCCESSFULLY,
                                    Toast.LENGTH_SHORT).show();
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
                params.put("method",method);
                params.put("key",key);
                params.put(CONSTANT.BRANCH, str_branch);
                params.put(CONSTANT.broker_name, strbrokerName);
                params.put(CONSTANT.CONTACT, strContactNo);
                params.put(CONSTANT.alternate_contact, strContactAltNo);
                params.put(CONSTANT.EMAIL_ID, strEmail);
                params.put(CONSTANT.ADDRESS, strAddress);
                params.put(CONSTANT.ACTIVE_STATUS, strbroker_status);
                params.put("created_by", strCreatedBy);
                params.put("created_on", strCreatedOn);
                params.put("bank_account_no", str_bank_account_no);
                params.put("bank_name", str_bank_name);
                params.put("pan_no", str_pan_no);
                params.put("name_on_pancard", str_name_on_pancard);
                params.put("bank_ifsc", strBankIFSC);

                Log.e("BRANCH :", str_branch);
                Log.e("broker_name", strbrokerName);
                Log.e("strContactNo", strContactNo);
                Log.e("strContactAltNo", strContactAltNo);
                Log.e("strEmail", strEmail);
                Log.e("address", strAddress);
                Log.e("broker_status :", "Y");
                Log.e("created_on", strCreatedOn);
                Log.e("bank_account_no", str_bank_account_no);
                Log.e("bank_name", str_bank_name);
                Log.e("pan_no", str_pan_no);
                Log.e("name_on_pancard", str_name_on_pancard);
                Log.e("bank_ifsc", strBankIFSC);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void update_function_api_call() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_BROKER_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String apikeyresponse = object.getString("key");

                            if(status.equals(CONSTANT.TRUE) &&
                                    apikeyresponse.equals(key)){
                                Toast.makeText(getApplicationContext(),CONSTANT.DATA_UPDATE,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),CONSTANT.UNSUCCESSFULLY,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("JSONException",e.getMessage());
                            Toast.makeText(getApplicationContext(),CONSTANT.IMAGE_UPLOADED_UNSUCCESSFULL,
                                    Toast.LENGTH_SHORT).show();
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
                params.put(CONSTANT.METHOD,function_method);
                params.put(CONSTANT.KEY,key);
                params.put(CONSTANT.BRANCH, str_branch);
                params.put(CONSTANT.BROKER_CODE,broker_code_from_broker_list);
                params.put(CONSTANT.broker_name, strbrokerName);
                params.put(CONSTANT.CONTACT, strContactNo);
                params.put(CONSTANT.alternate_contact, strContactAltNo);
                params.put(CONSTANT.EMAIL_ID, strEmail);
                params.put(CONSTANT.ADDRESS, strAddress);
                params.put(CONSTANT.ACTIVE_STATUS, strbroker_status);
                params.put("modified_by", strCreatedBy);
                params.put("modified_on", currentDate());
                params.put("bank_account_no", str_bank_account_no);
                params.put("bank_name", str_bank_name);
                params.put("pan_no", str_pan_no);
                params.put("name_on_pancard", str_name_on_pancard);
                params.put("bank_ifsc", strBankIFSC);
                Log.e("BRANCH :", str_branch);
                Log.e(CONSTANT.broker_name, strbrokerName);
                Log.e(CONSTANT.BROKER_CODE, broker_code_from_broker_list);
                Log.e(CONSTANT.alternate_contact, strContactAltNo);
                Log.e(CONSTANT.EMAIL_ID, strEmail);
                Log.e(CONSTANT.ADDRESS, strAddress);
                Log.e("strCreatedOn", strCreatedOn);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radiobtn_active_id:
                if (checked) {
                    strbroker_status = "Y";
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "Active", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radiobtn_inactive_id:
                if (checked){
                    strbroker_status = "N";
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "InActive", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //------------------fetch broker data-------------------------------------
    private void fetch_broker_details(final String broker_code_from_broker_list) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();
        final String method = "broker_detail_fetch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_BROKER_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response Fetch data",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String responseKey = object.getString(CONSTANT.KEY);
                            if(status.equals(CONSTANT.TRUE) && responseKey.equals(key)){
                                edt_branch.setText(object.optString("branch"));
                                str_broker_code = object.optString("broker_code");
                                edtbrokerName.setText(object.optString("broker_name"));
                                edtContactNo.setText(object.optString("contact"));
                                edtContactAltNo.setText(object.optString("alternate_contact"));
                                edtEmail.setText(object.optString("email_id"));
                                edtAddress.setText(object.optString("address"));
                                strbroker_status = object.optString("active_status");
                                if(strbroker_status!=null && !strbroker_status.equals(CONSTANT.BLANK)){
                                    if(strbroker_status.equals("Y")){
                                        radio_active_id.setChecked(true);
                                    }else{
                                        radio_inactive_id.setChecked(true);
                                    }
                                }
                                strCreatedBy = object.optString("created_by");
                                strCreatedOn = object.optString("created_on");
                                edt_bank_account_no.setText(object.optString("bank_account_no"));
                                edt_bank_name.setText(object.optString("bank_name"));
                                edt_pan_no.setText(object.optString("pan_no"));
                                edt_name_on_pancard.setText(object.optString("name_on_pancard"));
                                edtBankIFSC.setText(object.optString("bank_ifsc"));
                            }
                        } catch (JSONException e) {
                            Log.e("JSONException",e.getMessage());
                            Toast.makeText(getApplicationContext(),CONSTANT.DATA_NOT_FOUND,
                                    Toast.LENGTH_SHORT).show();
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
                params.put(CONSTANT.METHOD,method);
                params.put(CONSTANT.KEY,key);
                params.put(CONSTANT.BROKER_CODE,broker_code_from_broker_list);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
