package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.ListOfBrokerAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.ListOfBrokersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVehicleReq extends AppCompatActivity implements View.OnClickListener {
    String ADD_VEHICLE_URL = CONSTANT.DEVELOPMENT_URL + "Operations/market_vehicle_entry";
    String BRANCH_LIST_URL = CONSTANT.DEVELOPMENT_URL + "Operations/branch";
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_from_branch, edt_loading_point, edt_unloading_point, edt_actual_wt_of_goods,
            edt_vehicle_capacity,edt_broker_rate1,edt_broker_rate2,edt_broker_rate3,edt_advance1,edt_advance2,
            edt_advance3,edt_remark1,edt_remark2,edt_remark3;
    Spinner spinner_to_branch,spinner_req_type, spinner_touching_point, spinner_goods_type, spinner_vehicle_type,
            spinner_broker_selection1, spinner_broker_selection2, spinner_broker_selection3;
    String str_vehicle_req_code, str_from_branch, str_to_branch, str_loading_point, str_unloading_point,
            str_actual_wt_of_goods, str_vehicle_capacity, str_req_type, str_touching_point,
            str_goods_type, str_vehicle_type, str_broker_selection1, str_broker_selection2,
            str_broker_selection3;
    String method,emp_id,branch_code;
    CardView broker1_rate_details,broker2_rate_details,broker3_rate_details;
    Boolean FLAG_DROP_DOWN = false, FLAG_DROP_DOWN2 = false, FLAG_DROP_DOWN3 = false;
    TextView show_more_click_link,show_more_click_link2,show_more_click_link3;
    String CALL_BROKER_LIST = CONSTANT.DEVELOPMENT_URL + "Operations/brokercalllist";
    List<ListOfBrokersModel> brokersModelList;
    ListOfBrokerAdaptor listOfBrokerAdaptor;
    ArrayList<String> to_branch_name_list;
    RecyclerView recyclerView;
    JSONArray array;
    List<String> list;
    List<String> brokerlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_req);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.ADD_VEHICLE);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        init();
        load_branch_list();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        brokersModelList = new ArrayList<>();


        emp_id = Preferences.getPreference(AddVehicleReq.this,CONSTANT.EMPID);
        str_from_branch = Preferences.getPreference(AddVehicleReq.this,CONSTANT.EMPLOYEE_BRANCH);
        edt_from_branch.setText(str_from_branch.trim());
        call_broker_list();
//        ArrayAdapter<ListOfBrokersModel> adapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_spinner_item, brokersModelList);
//
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_broker_selection1.setAdapter(adapter);
        spinner_broker_selection1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                if (position > 0) {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                    Pattern mPattern = Pattern.compile("\\d+");
                    Matcher matcher = mPattern.matcher(str_vehicle_type);
                    if (matcher.find()) {
                        edt_vehicle_capacity.setText(matcher.group());
                        str_vehicle_capacity = edt_vehicle_capacity.getText().toString().trim();
                    }
                }else {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_broker_selection1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to_branch.setAdapter(dataAdapter);
        spinner_to_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_to_branch = spinner_to_branch.getSelectedItem().toString();
                if (position > 0) {
                    str_to_branch = parent.getItemAtPosition(position).toString();
                    Pattern mPattern = Pattern.compile("(\\w+)\\s\\-");
                    Matcher matcher = mPattern.matcher(str_to_branch);
                    if (matcher.find()) {
                        branch_code = matcher.group().trim();
                    }
                }else {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void load_branch_list() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method_branch = "to_branch_list";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BRANCH_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("to_branch_list",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String keyResponse = object.getString(CONSTANT.KEY);
                            if(status.equals(CONSTANT.TRUE)){
                                array = object.getJSONArray("brnsearch");
                                for (int i =0;i<array.length();i++){
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String branch =jsonObject.getString("branch_name");
                                    list.add(branch);
                                }
                            }
                            spinner_to_branch.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this,
                                    android.R.layout.simple_spinner_dropdown_item, list));
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
                params.put(CONSTANT.METHOD,method_branch);
                params.put(CONSTANT.KEY,key);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void call_broker_list()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        method = "call_broker_list";
        final ApiKey apiKey = new ApiKey();
        final String key = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CALL_BROKER_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("call_broker_list",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(CONSTANT.STATUS);
                            String keyResponse = object.getString(CONSTANT.KEY);
                            if(status.equals(CONSTANT.TRUE)){
                                array = object.getJSONArray("searchbroker");
                                for (int i =0;i<array.length();i++){
//                                    JSONObject jsonObjectName =  array.getJSONObject(i);
//                                    String broker_name =jsonObjectName.getString("broker_name");
//                                    brokerlist.add(broker_name);
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    ListOfBrokersModel brokersModel = new ListOfBrokersModel(
                                            jsonObject.getString("broker_name"),
                                            jsonObject.getString("broker_code"),
                                            jsonObject.getString("contact_no"),
                                            jsonObject.getString("address"),
                                            jsonObject.getString("account_no"),
                                            jsonObject.getString("bank_name"),
                                            jsonObject.getString("pan_no"),
                                            jsonObject.getString("name_on_pan_card"),
                                            jsonObject.getString("ifsc_code"));
                                    brokersModelList.add(brokersModel);
                                }
//                                spinner_broker_selection1.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this, android.R.layout.simple_spinner_dropdown_item, brokerlist));
                                listOfBrokerAdaptor = new ListOfBrokerAdaptor(getApplicationContext(),brokersModelList);
                                recyclerView.setAdapter(listOfBrokerAdaptor);
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
                params.put(CONSTANT.METHOD,method);
                params.put(CONSTANT.KEY,key);
                params.put(CONSTANT.EMPID,emp_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void init() {
        edt_from_branch = findViewById(R.id.edt_from_branch);
        spinner_to_branch = findViewById(R.id.spinner_to_branch);
        edt_loading_point = findViewById(R.id.edt_loading_point);
        edt_unloading_point = findViewById(R.id.edt_unloading_point);
        edt_actual_wt_of_goods = findViewById(R.id.edt_actual_wt_of_goods);
        spinner_req_type = findViewById(R.id.spinner_req_type);
        spinner_touching_point = findViewById(R.id.spinner_touching_point);
        spinner_goods_type = findViewById(R.id.spinner_goods_type);
        spinner_vehicle_type = findViewById(R.id.spinner_vehicle_type);
        edt_vehicle_capacity = findViewById(R.id.edt_vehicle_capacity);
        spinner_broker_selection1 = findViewById(R.id.spinner_broker_selection1);
        spinner_broker_selection2 = findViewById(R.id.spinner_broker_selection2);
        spinner_broker_selection3 = findViewById(R.id.spinner_broker_selection3);

        show_more_click_link = findViewById(R.id.show_more_click_link);
        show_more_click_link.setOnClickListener(this);
        broker1_rate_details = findViewById(R.id.broker1_rate_details);

        show_more_click_link2 = findViewById(R.id.show_more_click_link2);
        show_more_click_link2.setOnClickListener(this);
        broker2_rate_details = findViewById(R.id.broker2_rate_details);

        show_more_click_link3 = findViewById(R.id.show_more_click_link3);
        show_more_click_link3.setOnClickListener(this);
        broker3_rate_details = findViewById(R.id.broker3_rate_details);
        recyclerView = findViewById(R.id.recyclerview_brokerlist);

        edt_broker_rate1 = findViewById(R.id.edt_broker_rate1);
        edt_broker_rate2 = findViewById(R.id.edt_broker_rate2);
        edt_broker_rate3 = findViewById(R.id.edt_broker_rate3);

        edt_advance1 = findViewById(R.id.edt_advance1);
        edt_advance2 = findViewById(R.id.edt_advance2);
        edt_advance3 = findViewById(R.id.edt_advance3);

        edt_remark1 = findViewById(R.id.edt_remark1);
        edt_remark2 = findViewById(R.id.edt_remark2);
        edt_remark3 = findViewById(R.id.edt_remark3);
    }

    private void getdata() {
        if (str_req_type.equals("Select")) {
//            str_to_branch = spinner_to_branch.getText().toString().trim();
        }
        str_loading_point = edt_loading_point.getText().toString().trim();
        str_unloading_point = edt_unloading_point.getText().toString().trim();
        str_actual_wt_of_goods = edt_actual_wt_of_goods.getText().toString().trim();
        str_req_type = spinner_req_type.getSelectedItem().toString().trim();
        if (str_req_type.equals("Select")) {
            Toast.makeText(getApplicationContext(),"Kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }
        str_touching_point = spinner_touching_point.getSelectedItem().toString().trim();
        if (str_touching_point.equals("Select")) {
            Toast.makeText(getApplicationContext(),"Kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }
        str_goods_type = spinner_goods_type.getSelectedItem().toString().trim();
        if (str_goods_type.equals("Select")) {
//            //TODO if user not select any list of item,do something
            Toast.makeText(getApplicationContext(),"kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                getdata();
//                mandatoryCheck();
                save();
//                add_function_api_call();
                break;
            case R.id.show_more_click_link:
                if(FLAG_DROP_DOWN.equals(false)){
                    broker1_rate_details.setVisibility(View.VISIBLE);
                    show_more_click_link.setText(CONSTANT.SHOW_LESS);
                    FLAG_DROP_DOWN = true;
                }else{
                    broker1_rate_details.setVisibility(View.GONE);
                    show_more_click_link.setText(CONSTANT.SHOW_MORE);
                    FLAG_DROP_DOWN = false;
                }
                break;
            case R.id.show_more_click_link2:
                if(FLAG_DROP_DOWN2.equals(false)){
                    broker2_rate_details.setVisibility(View.VISIBLE);
                    show_more_click_link2.setText(CONSTANT.SHOW_LESS);
                    FLAG_DROP_DOWN2 = true;
                }else{
                    broker2_rate_details.setVisibility(View.GONE);
                    show_more_click_link2.setText(CONSTANT.SHOW_MORE);
                    FLAG_DROP_DOWN2 = false;
                }
                break;
            case R.id.show_more_click_link3:
                if(FLAG_DROP_DOWN3.equals(false)){
                    broker3_rate_details.setVisibility(View.VISIBLE);
                    show_more_click_link3.setText(CONSTANT.SHOW_LESS);
                    FLAG_DROP_DOWN3 = true;
                }else{
                    broker3_rate_details.setVisibility(View.GONE);
                    show_more_click_link3.setText(CONSTANT.SHOW_MORE);
                    FLAG_DROP_DOWN3 = false;
                }
                break;
        }
    }

    private void save() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "add_vehicle_req";
        final String key = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_VEHICLE_URL,
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
                                Toast.makeText(getApplicationContext(),CONSTANT.ADD_VEHICLE_SUCCESSFULLY,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }else if(status.equals(CONSTANT.already_exist)){
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
                Log.d("response======", "" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.SERVER_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getBaseContext(),
                        CONSTANT.AUTH_FAILURE_ERROR,
                        Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.PARSE_ERROR,
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
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, key);
                params.put(CONSTANT.EMPID, emp_id);
                params.put("from_branch",str_from_branch);
                params.put("to_branch",branch_code);
//                params.put("to_branch","dhrt");

                params.put("loading_point",str_loading_point);
                params.put("unloading_point",str_unloading_point);
                params.put("req_type",str_req_type);
                params.put("touching_point",str_touching_point);
                params.put("good_type",str_goods_type);
                params.put("actual_wt_of_goods",str_actual_wt_of_goods);
                params.put("vehicle_type",str_vehicle_type);
                params.put("vehicle_capacity",str_vehicle_capacity);
                params.put("market_vehicle_entry_status","pending");
//                params.put("broker_name1",str_broker_selection1);
//                params.put("broker_code1",str_broker_selection1);
                params.put("broker1_contact_no","987654212");
                params.put("broker_1_rate",edt_broker_rate1.getText().toString().trim());
                params.put("broker_1_advance",edt_advance1.getText().toString().trim());
                params.put("broker_1_remark",edt_remark1.getText().toString().trim());
//                params.put("broker_name1",str_broker_selection2);
//                params.put("broker_code1",str_broker_selection2);
                params.put("broker2_contact_no","987654212");
                params.put("broker_2_rate",edt_broker_rate2.getText().toString().trim());
                params.put("broker_2_advance",edt_advance2.getText().toString().trim());
                params.put("broker_2_remark",edt_remark2.getText().toString().trim());
//                params.put("broker_name1",str_broker_selection3);
//                params.put("broker_code1",str_broker_selection3);
                params.put("broker3_contact_no","987654212");
//                params.put("broker_3_rate",edt_broker_rate2.getText().toString().trim());
//                params.put("broker_3_advance",edt_advance2.getText().toString().trim());
//                params.put("broker_3_remark",edt_remark3.getText().toString().trim());
//                params.put("req_type","FTL");
//                params.put("touching_point","0");
//                params.put("good_type","Heavy Weight");
//                params.put("actual_wt_of_goods","100");
//                params.put("vehicle_type","500-Volvo");
//                params.put("vehicle_capacity","500");
//                params.put("market_vehicle_entry_status","pending");
                params.put("broker_name1","Rajnikant");
//                params.put("broker1_contact_no","987654212");
                params.put("broker_code1","100");
//                params.put("broker_1_rate","5000");
//                params.put("broker_1_advance","1000");
//                params.put("broker_1_remark","next month will pay");
                params.put("broker_name2","Lokesh");
//                params.put("broker2_contact_no","987654812");
                params.put("broker_code2","110");
//                params.put("broker_2_rate","5000");
//                params.put("broker_2_advance","0");
//                params.put("broker_2_remark","");
                params.put("broker_name3","");
//                params.put("broker3_contact_no","");
                params.put("broker_code3","");
                params.put("broker_3_rate","");
                params.put("broker_3_advance","");
                params.put("broker_3_remark","");
                Log.e("method","add_vehicle_req");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void mandatoryCheck() {

    }
}
