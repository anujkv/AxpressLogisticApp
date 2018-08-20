package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    String url = URL + "HRMS/Reset_Pass";
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
        final String method = CONSTANT.RESET_PASSWORD_METHOD;
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("Status");
                    String apiKEYresponse = object.optString(CONSTANT.KEY);

                    if (status.equals(CONSTANT.TRUE)) {
                        Toast.makeText(getApplicationContext(), CONSTANT.PASSWORD_DEFAULT_SET,
                                Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), CONSTANT.WRONG_CREDENTIAL, Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

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
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.EMPLOYEE_ID, strEmpId);
                params.put(CONSTANT.EMPLOYEE_EMAIL, strEmail);
                params.put(CONSTANT.EMPLOYEE_DOB, CONSTANT.BLANK);
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, apikey.trim());
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
                        editTextResetID.getText().toString().trim().equals(CONSTANT.BLANK)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_EMPLOYEE_ID,
                            LENGTH_SHORT).show();
                } else {
                    strEmpId = editTextResetID.getText().toString().trim();
                    if (editTextResetEmail.getText().toString().trim().isEmpty() &&
                            editTextResetEmail.getText().toString().trim().equals(CONSTANT.BLANK)) {
                        Toast.makeText(getApplicationContext(), CONSTANT.ENTER_EMAIL_ID,
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
