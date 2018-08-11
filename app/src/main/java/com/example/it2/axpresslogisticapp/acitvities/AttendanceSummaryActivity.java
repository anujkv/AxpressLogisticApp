package com.example.it2.axpresslogisticapp.acitvities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.Utilities.Preferences;
import com.example.it2.axpresslogisticapp.adaptor.AttendanceAdaptor;
import com.example.it2.axpresslogisticapp.model.AttendanceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class AttendanceSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    String attendance_summary_url = URL +"HRMS/attendance_summary";
    CalendarView calendarView;
    Intent intent;
    String status;
    RecyclerView recyclerView;
    List<AttendanceModel> attendanceModelList;
    AttendanceAdaptor attendanceAdaptor;
    AttendanceModel attendanceModel;
    String str_month, month;
    int currentMonthPosition;
    ImageView calendarBackBtm, calendarForewordBtn;
    TextView calendarMonth_TextView;
    String currentMonthINTEXT, currentYear;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_summary);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Attendance Summary");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);

        getCurrentMonth();
        currentMonthPosition = Integer.parseInt(str_month);
        calendarBackBtm = findViewById(R.id.calendarbackbtn);
        calendarForewordBtn = findViewById(R.id.calendarforwardbtn);
        calendarMonth_TextView = findViewById(R.id.txt_calendarMonth);
        calendarBackBtm.setOnClickListener(this);
        calendarForewordBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.attCalendarviewRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        attendanceModelList = new ArrayList<>();
        attendance_summary_data(str_month);

        calendarView = findViewById(R.id.attCalendarView);
        calendarView.setWeekDayTextAppearance(R.color.colorPrimaryDark);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                str_month = String.valueOf(month + 1);
                attendance_summary_data(str_month);
            }
        });
    }

    public void getCurrentMonth() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        SimpleDateFormat cm = new SimpleDateFormat("MMM");
        SimpleDateFormat cy = new SimpleDateFormat("yyyy");
        month = df.format(c);
        if (Integer.parseInt(df.format(c)) < 10) {
            str_month = df.format(c).replace("0", "");
        } else {
            str_month = df.format(c);
        }
        currentMonthINTEXT = cm.format(c);
        currentYear = cy.format(c);

    }

    private void attendance_summary_data(final String str_month) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final ApiKey apiKey = new ApiKey();
        final String method = "attendance_summary";
        final String apikey = apiKey.saltStr();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, attendance_summary_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.optString("status");
                    String apikeyResponse = jsonObject.optString("key");

                    if (status.equals("true") && apikeyResponse.equals(apikey)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("attendance");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            attendanceModel = new AttendanceModel(
                                    object.getString("date"),
                                    object.getString("in_time"),
                                    object.getString("out_time"),
                                    object.getString("reason"),
                                    object.getString("approval_flag"),
                                    object.getString("applied_date"),
                                    object.getString("pin_no"),
                                    object.getString("day_status"),
                                    object.getString("leave_type")
                            );
                            attendanceModelList.add(attendanceModel);
                        }
                        attendanceAdaptor = new AttendanceAdaptor(AttendanceSummaryActivity.this,
                                attendanceModelList);
                        recyclerView.setAdapter(attendanceAdaptor);

                    } else {
                        Toast.makeText(getApplicationContext(), "Could not load data!",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key", apikey);
                params.put("method", method);
                params.put("month", str_month);
                params.put("emplid", Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.calendarbackbtn:
                if (currentMonthPosition >= 1) {
                    setPreviousMonth();
                }
                break;
            case R.id.calendarforwardbtn:
                if (currentMonthPosition <= Integer.parseInt(str_month)) ;
            {
                setNextMonth();
            }
            break;
        }
    }

    private void setNextMonth() {
        try {
            if (attendanceModelList.size() > 0) {
                currentMonthPosition = currentMonthPosition + 1;
                setMonthData(currentMonthPosition);
            }else{
                if (currentMonthPosition > Integer.parseInt(str_month)){
                    currentMonthPosition = --currentMonthPosition;
                    setMonthData(currentMonthPosition);
                }
                else {
                    currentMonthPosition = ++currentMonthPosition + 1;
                    setMonthData(currentMonthPosition);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPreviousMonth() {
        try {
            if (attendanceModelList.size() > 0) {
                currentMonthPosition = currentMonthPosition - 1;
                setMonthData(currentMonthPosition);
            }else if (currentMonthPosition> Integer.parseInt(str_month)){
                currentMonthPosition = --currentMonthPosition -1;
                setMonthData(currentMonthPosition);
            }else{
                currentMonthPosition = ++currentMonthPosition;
                setMonthData(currentMonthPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMonthData(int currentMonthPosition) {
        attendanceModelList.clear();
        attendance_summary_data(String.valueOf(currentMonthPosition));
    }
}
