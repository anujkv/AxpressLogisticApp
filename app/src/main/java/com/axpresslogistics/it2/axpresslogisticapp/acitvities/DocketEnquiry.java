package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.InvoiceDocketAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.InvoiceDocketModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class DocketEnquiry extends AppCompatActivity {
    private String url = URL + "Operations/Docket_Invoice";
    private String DOCKET_LIST_URL  = URL +"Operations/invoice_details";
    Boolean FLAG = false;
    RadioGroup radio_group_id;
    RadioButton radio_btn_id;
    EditText input_editSearch_text_id;
    Button submit_docket_btn;
    String method, strInput_editSearch_text,docket;
    int intInput_editSearch_text;
    Intent intent;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    InvoiceDocketAdaptor invoiceDocketAdaptor;
    List<InvoiceDocketModel> invoiceDocketModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_enquiry);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        progressBar = findViewById(R.id.progressBar);
        lable.setText(CONSTANT.DOCKET_INVOICE);
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

        submit_docket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_group_id.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radio_btn_id = findViewById(selectedId);
                strInput_editSearch_text = input_editSearch_text_id.getText().toString().trim();

                if (radio_btn_id==null) {
                    Toast.makeText(getApplicationContext(), CONSTANT.CHOOSE_THE_SEARCH_TYPE,
                            Toast.LENGTH_SHORT).show();

                } else if (strInput_editSearch_text.isEmpty() || strInput_editSearch_text.equals("")) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_DOCKET_INVOICE_NO,
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(radio_btn_id.getText().equals("Docket")){
                        if(strInput_editSearch_text.length()==7){
                            dataJsonFunction();
                        }else{
                            input_editSearch_text_id.setText("");
                            Toast.makeText(getApplicationContext(),"Docket no not found",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else if(radio_btn_id.getText().equals("Invoice")){
                        showDocketList();
                    }
                }
            }
        });
    }

    private void showDocketList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "invoice_details";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOCKET_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response Invoice",response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.optString(CONSTANT.STATUS);
                            if(status.equals(CONSTANT.TRUE)){
                                progressDialog.dismiss();
                                Intent intent = new Intent(DocketEnquiry.this,InvoiceDocketActivityList.class);
                                intent.putExtra("invoice_no",strInput_editSearch_text);
                                startActivity(intent);
                            }
                            else if(status.equals(CONSTANT.FALSE)){
                                Toast.makeText(getApplicationContext(),"Invoice No not found",
                                        Toast.LENGTH_SHORT).show();
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(CONSTANT.METHOD,CONSTANT.INVOICE_DETAILS_METHOD);
                params.put(CONSTANT.KEY,apikey);
                params.put(CONSTANT.INVOICE_NO,strInput_editSearch_text.trim());
                progressDialog.dismiss();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog.dismiss();
    }

    public void dataJsonFunction() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        submit_docket_btn.setClickable(false);
//        final String method;
        String url = URL + "Operations/Docket_Invoice";
        ApiKey apiKey = new ApiKey();
        final String method = CONSTANT.DOCKET;
        final String apikey = apiKey.saltStr();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.
                Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString(CONSTANT.STATUS);
                    String apkKeyResponse = object.optString(CONSTANT.KEY);

                    if (status.equals(CONSTANT.TRUE)) {
                        Intent intent = new Intent(DocketEnquiry.this, DocketTracking.class);
                        intent.putExtra(CONSTANT.RESPNOSE, response);
                        startActivity(intent);
                    } else if (status.equals(CONSTANT.FALSE)){
                        Toast.makeText(getApplicationContext(),method + CONSTANT.NOT_FOUND,
                                Toast.LENGTH_SHORT).show();
                        submit_docket_btn.setClickable(true);
                        progressDialog.show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),CONSTANT.SOMETHING_WENT_WRONG,
                                Toast.LENGTH_SHORT).show();
                        submit_docket_btn.setClickable(true);
                        progressDialog.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error.toString());
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
                submit_docket_btn.setClickable(true);
                progressDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.DOCKET_NO, strInput_editSearch_text);
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, apikey);
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
