package com.example.it2.axpresslogisticapp.acitvities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.adaptor.InvoiceDocketAdaptor;
import com.example.it2.axpresslogisticapp.model.InvoiceDocketModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceDocketActivityList extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    InvoiceDocketAdaptor invoiceDocketAdaptor;
    List<InvoiceDocketModel> invoiceDocketModelList;
    String DOCKET_LIST_URL = CONSTANT.URL + "Operations/invoice_details", invoice_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_docket_list);
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

       invoice_no = getIntent().getStringExtra("invoice_no");

        recyclerView = findViewById(R.id.customerRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        invoiceDocketModelList = new ArrayList<>();
        showDocketList();


    }

    private void showDocketList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "saved_visit_form";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOCKET_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    Log.e("Response Invoice",response);
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
                params.put(CONSTANT.INVOICE_NO,invoice_no);
                return super.getParams();
            }
        };
    }
}
