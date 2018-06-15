package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DocketEnquiry extends AppCompatActivity {
    private String url = "http://webapi.axpresslogistics.com/api/Operations/Docket_Invoice";
    Boolean FLAG = false;
    RadioGroup radio_group_id;
    RadioButton radio_btn_id;
    EditText input_editSearch_text_id;
    Button submit_docket_btn;
    String method, strInput_editSearch_text;
    int intInput_editSearch_text;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_enquiry);

        //initialize_data_types..
        initialize_dataType();
        radio_group_id.clearCheck();

        submit_docket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInput_editSearch_text = input_editSearch_text_id.getText().toString().trim();
                radio_group_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radio_btn_id = group.findViewById(checkedId);
                        if (radio_btn_id != null) {
                            method = radio_btn_id.toString();
                        } else {
                            Toast.makeText(getApplicationContext(), "Choose the search type.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if (radio_btn_id==null) {
                    Toast.makeText(getApplicationContext(), "Choose the search type.", Toast.LENGTH_SHORT).show();

                } else if (strInput_editSearch_text.isEmpty() || strInput_editSearch_text == null) {
                    Toast.makeText(getApplicationContext(), "Enter the Docket/Invoice No.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), strInput_editSearch_text, Toast.LENGTH_SHORT).show();
                    dataJsonFunction();
                }
            }
        });
    }

    private void dataJsonFunction() {
        submit_docket_btn.setClickable(false);
//        final String method;
        final String method = "docket";
        final String apikey = saltStr();
        Log.d("apikey : ", apikey);
        Log.d("method : ", method);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("responseStart : ", response.toString());
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("status");
                    String apkKeyResponse = object.optString("key");
                    Log.d("Status : ",status);
                    Log.d("key : ",apkKeyResponse);

                    if (status.equals("true")) {
                        Log.d("======================",status);
                        Intent intent = new Intent(getApplicationContext(), DocketTracking.class);
                        intent.putExtra("response", response.toString());
                        startActivity(intent);
                    } else if (status.equals("false")){
                        Toast.makeText(getApplicationContext(),method + " not found!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error.toString());
                if(error.toString().equals("com.android.volley.ServerError")){
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("docket_no", strInput_editSearch_text);
                params.put("method", method);
                params.put("key", apikey);
//                params.put("docket_no","4422791");
//                params.put("method","docket");
//                params.put("key","BGOOLTRECUOUQS1NJA");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String saltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void initialize_dataType() {
        radio_group_id = findViewById(R.id.radio_group_id);
        input_editSearch_text_id = findViewById((R.id.input_editSearch_text_id));
        submit_docket_btn = findViewById(R.id.btn_search);
    }

    @Override
    protected void onPostResume() {
        submit_docket_btn.setClickable(true);
        super.onPostResume();
    }
}
