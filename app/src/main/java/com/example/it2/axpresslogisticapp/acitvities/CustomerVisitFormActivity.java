package com.example.it2.axpresslogisticapp.acitvities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import static android.icu.util.Calendar.getInstance;

public class CustomerVisitFormActivity extends AppCompatActivity implements View.OnClickListener {
    String url = "";
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_search_panal, edt_customer_name, edt_visitdate, edtContactPerson, edtContactNo, edtEmail, edtAddress,
            edt_product_name, edtRemark, edt_other_employee_name;
    String str_search, str_customer_name, str_visitdate, strContactPerson, strContactNo, strEmail, strAddress,
            str_product_name, strStatus, strRemark, str_other_employee_name, str_visit_for,
            str_visit_type, str_scope;
    TextView add_new_card, add_exist_card;
    LinearLayout show_front_cardLayout, show_back_cardLayout, search_panalLayout;
    Spinner spinner_visit_for, spinner_visit_type, spinner_scope, spinner_status;
    CheckBox check_new, check_followUp;
    String saved = "Saved", notSaved = "data not saved", method;
    String compVisitID;
    EmpProfileActivity empProfileActivity;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, hh,mm,sec;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_form);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Customer Visit Form");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));

        init();
        getdata();
    }

    private void init() {
        search_panalLayout = findViewById(R.id.layout_search);
        edt_search_panal = findViewById(R.id.edt_search_panal);
        edt_customer_name = findViewById(R.id.edt_customer_name);
        edt_visitdate = findViewById(R.id.edtVisitDate);
        edt_visitdate.setOnClickListener(this);
        edtContactPerson = findViewById(R.id.edtContactPerson);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_product_name = findViewById(R.id.edt_product_name);
        edtRemark = findViewById(R.id.edtRemark);
        edt_other_employee_name = findViewById(R.id.edt_other_employee_name);

        add_new_card = findViewById(R.id.add_new_card);
        add_exist_card = findViewById(R.id.add_exist_card);

        show_front_cardLayout = findViewById(R.id.show_front_cardLayout);
        show_back_cardLayout = findViewById(R.id.show_back_cardLayout);
        show_front_cardLayout.setOnClickListener(this);
        show_back_cardLayout.setOnClickListener(this);

        spinner_visit_for = findViewById(R.id.spinner_visit_for);
        spinner_visit_type = findViewById(R.id.spinner_visit_type);
        spinner_scope = findViewById(R.id.spinner_scope);
        spinner_status = findViewById(R.id.spinner_status);

        check_new = findViewById(R.id.check_new);
        check_followUp = findViewById(R.id.check_followUp);
        check_new.setOnClickListener(this);
        check_followUp.setOnClickListener(this);
    }

    private void getdata() {
        str_customer_name = edt_customer_name.getText().toString().trim();
        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        strRemark = edtRemark.getText().toString().trim();
        str_other_employee_name = edt_other_employee_name.getText().toString().trim();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                save();
                break;
            case R.id.edtVisitDate:
                callDateDialogBox();
                break;
            case R.id.spinner_visit_for:
                spinner_visit_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_for = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_visit_type:
                spinner_visit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_type = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_scope:
                spinner_scope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_scope = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_status:
                spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        strStatus = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.check_new:
                Toast.makeText(getApplicationContext(), "new", Toast.LENGTH_SHORT).show();
                if (check_new.isChecked()) {
                    search_panalLayout.setVisibility(View.GONE);
                    check_followUp.setChecked(false);
                }
                break;
            case R.id.check_followUp:
                Toast.makeText(getApplicationContext(), "follow", Toast.LENGTH_SHORT).show();
                if (check_followUp.isChecked()) {
                    search_panalLayout.setVisibility(View.VISIBLE);
                    check_new.setChecked(false);
                    edt_search_panal.setFocusable(true);
                }
                break;

            case R.id.add_new_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.add_exist_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callDateDialogBox() {
        edt_visitdate = findViewById(R.id.edtVisitDate);
        calendar = getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(currentTime.getTime());
        showDate(formattedDate);


//        Date date = new Date();
//        String strDateFormat = "hh:mm:ss a";
//        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//        str_visitdate= dateFormat.format(date);
//        System.out.println("Current time of the day using Date - 12 hour format: " + str_visitdate);



    }

    private void showDate(String formattedDate) {
        edt_visitdate.setText(formattedDate);
    }

    private void showDate(int year, int i, int day, Date currentTime) {
        edt_visitdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day).append("  ").append(currentTime));
        str_visitdate = edt_visitdate.getText().toString().trim();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        edt_visitdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        str_visitdate = edt_visitdate.getText().toString().trim();
    }

    private void save() {
        if (check_new.isChecked() || check_followUp.isChecked()) {
            if (check_new.isChecked()) {
                compVisitID = uniqueVisitID();
                String businessType = "customer_visit_add";
                method = businessType;
                pushonDB(compVisitID);
//                pushonLocalDB();
                Toast.makeText(getApplicationContext(),
                        "Saved", Toast.LENGTH_SHORT).show();
            } else {
                String businessType = "customer_visit_follow";
                method = businessType;
                pushonDB(compVisitID);
//                pushonLocalDB();
                Toast.makeText(getApplicationContext(),
                        "Saved", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Kindly choose the business type new/follow",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void pushonLocalDB() {

    }

    private void pushonDB(String compVisitID) {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String compvisitID = compVisitID;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.optString("Status");
                    String apikeyResponse = object.optString("key");

                    if (status.equals("true") && apikeyResponse.equals(apikey)) {
                        Toast.makeText(getApplicationContext(), saved, Toast.LENGTH_SHORT).show();
                        //activity finished for return back to CustomervisitListActivity after saving details...
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), notSaved, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("empId",empProfileActivity.strEmpCode);
                params.put("uniqueVisitID", compvisitID);
                params.put("str_customer_name", str_customer_name);
                params.put("str_visitdate", str_visitdate);
                params.put("str_visit_for", str_visit_for);
                params.put("str_visit_type", str_visit_type);
                params.put("strContactPerson", strContactPerson);
                params.put("strContactNo", strContactNo);
                params.put("strEmail", strEmail);
                params.put("strAddress", strAddress);
                params.put("str_product_name", str_product_name);
                params.put("str_scope", str_scope);
                params.put("strStatus", strStatus);
                params.put("strRemark", strRemark);
                params.put("employee_id", str_other_employee_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String uniqueVisitID() {
        String comp = (String) str_customer_name.subSequence(0,2);
        String SALTCHARS = "1234567890";
        StringBuilder visitkey = new StringBuilder();
        Random rnd = new Random();
        while (visitkey.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            visitkey.append(SALTCHARS.charAt(index));
        }
        String visitStr = comp + visitkey.toString();
        return visitStr;

    }
}
