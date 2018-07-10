package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.example.it2.axpresslogisticapp.acitvities.ApiKey;


import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String url = "http://webapi.axpresslogistics.com/api/HRMS/Get_Login";
    EditText employee_code, password;
    Button login_button;
    TextView forgetPassword;
    String employeeCodeValue, passwordValue, username;
    Bundle bundle;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //find the value from xml files..
        employee_code = findViewById(R.id.input_employee_id);
        password = findViewById(R.id.input_password_id);
        forgetPassword = findViewById(R.id.forget_password_linkId);
        progressBar = findViewById(R.id.progressBarId);
        login_button = findViewById(R.id.btn_login);
        //clickable events...
        login_button.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //set click event on login button, check condition and redirect it...
            case R.id.btn_login:
                hide_keyboard();
                if (employee_code.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the Employee ID.",
                            Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the Password",
                            Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
                break;
            //set click event on forget link for redirection on forget page activity...
            case R.id.forget_password_linkId:
                forgetPassword();
                break;
        }
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        login_button.setClickable(false);
        ApiKey apiKey = new ApiKey();
        final String method = "login";
        final String apikey = apiKey.saltStr();
        Log.d("apikey : ",apikey);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("Status");
                    String apiKEYresponse = object.optString("key");
                    Log.e("status : ", status);
                    Log.e("my apikey : ", apikey);
                    Log.e("response apikey : ",apiKEYresponse );

                    if (status.equals("true")&& apikey.equals(apiKEYresponse)) {
                        username = object.optString("Employee_Name");
                        Toast.makeText(getApplicationContext(), "Welcome " + username, LENGTH_SHORT).show();
                        Intent logindataIntent = new Intent(getApplicationContext(), MainHomeActivity.class);
                        logindataIntent.putExtra("response", response.toString());
                        startActivity(logindataIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), status+"wrong credential.. ", Toast.LENGTH_LONG).show();
                        invisibleProgressbar();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======",""+error.toString());
                if(error.toString().equals("com.android.volley.ServerError")){
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500", Toast.LENGTH_LONG).show();
                    invisibleProgressbar();
                } else{
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    invisibleProgressbar();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", employee_code.getText().toString().trim());
                params.put("pwd", password.getText().toString().trim());
                params.put("method", method);
                params.put("key", apikey.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //hide softkeyboard from screen..
    private void hide_keyboard() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //Function of forgetPassword....
    private void forgetPassword() {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        invisibleProgressbar();
        clearText();

    }

    private void invisibleProgressbar() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        login_button.setClickable(true);
    }

    private  void clearText(){
        employee_code.setText("");
        password.setText("");
    }


}
