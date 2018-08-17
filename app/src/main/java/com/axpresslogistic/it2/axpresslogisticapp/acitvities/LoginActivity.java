package com.axpresslogistic.it2.axpresslogisticapp.acitvities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.axpresslogistic.it2.axpresslogisticapp.R;
import com.axpresslogistic.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistic.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.axpresslogistic.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    EditText employee_code, password;
    Button login_button;
    TextView forgetPassword;
    String username;
    Bundle bundle;
    ProgressBar progressBar;
    LocationManager locationManager;
    double lat, lon;
    private String url = URL + "HRMS/Get_Login";

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                    5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        getLocationPermissionCheck();
        if (!Preferences.getPreference(LoginActivity.this, CONSTANT.APIKEY).equals(CONSTANT.BLANK)) {
            Toast.makeText(getApplicationContext(), CONSTANT.WELCOME
                            + Preferences.getPreference(LoginActivity.this, CONSTANT.USER_NAME),
                    LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
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
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_EMPLOYEE_ID,
                            Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_PASSWORD,
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
        final String method = CONSTANT.LOGIN_METHOD;
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Login Response : ",response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.optString(CONSTANT.STATUS);
                    String apiKEYresponse = object.optString(CONSTANT.KEY);
                    Preferences.setPreference(LoginActivity.this, CONSTANT.APIKEY, apiKEYresponse);

                    if (status.equals(CONSTANT.TRUE) && apikey.equals(apiKEYresponse)) {
                        username = object.optString(CONSTANT.EMPLOYEE_NAME);
                        Preferences.setPreference(LoginActivity.this, CONSTANT.USER_NAME,
                                object.optString(CONSTANT.EMPLOYEE_NAME));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMPID,
                                object.optString("Emplid"));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMAIL,
                                object.optString(CONSTANT.EMAIL));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_BRANCH,
                                object.optString(CONSTANT.EMPLOYEE_BRANCH));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_DESIGNATION,
                                object.optString(CONSTANT.EMPLOYEE_DESIGNATION));
                        Preferences.setPreference(LoginActivity.this, CONSTANT.EMPLOYEE_DEPT,
                                object.optString(CONSTANT.EMPLOYEE_DEPT));
                        Toast.makeText(getApplicationContext(), CONSTANT.WELCOME + username,
                                LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), CONSTANT.WRONG_CREDENTIAL,
                                Toast.LENGTH_LONG).show();
                        invisibleProgressbar();
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
                invisibleProgressbar();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", employee_code.getText().toString().trim());
                params.put("pwd", password.getText().toString().trim());
                params.put(CONSTANT.METHOD, method);
                params.put(CONSTANT.KEY, apikey.trim());
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

    private void clearText() {
        employee_code.setText(CONSTANT.BLANK);
        password.setText(CONSTANT.BLANK);
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
