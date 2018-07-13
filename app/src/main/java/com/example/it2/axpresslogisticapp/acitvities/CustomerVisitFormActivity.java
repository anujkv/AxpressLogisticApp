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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.icu.util.Calendar.getInstance;

public class CustomerVisitFormActivity extends AppCompatActivity implements View.OnClickListener {
    String URL_ADD_NEW = "http://webapi.axpresslogistics.com/api/Operations/customer_visit";
    String URL_FOLLOW = "http://webapi.axpresslogistics.com/api/Operations/customer_search";
    String URL_SEARCH = "https://api.myjson.com/bins/1b3nue";
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
    String businessType, compVisitID;


    EmpProfileActivity empProfileActivity;
//    String empid = empProfileActivity.strEmpCode;
    String empid = "1853";
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
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

    private void getdata(String compVisitID, String businessType) {
        method = businessType;
        this.compVisitID = compVisitID;
        str_customer_name = edt_customer_name.getText().toString().trim();
        str_visitdate = edt_visitdate.getText().toString().trim();
        str_visit_for = spinner_visit_for.getSelectedItem().toString();
        str_visit_type = spinner_visit_type.getSelectedItem().toString();
        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        strStatus = spinner_status.getSelectedItem().toString();
        str_scope = spinner_scope.getSelectedItem().toString();
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
                if (check_new.isChecked()) {
                    search_panalLayout.setVisibility(View.GONE);
                    check_followUp.setChecked(false);
                }
                break;
            case R.id.check_followUp:
                if (check_followUp.isChecked()) {
                    search_panalLayout.setVisibility(View.VISIBLE);
                    check_new.setChecked(false);
                    edt_search_panal.setFocusable(true);
                }
                break;
//            case R.id.edt_search_panal:
//                hitSearchAPi();
//                break;

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

    }

    private void showDate(String formattedDate) {
        edt_visitdate.setText(formattedDate);
    }

//    private void showDate(int year, int i, int day, Date currentTime) {
//        edt_visitdate.setText(new StringBuilder().append(year).append("-")
//                .append(month).append("-").append(day).append("  ").append(currentTime));
//        str_visitdate = edt_visitdate.getText().toString().trim();
//    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
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
//        str_visitdate = edt_visitdate.getText().toString().trim();
    }

    private void save() {
        if (check_new.isChecked() || check_followUp.isChecked()) {
            if (check_new.isChecked()) {
                compVisitID = uniqueVisitID();
                businessType = "customer_visit_add";
                pushonDBNew(compVisitID,businessType);
            } else if(check_followUp.isChecked()) {
                businessType = "customer_visit_follow";
                pushonDBNew(compVisitID,businessType);
            }
            getdata(compVisitID,businessType);

        } else {
            Toast.makeText(getApplicationContext(), "Kindly choose the business type new/follow",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void hitSearchAPi() {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object =  new JSONObject(response);
                            JSONArray array = object.getJSONArray("search_list");

                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
//                                jsonObject.getString()
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getApplicationContext(), "Unexpected response code: 404/500",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey.trim());
                params.put("input",str_search);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void pushonDBFollow(String compVisitID) {

    }

    private void pushonDBNew(String compVisitID, String businessType) {
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String compvisitID = compVisitID;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_NEW,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!= null && response.length() >0){
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.optString("status");
                        String apiKeyResponse = object.optString("key");

                        if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                            Toast.makeText(getApplicationContext(), saved, Toast.LENGTH_SHORT).show();
                            //activity finished for return back to CustomervisitListActivity after saving details...
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), notSaved, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "<<<<<"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "<<<<Response: "+response.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("<<<<Response: ",response.toString());
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
                    Toast.makeText(getApplicationContext(), "====="+error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("method", method);
                Log.e("key", apikey);
                Log.e("emplid",empid);
                Log.e("uniqueVisitID", compvisitID);
                Log.e("customer", str_customer_name);
                Log.e("visit_date", str_visitdate);
                Log.e("visit_for", str_visit_for);
                Log.e("visit_type", str_visit_type);
                Log.e("contact_person", strContactPerson);
                Log.e("contact", strContactNo);
                Log.e("email_id", strEmail);
                Log.e("address", strAddress);
                Log.e("product", str_product_name);
                Log.e("scope", str_scope);
                Log.e("status", strStatus);
                Log.e("remark", strRemark);
                Log.e("other_employee_name", str_other_employee_name);

                params.put("method", method);
                params.put("key",apikey);
                params.put("emplid",empid);
                params.put("uniqueVisitID", "asd234234");
                params.put("customer", str_customer_name);
                params.put("visit_date",str_visitdate);
                params.put("visit_for", str_visit_for);
                params.put("visit_type", str_visit_type);
                params.put("contact_person", strContactPerson);
                params.put("contact",strContactNo);
                params.put("email_id", strEmail);
                params.put("address", strAddress);
                params.put("product", str_product_name);
                params.put("scope", str_scope);
                params.put("status", strStatus);
                params.put("remark", strRemark);
                params.put("other_employee_name",str_other_employee_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String uniqueVisitID() {
//        String comp = str_customer_name.substring(0,2);
//        Log.e("substring company :",comp);
        String SALTCHARS = "1234567890";
        StringBuilder visitkey = new StringBuilder();
        Random rnd = new Random();
        while (visitkey.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            visitkey.append(SALTCHARS.charAt(index));
        }
//        String visitStr = comp + visitkey.toString();
        String visitStr = visitkey.toString();
        Log.e("uniqueID===",visitStr);
        return visitStr;

    }

    public void selectedSearchValue(final String id) {
        ApiKey apiKey = new ApiKey();
        final String method = "customer_visit_follow";
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  jsonObject = new JSONObject(response);
                            setData(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("method",method);
                params.put("key",apikey);
                params.put("customer_visit_search",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setData(JSONObject jsonObject) {
//        str_customer_name = jsonObject.optString("customer");
//        str_visitdate = jsonObject.optString("visit_date");
//        str_visit_for = jsonObject.optString("visit_for");
//        str_visit_type = jsonObject.optString("visit_type");
//        strContactPerson = jsonObject.optString("contact_person");
//        strContactNo = jsonObject.optString("contact");
//        strEmail = jsonObject.optString("email_id");
//        strAddress = jsonObject.optString("address");
//        str_product_name = jsonObject.optString("product");
//        strStatus = jsonObject.optString("status");
//        str_scope = jsonObject.optString("scope");
//        strRemark = jsonObject.optString("remark");
//        str_other_employee_name = jsonObject.optString("other_employee_name");
        //============================================
        edt_customer_name.setText(jsonObject.optString("customer"));
        edt_visitdate.setText(jsonObject.optString("visit_date"));
        spinner_visit_for.setSelection(Integer.parseInt(jsonObject.optString("visit_for")));
        spinner_visit_type.setSelection(Integer.parseInt(jsonObject.optString("visit_type")));

        edtContactPerson.setText(jsonObject.optString("contact_person"));
        edtContactNo.setText(jsonObject.optString("contact"));
        edtEmail.setText(jsonObject.optString("email_id"));
        edtAddress.setText(jsonObject.optString("address"));
        edt_product_name.setText(jsonObject.optString("product"));

        spinner_status.setSelection(Integer.parseInt(jsonObject.optString("status")));
        spinner_scope.setSelection(Integer.parseInt(jsonObject.optString("scope")));

        edtRemark.setText(jsonObject.optString("remark"));
        edt_other_employee_name.setText(jsonObject.optString("other_employee_name"));

    }
}
