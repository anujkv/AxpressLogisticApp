package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddBroker.BrokerList;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.PODScan.PODScanner;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleApproval.VehicleSavedList;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VendorListActivity;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddVehicle.AddVehicleReq;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.ChallanReceiver.ChallanReciver;
import com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.VisitForm.CustomerViewListActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT.URL;

public class DBDialog extends AppCompatActivity {
    String GET_COM_URL = URL + "Operations/Get_Company?EMPLID=";
    String emplid;
    Button btn;
    String db_name;
    String[] newArray;
    Spinner spinner;
    int positionSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbdialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        btn = findViewById(R.id.db_btn);
        spinner = findViewById(R.id.spinner_db);
        Intent intent = getIntent();
        final String activity_name = intent.getStringExtra("activity_name");
//        Log.e("Activity", activity_name);

        showDBList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_name != null) {
                    if (activity_name.equals("BrokerList")) {
                        Log.e("Activity",activity_name);
                        Intent intentBL = new Intent(DBDialog.this, BrokerList.class);
                        intentBL.putExtra("db_name", db_name);
                        startActivity(intentBL);
                    } else if (activity_name.equals("AddVehicleReq")) {
                        Intent intentAVR = new Intent(DBDialog.this, AddVehicleReq.class);
                        intentAVR.putExtra("db_name", db_name);
                        startActivity(intentAVR);
                    } else if (activity_name.equals("MarketVehicleRequest")) {
                        Intent intentVR = new Intent(DBDialog.this, VehicleSavedList.class);
                        intentVR.putExtra("db_name", db_name);
                        startActivity(intentVR);
                    } else if (activity_name.equals("ChallanReciver")) {
                        Intent intentCR = new Intent(DBDialog.this, ChallanReciver.class);
                        intentCR.putExtra("db_name", db_name);
                        startActivity(intentCR);
                    } else if (activity_name.equals("CustomerViewListActivity")) {
                        Intent intentCVF = new Intent(DBDialog.this, CustomerViewListActivity.class);
                        intentCVF.putExtra("db_name", db_name);
                        startActivity(intentCVF);
                    }else if (activity_name.equals("VendorApproval")) {
                        Intent intentCVF = new Intent(DBDialog.this, VendorListActivity.class);
                        intentCVF.putExtra("db_name", db_name);
                        startActivity(intentCVF);
                    }else if (activity_name.equals("PODScan")) {
                        Intent intentPS = new Intent(DBDialog.this, PODScanner.class);
                        intentPS.putExtra("db_name", db_name);
                        startActivity(intentPS);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Kindly choose company",
                            Toast.LENGTH_SHORT).show();
                }
                Log.e("db_name", db_name);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().contains("Logistics")) {
                    db_name = "AxpressERP";
                } else if (spinner.getSelectedItem().toString().contains("SCM")) {
                    db_name = "ASCM";
                } else if (spinner.getSelectedItem().toString().contains("CARGO")) {
                    db_name = "AxpressCargo";
                } else {
                    db_name = spinner.getSelectedItem().toString();
                }
                Log.e("DB_NAME", db_name);
                Preferences.setPreference(DBDialog.this,CONSTANT.DB_NAME,db_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionSp = 0;
            }
        });
    }

    private void showDBList() {
        Log.e("URL", emplid);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Select DataBase");
        progressDialog.setMessage("Obtaining Database Details...Wait for moment!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GET_COM_URL = GET_COM_URL + emplid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_COM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Request", response);
                progressDialog.dismiss();
                String stringArray = response;
                newArray = stringArray.replace("[", "")
                        .replace("]", "")
                        .replace("\"", "")
                        .split(",");
                List<String> list = new ArrayList<String>();
                List<String> companey_list = new ArrayList<>();

                list.addAll(Arrays.asList(newArray));
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).toUpperCase().contains("ERP"))
                            companey_list.add("Axpress Logistics");
                        else if (list.get(i).toUpperCase().contains("SCM"))
                            companey_list.add("Axpress SCM");
                        else if (list.get(i).toUpperCase().contains("CARGO"))
                            companey_list.add("Axpress CARGO");
                        else {
                            companey_list.add(list.get(i));
                        }
                        for (int l = 0; l < companey_list.size(); l++) {
                            if (companey_list.get(l).equals("Axpress Logistics")) {
                                companey_list.remove(l);
                                companey_list.add(0, "Axpress Logistics");
                            }
                        }
                        Log.e("Index", new Gson().toJson(companey_list));

                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DBDialog.this,
                        android.R.layout.simple_spinner_item, companey_list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Log.e("NetworkError", CONSTANT.INTERNET_ERROR);
                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError", CONSTANT.TIMEOUT_ERROR);
                } else if (error instanceof ServerError) {
                    Log.e("ServerError", CONSTANT.RESPONSEERROR);
                } else if (error instanceof ParseError) {
                    Log.e("ParseError", CONSTANT.TIMEOUT_ERROR);
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
