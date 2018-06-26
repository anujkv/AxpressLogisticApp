package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    String url = "http://webapi.axpresslogistics.com/api/HRMS/Reset_Pass";
    EditText editTextResetEmail, editTextResetID, editTextDOBID;
    TextView textViewBackLinkId, textViewSubmitLinkId;
    String strEmail, strEmpId, strDOBID;
    Intent intent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        editTextResetEmail = findViewById(R.id.editTextResetEmail);
        editTextResetID = findViewById(R.id.editTextResetID);
//        editTextDOBID = findViewById(R.id.editTextDOBID);
        textViewBackLinkId = findViewById(R.id.textViewBackLinkId);
        textViewSubmitLinkId = findViewById(R.id.textViewSubmitLinkId);
        progressBar = findViewById(R.id.progressBar);
        editTextResetID.setFocusable(true);

        textViewBackLinkId.setOnClickListener(this);
        textViewSubmitLinkId.setOnClickListener(this);

    }


    private void resetPassword() {
        ApiKey apiKey = new ApiKey();
        final String method = "reset";
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("Status");
                    String apiKEYresponse = object.optString("key");
                    Log.d("Response>>>", status);

                    if (status.equals("true")) {
                        Toast.makeText(getApplicationContext(), "Password is default, same as Employee ID!.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "credential not match", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", strEmpId);
                params.put("employee_email", strEmail);
                params.put("employee_dob", "");
                params.put("method", method);
                params.put("key", apikey.trim());
                Log.d("employee_id", strEmpId);
                Log.d("employee_email", strEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewSubmitLinkId:
                //Check Emp ID is empty or not...
                if (editTextResetID.getText().toString().trim().isEmpty() &&
                        editTextResetID.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the employee Id!",
                            LENGTH_SHORT).show();
                }
                else {
                    strEmpId = editTextResetID.getText().toString().trim();
                    if (editTextResetEmail.getText().toString().trim().isEmpty() &&
                            editTextResetEmail.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter the email Id!",
                                LENGTH_SHORT).show();
                    } else {
                        strEmail = editTextResetEmail.getText().toString().trim();
                        progressBar.setVisibility(View.VISIBLE);
                        resetPassword();

                    }

                }
                break;

            case R.id.textViewBackLinkId:
                finish();
                break;
        }
    }
}
