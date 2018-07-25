package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;

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
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_enquiry);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        progressBar = findViewById(R.id.progressBar);
        lable.setText("Docket/Invoice Enquiry");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //initialize_data_types..
        initialize_dataType();
        radio_group_id.clearCheck();
        radio_group_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radio_btn_id = group.findViewById(checkedId);
                if (radio_btn_id != null) {
                    method = radio_btn_id.toString();
                } else {
                    Toast.makeText(getApplicationContext(), "Choose the search type.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit_docket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInput_editSearch_text = input_editSearch_text_id.getText().toString().trim();

                if (radio_btn_id==null) {
                    Toast.makeText(getApplicationContext(), "Choose the search type.",
                            Toast.LENGTH_SHORT).show();

                } else if (strInput_editSearch_text.isEmpty() || strInput_editSearch_text == null) {
                    Toast.makeText(getApplicationContext(), "Enter the Docket/Invoice No.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dataJsonFunction();
                }
            }
        });
    }

    private void dataJsonFunction() {
        progressBar.setVisibility(View.VISIBLE);
        submit_docket_btn.setClickable(false);
//        final String method;
        ApiKey apiKey = new ApiKey();
        final String method = "docket";
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.
                Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("status");
                    String apkKeyResponse = object.optString("key");

                    if (status.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(), DocketTracking.class);
                        intent.putExtra("response", response.toString());
                        startActivity(intent);
                    } else if (status.equals("false")){
                        Toast.makeText(getApplicationContext(),method + " not found!",
                                Toast.LENGTH_SHORT).show();
                        submit_docket_btn.setClickable(true);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"something went wrong!",
                                Toast.LENGTH_SHORT).show();
                        submit_docket_btn.setClickable(true);
                        progressBar.setVisibility(View.INVISIBLE);
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
                    Toast.makeText(getApplicationContext(), CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                    submit_docket_btn.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (error.toString().equals("com.android.volley.NoConnectionError")){
                    Toast.makeText(getApplicationContext(), CONSTANT.INTERNETERROR, Toast.LENGTH_LONG).show();
                    submit_docket_btn.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    submit_docket_btn.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("docket_no", strInput_editSearch_text);
                params.put("method", method);
                params.put("key", apikey);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initialize_dataType() {
        radio_group_id = findViewById(R.id.radio_group_id);
        input_editSearch_text_id = findViewById((R.id.input_editSearch_text_id));
        submit_docket_btn = findViewById(R.id.btn_search);
    }

    @Override
    protected void onPostResume() {
        submit_docket_btn.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
        super.onPostResume();
    }
}
