package com.example.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.Utilities.Preferences;


import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,LocationListener {
    private String url = "http://webapi.axpresslogistics.com/api/HRMS/Get_Login";
    EditText employee_code, password;
    Button login_button;
    TextView forgetPassword;
    String employeeCodeValue, passwordValue, username;
    Bundle bundle;
    ProgressBar progressBar;
    LocationManager locationManager;
    double lat, lon;

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
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        getLocationPermissionCheck();
        if (Preferences.getPreference(LoginActivity.this,"APIKEY")!=""){

            //username = object.optString("Employee_Name");
            Toast.makeText(getApplicationContext(), "Welcome " + Preferences.getPreference(LoginActivity.this,CONSTANT.USER_NAME), LENGTH_SHORT).show();
            Intent logindataIntent = new Intent(getApplicationContext(), MainHomeActivity.class);
          //  logindataIntent.putExtra("response", response.toString());
            startActivity(logindataIntent);

        }

    }

    private void getLocationPermissionCheck() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(getApplicationContext(), Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.
                            ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        }
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
                    Preferences.setPreference(LoginActivity.this,"APIKEY",apiKEYresponse);
                    Log.e("status : ", status);
                    Log.e("my apikey : ", apikey);
                    Log.e("response  : ",response.toString() );

                    if (status.equals("true")&& apikey.equals(apiKEYresponse)) {
                        username = object.optString("Employee_Name");
                        Preferences.setPreference(LoginActivity.this, CONSTANT.USER_NAME,object.optString("Employee_Name"));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMAIL,object.optString("Employee_Email"));
                        Toast.makeText(getApplicationContext(), "Welcome " + username, LENGTH_SHORT).show();
                        Intent logindataIntent = new Intent(getApplicationContext(), MainHomeActivity.class);
                      //  logindataIntent.putExtra("response", response.toString());
                        Preferences.setPreference(LoginActivity.this,"response",response.toString());

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


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
