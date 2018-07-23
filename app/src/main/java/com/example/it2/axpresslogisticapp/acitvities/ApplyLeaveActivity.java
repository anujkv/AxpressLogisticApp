package com.example.it2.axpresslogisticapp.acitvities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.it2.axpresslogisticapp.adaptor.AppliedLeaveAdaptor;
import com.example.it2.axpresslogisticapp.model.AppliedLeaveModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApplyLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    String applied_url = "http://webapi.axpresslogistics.com/api/HRMS/leave_entry";
    String leave_info_url = "http://webapi.axpresslogistics.com/api/HRMS/leave_search";
    Intent intent;
    String jsonString, emplid, formattedDate;
    JSONObject jObj;
    CalendarView calendarView, calendarView1;
    String date2, date, fromDate, toDate, leaveReason, strleave_type, strPin_no, leaveType;
    EditText input_leave_from, input_leave_to;
    int dayfrom, monthfrom, yearfrom, dayto, monthto, yearto;
    TextView total_leave_days;
    EditText editTextReason_of_leave;
    AlertDialog.Builder builderfrom, builderto;
    Spinner spinner_apply_leave;
    int daysDifference = 0;
    AlertDialog dialog;
    String notApplied = "Leave Not Applied", applied = "Leave Applied";
    RecyclerView recyclerView;
    List<AppliedLeaveModel> appliedLeaveModelList;
    AppliedLeaveAdaptor leaveAdaptor;
    Date selectedDate;
    Button okButton;
    int inputResonCount = 0;
    int inputDateCount = 0;
    int selectorInputCount = 0;
    AppliedLeaveModel appliedLeaveModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Apply Leave");
        ImageButton backbtn_toolbar, savebtn_toolbar;
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));

        calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis());

        recyclerView = findViewById(R.id.leaveAppliedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        appliedLeaveModelList = new ArrayList<>();
        uploadleavelist();
        calendarView.setWeekNumberColor(Color.RED);
        calendarView.setWeekSeparatorLineColor(Color.GREEN);
        calendarView.setFocusedMonthDateColor(Color.YELLOW);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, final int dayOfMonth) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
                formattedDate = df.format(c);
                final int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int m = Calendar.getInstance().get(Calendar.MONTH);
                final int y = Calendar.getInstance().get(Calendar.YEAR);
                dayfrom = dayOfMonth;
                monthfrom = month + 1;
                yearfrom = year;
                date = dayOfMonth + "-" + monthfrom + "-" + year;
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    selectedDate = (Date) formatter.parse(date);
                    System.out.println("Today is " + selectedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                dateAPI = year + "-" + month+ "-" + dayOfMonth;
                createDialogBox();
            }
//                else {}
//            }
        });


        try {
            intent = getIntent();
            jsonString = intent.getStringExtra("response");
            jObj = new JSONObject(jsonString);
            emplid = jObj.optString("Emplid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createDialogBox() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.apply_leave_view, null);
        input_leave_from = alertLayout.findViewById(R.id.input_leave_from);
        input_leave_to = alertLayout.findViewById(R.id.input_leave_to);
        total_leave_days = alertLayout.findViewById(R.id.total_leave_days);
        spinner_apply_leave = alertLayout.findViewById(R.id.spinner_apply_leave);
        editTextReason_of_leave = alertLayout.findViewById(R.id.editTextReason_of_leave);

        spinner_apply_leave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strleave_type = parent.getItemAtPosition(position).toString();
                Log.e("StrLeave_TYPE", strleave_type);
                selectorInputCount = position;
                mapLeaveCode(strleave_type);
                Log.e("Spinner : ", String.valueOf(selectorInputCount));
                Log.e("Reason : ", String.valueOf(inputResonCount));
                Log.e("Date : ", String.valueOf(inputDateCount));
                if (inputDateCount > 0 && inputResonCount > 0) {
                    validation(inputDateCount, inputResonCount, selectorInputCount);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        input_leave_from.setText(date);
        builderfrom = new AlertDialog.Builder(alertLayout.getContext());
        builderfrom.setTitle("Apply Leave");
        builderfrom.setView(alertLayout);
        builderfrom.setCancelable(false);

        builderfrom.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builderfrom.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                applied();
            }


        });
        checkEmptyFields();
        dialog = builderfrom.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        input_leave_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.calendar_view, null);
                calendarView1 = alertLayout.findViewById(R.id.calendarViewdatePicker);
                calendarView1.setMinDate(selectedDate.getTime());
                System.out.println(calendarView.getDate() + "  Current Time");
                builderto = new AlertDialog.Builder(alertLayout.getContext());
                builderto.setTitle("Apply Leave");
                builderto.setView(alertLayout);
                builderto.setCancelable(false);

                calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        dayto = dayOfMonth;
                        monthto = month + 1;
                        yearto = year;
                        date2 = dayOfMonth + "-" + monthto + "-" + year;
                    }
                });

                builderto.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                builderto.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        input_leave_to.setText(date2);
                        calculate_days();
                    }
                });
                AlertDialog dialog1 = builderto.create();
                dialog1.show();
            }
        });
    }

    private void mapLeaveCode(String strleave_type) {
        String type = strleave_type;
        switch (type) {
            case "Casual Leave":
                leaveType = "CL";
                strPin_no = "901";
                break;

            case "Sick Leave":
                leaveType = "SL";
                strPin_no = "902";
                break;

            case "Earned Leave":
                leaveType = "EL";
                strPin_no = "903";
                break;

            case "Loss of Pay":
                leaveType = "LP";
                strPin_no = "904";
                break;

            case "On Duty":
                leaveType = "OD";
                strPin_no = "905";
                break;

            case "Maternity Leave":
                leaveType = "ML";
                strPin_no = "906";
                break;


            case "Compensatory Off":
                leaveType = "CO";
                strPin_no = "907";
                break;

            case "Credit Off":
                leaveType = "CR";
                strPin_no = "908";
                break;
            default:
                leaveType = "Select";
                strPin_no = "000";
        }
    }

    private void checkEmptyFields() {
        input_leave_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                inputDateCount = count;
                validation(inputDateCount, inputResonCount, selectorInputCount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextReason_of_leave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                inputResonCount = count;
                validation(inputDateCount, inputResonCount, selectorInputCount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void calculate_days() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
        fromDate = yearfrom + "-" + monthfrom + "-" + dayfrom;
        toDate = yearto + "-" + monthto + "-" + dayto;

        try {

            Calendar startDate = Calendar.getInstance();
            startDate.set(yearfrom, monthfrom, dayfrom);
            long startDateMillis = startDate.getTimeInMillis();

            Calendar endDate = Calendar.getInstance();
            endDate.set(yearto, monthto, dayto);
            long endDateMillis = endDate.getTimeInMillis();

            long differenceMillis = endDateMillis - startDateMillis;
            daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
            daysDifference = daysDifference + 1;
            if (daysDifference > 0) {
                Toast.makeText(getApplicationContext(), "Total days " + daysDifference, Toast.LENGTH_SHORT).show();
                total_leave_days.setText(String.valueOf(daysDifference));

            } else {
                Toast.makeText(getApplicationContext(), "Applied toDate should not be back head from start leave date!" +
                        daysDifference + 1, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
        }
    }

    private void applied() {
        leaveReason = editTextReason_of_leave.getText().toString();
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method = "apply_leave";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, applied_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.optString("status");
                        String apiKeyResponse = object.optString("key");

                        if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                            refreshAppliedList();
                            Toast.makeText(getApplicationContext(), applied, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), notApplied, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Response: ", response.toString());
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
                    Log.e("error: ", error.toString());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.e("method", method);
                Log.e("key", apikey);
                Log.e("emplid", emplid);
                Log.e("from", fromDate);
                Log.e("to", toDate);
                Log.e("days", String.valueOf(daysDifference));
                Log.e("reason", leaveReason);
                Log.e("type", leaveType);
                Log.e("pin_no", strPin_no);
                Log.e("applied_date", formattedDate);

                params.put("method", method);
                params.put("key", apikey);
                params.put("emplid", emplid);
                params.put("pin_no", strPin_no);
                params.put("type", leaveType);
                params.put("from", fromDate);
                params.put("to", toDate);
                params.put("days", String.valueOf(daysDifference));
                params.put("reason", leaveReason);
                params.put("applied_date", formattedDate);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void refreshAppliedList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final String method = "leave_info";
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        try {
            if(appliedLeaveModelList.size()>0){
                appliedLeaveModelList.clear();
                uploadleavelist();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
    }

    private void uploadleavelist() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        ApiKey apiKey = new ApiKey();
        final String method = "leave_info";
        final String apikey1 = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, leave_info_url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.optString("status");
                            String apikeyResponse = object.optString("key");
                            JSONArray array = object.optJSONArray("leave");
                            Log.e("JSONArray", response);
                            if (status.equals("true") && apikeyResponse.equals(apikey1)) {
                                Log.e("ResponseforRecycle ", status);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    appliedLeaveModel = new AppliedLeaveModel(
                                            jsonObject.getString("from"),
                                            jsonObject.getString("reason"),
                                            jsonObject.getString("days"),
                                            jsonObject.getString("type"),
                                            jsonObject.getString("to"),
                                            jsonObject.getString("pin_no"),
                                            jsonObject.getString("applied_date"),
                                            jsonObject.getString("approval_flag"));
                                    appliedLeaveModelList.add(appliedLeaveModel);
                                }
                                leaveAdaptor = new AppliedLeaveAdaptor(ApplyLeaveActivity.this, appliedLeaveModelList);
                                recyclerView.setAdapter(leaveAdaptor);
                                leaveAdaptor.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "Could not load data!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        leaveAdaptor.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey1);
                params.put("emplid", emplid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void validation(int count1, int count2, int count3) {
        if (count1 > 0 && count2 > 0 && count3 > 0) {
            okButton.setEnabled(true);
        } else {
            okButton.setEnabled(false);
        }
    }

}
