package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.BrokerApprovalAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.SearchVehicleReqAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.BrokerApprovalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicalApproval extends AppCompatActivity implements View.OnClickListener{
    ImageButton backbtn_toolbar, savebtn_toolbar;
    TextView show1,show2,show3;
    Boolean FLAG_DROP_DOWN = false, FLAG_DROP_DOWN2 = false, FLAG_DROP_DOWN3 = false;
    CardView broker1_rate_details,broker2_rate_details,broker3_rate_details;
    String method,str_empid,methodAPI,vehicle_req_code,broker_status,BROKER_RATE,BROKER_NAME,
            BROKER_CODE,SELECTION_STATUS;
    String APPROVED_URL = CONSTANT.DEVELOPMENT_URL + "Operations/approved_vehicle";
    String FETCH_APPROVAL_URL = CONSTANT.DEVELOPMENT_URL + "Operations/saved_vehicle_list_for_approval";
    String str_vehicle_req_code,str_request_date,str_from_branch,str_to_branch,str_loading_point,
            str_unloading_point,str_req_type,str_goods_type,str_act_wt_of_goods,
            str_vehicle_type,str_broker_code,str_approved_status, str_contact, str_broker_rate,
            str_broker_advance, str_broker_remark;
    int no_of_brokers;
    EditText edt_vehicle_req_code,edt_vehicle_req_date,edt_from_branch,edt_to_branch,edt_loading_point,
            edt_unloading_point,spinner_req_type,spinner_goods_type,edt_actual_wt_of_goods,
            spinner_vehicle_type,
            spinner_broker_selection1,spinner_broker_selection2,spinner_broker_selection3;
    Button btn_broker_selection1,btn_broker_selection2,btn_broker_selection3;
    BrokerApprovalAdaptor fetchapprovalAdaptor;
    List<BrokerApprovalModel> modelList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_approval);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.VEHICLE_APPROVAL);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        init();

        recyclerView = findViewById(R.id.broker_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();

        str_from_branch = Preferences.getPreference(getApplicationContext(),CONSTANT.EMPLOYEE_BRANCH);
        Log.e("from_branch",str_from_branch);
        str_empid = Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
        edt_from_branch.setText(str_from_branch.trim());
        vehicle_req_code = getIntent().getStringExtra("vehicle_request_code");
        methodAPI = getIntent().getStringExtra("method");
        if(vehicle_req_code != null){
            fetch_approval_details(methodAPI,vehicle_req_code);
            set_data_in_fields();
        }
        String APPROVED_BROKER_CODE = getIntent().getStringExtra("broker_code");
        if(APPROVED_BROKER_CODE != null){
            BROKER_RATE = getIntent().getStringExtra("broker_rate");
            BROKER_CODE = getIntent().getStringExtra("broker_code");
            BROKER_NAME = getIntent().getStringExtra("broker_name");
            SELECTION_STATUS = getIntent().getStringExtra("approved_status") ;
        }
    }

    private void set_data_in_fields() {
        edt_vehicle_req_code.setText(str_vehicle_req_code);
        edt_vehicle_req_date.setText(str_request_date);
        edt_from_branch.setText(str_from_branch);
        edt_to_branch.setText(str_to_branch);
        edt_loading_point.setText(str_loading_point);
        edt_unloading_point.setText(str_unloading_point);
        spinner_req_type.setText(str_req_type);
        spinner_goods_type.setText(str_goods_type);
        edt_actual_wt_of_goods.setText(str_act_wt_of_goods);
        spinner_vehicle_type.setText(str_vehicle_type);
    }

    private void fetch_approval_details(String methodAPI, final String vehicle_req_code) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = methodAPI;
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_APPROVAL_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response:",response);
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString(CONSTANT.STATUS);
                    String keyResponse = object.getString(CONSTANT.KEY);
                    broker_status = object.getString(CONSTANT.BROKER_STATUS);
                    if(broker_status.equals(CONSTANT.B)){
                        str_approved_status = "BApproved";
                    }else if (broker_status.equals(CONSTANT.L)){
                        str_approved_status = "Approved";
                    }
                    if(status.equals(CONSTANT.TRUE)) {
                        JSONArray array = object.getJSONArray("vpa");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            str_vehicle_req_code = jsonObject.getString("vehicle_request_code");
                            edt_vehicle_req_code.setText(str_vehicle_req_code);
                            str_request_date = jsonObject.getString("request_date");
                            edt_vehicle_req_date.setText(str_request_date);
                            str_from_branch = jsonObject.getString("from_branch");
                            edt_from_branch.setText(str_from_branch);
                            str_to_branch = jsonObject.getString("to_branch");
                            edt_to_branch.setText(str_to_branch);
                            str_loading_point = jsonObject.getString("loading_point");
                            edt_loading_point.setText(str_loading_point);
                            str_unloading_point = jsonObject.getString("unloading_point");
                            edt_unloading_point.setText(str_unloading_point);
                            str_req_type = jsonObject.getString("requirement_type");
                            spinner_req_type.setText(str_req_type);
                            str_goods_type = jsonObject.getString("goods_type");
                            spinner_goods_type.setText(str_goods_type);
                            str_act_wt_of_goods = jsonObject.getString("actual_wt_of_goods");
                            edt_actual_wt_of_goods.setText(str_act_wt_of_goods);
                            str_vehicle_type = jsonObject.getString("vehicle_type");
                            spinner_vehicle_type.setText(str_vehicle_type);

                            JSONArray jsonArray = jsonObject.getJSONArray("bselectiondetail");
                            no_of_brokers = jsonArray.length();
                            for (int j = 0; j < jsonArray.length(); j++){
                                JSONObject jsonObjectbroker = jsonArray.getJSONObject(j);
                                BrokerApprovalModel model = new BrokerApprovalModel(
                                        jsonObjectbroker.getString("broker_name"),
                                        jsonObjectbroker.getString("broker_code"),
                                        jsonObjectbroker.getString("broker_rate"),
                                        jsonObjectbroker.getString("broker_advance"),
                                        jsonObjectbroker.getString("broker_remark"),
                                        object.getString(CONSTANT.BROKER_STATUS));
                                modelList.add(model);
                            }

                            fetchapprovalAdaptor = new BrokerApprovalAdaptor(getApplicationContext(), modelList);
                            recyclerView.setAdapter(fetchapprovalAdaptor);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Sorry data couldn't load!",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                Map<String, String> params = new HashMap<>();

                Log.e("branch_code",str_from_branch);
                Log.e("emplid",str_empid);
                Log.e("key",key);
                Log.e("method",method);
                Log.e("vehicle_request_code",vehicle_req_code);

                params.put("branch_code",str_from_branch);
                params.put("emplid",str_empid);
                params.put("key",key);
                params.put("method",method);
                params.put("vehicle_request_code",vehicle_req_code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void init() {
        edt_to_branch = findViewById(R.id.edt_to_branch);
        edt_loading_point = findViewById(R.id.edt_loading_point);
        edt_unloading_point = findViewById(R.id.edt_unloading_point);
        spinner_req_type = findViewById(R.id.spinner_req_type);
        spinner_goods_type = findViewById(R.id.spinner_goods_type);
        edt_actual_wt_of_goods = findViewById(R.id.edt_actual_wt_of_goods);
        spinner_vehicle_type = findViewById(R.id.spinner_vehicle_type);

        broker1_rate_details = findViewById(R.id.broker1_rate_details);
        broker2_rate_details = findViewById(R.id.broker2_rate_details);
        broker3_rate_details = findViewById(R.id.broker3_rate_details);

        edt_vehicle_req_code = findViewById(R.id.edt_vehicle_req_code);
        edt_vehicle_req_date = findViewById(R.id.edt_vehicle_req_date);
        edt_from_branch = findViewById(R.id.edt_from_branch);

        btn_broker_selection1 = findViewById(R.id.btn_broker_selection);

        spinner_broker_selection1 = findViewById(R.id.spinner_broker_selection);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                approved();
//                Log.e("req_code:",BROKER_CODE);
//                Log.e(CONSTANT.METHOD,"approved");
//                Log.e(CONSTANT.KEY,"dfgagsdgsdg345dfsd");
//                Log.e("Borker_Nmae:", BROKER_NAME);
//                Log.e("approved_status",str_approved_status);
                break;
        }
    }

    private void approved() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String approved_method = "approved_vehicle";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        List list = fetchapprovalAdaptor.clicked_details();
        if(!list.isEmpty()){
                BROKER_CODE = (String) list.get(0);
                BROKER_NAME = (String) list.get(1);
                BROKER_RATE = (String) list.get(2);
                SELECTION_STATUS = (String) list.get(3);
        }
        Log.e("List:", String.valueOf(list));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPROVED_URL, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Approved Response>",response);
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
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD,approved_method);
                params.put(CONSTANT.KEY,key);
                params.put("request_code",str_vehicle_req_code);
                params.put("broker_name", BROKER_NAME);
                params.put("broker_code",BROKER_CODE);
                params.put("broker_rate",BROKER_RATE);
                params.put("approved_status",SELECTION_STATUS);
                params.put("from_branch",str_from_branch);
                params.put("to_branch", str_to_branch);
                params.put("loading_point",str_loading_point);
                params.put("unloading_point",str_unloading_point);
                params.put("requirement_type", str_req_type);
                params.put("actual_wt_of_goods",str_act_wt_of_goods);

                params.put(CONSTANT.EMPID,str_empid);
                params.put(CONSTANT.BRANCH_CODE,str_from_branch);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
