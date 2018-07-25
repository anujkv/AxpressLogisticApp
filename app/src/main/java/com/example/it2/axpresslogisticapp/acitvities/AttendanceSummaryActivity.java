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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
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

public class AttendanceSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    String attendance_summary_url = "http://webapi.axpresslogistics.com/api/HRMS/attendance_summary";
    CalendarView calendarView;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    String date, TAG = "TAG : ";
    Intent intent;
    String jsonString, emplid, formattedDate, status;
    JSONObject jObj;
    RecyclerView recyclerView;
    List<AttendanceModel> attendanceModelList;
    AttendanceAdaptor attendanceAdaptor;
    AttendanceModel attendanceModel;
    String str_month, month;
    int currentMonthPosition,countmin,countmax;
    ImageView calendarBackBtm, calendarForewordBtn;
    TextView calendarMonth_TextView;
    String currentMonthINTEXT, currentYear;
    ArrayList<String> monthlist = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"));

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

//        try {
//            intent = getIntent();
//            jsonString = intent.getStringExtra("response");
//            jObj = new JSONObject(jsonString);
//            emplid = jObj.optString("Emplid");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


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

    private void mapMonth(int i) {
        switch (i) {
            case 1:
                calendarMonth_TextView.setText("January " + currentYear);
                break;
            case 2:
                calendarMonth_TextView.setText("February " + currentYear);
                break;
            case 3:
                calendarMonth_TextView.setText("March " + currentYear);
                break;
            case 4:
                calendarMonth_TextView.setText("April " + currentYear);
                break;
            case 5:
                calendarMonth_TextView.setText("May " + currentYear);
                break;
            case 6:
                calendarMonth_TextView.setText("June " + currentYear);
                break;
            case 7:
                calendarMonth_TextView.setText("July " + currentYear);
                break;
            case 8:
                calendarMonth_TextView.setText("August " + currentYear);
                break;
            case 9:
                calendarMonth_TextView.setText("September " + currentYear);
                break;
            case 10:
                calendarMonth_TextView.setText("October " + currentYear);
                break;
            case 11:
                calendarMonth_TextView.setText("November " + currentYear);
                break;
            case 12:
                calendarMonth_TextView.setText("December " + currentYear);
                break;

        }
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

        Log.e("Current Date = ", c.toString());
        Log.e("Str Date = ", str_month);
        Log.e("Date = ", month);

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
                    String methodResp = jsonObject.optString("method");

                    if (status.equals("true") && apikeyResponse.equals(apikey)) {
                        mapMonth(currentMonthPosition);
                        JSONArray jsonArray = jsonObject.getJSONArray("attendance");
                        Log.e("Status : ", status);
                        Log.e(" Month : ", str_month);
                        Log.e(" Response : ", response);
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
                            Log.e("date", object.getString("date"));
                            Log.e("day_status", object.getString("day_status"));
                            Log.e("in_time", object.getString("in_time"));
                            Log.e("out_time", object.getString("out_time"));
                            Log.e("leave_type", object.getString("leave_type"));
                            Log.e("pin_no", object.getString("pin_no"));
                            Log.e("reason", object.getString("reason"));
                            Log.e("applied_date", object.getString("applied_date"));
                            Log.e("approval_flag", object.getString("approval_flag"));
                            Log.e("=======", "==============");
                            attendanceModelList.add(attendanceModel);
                        }
                        attendanceAdaptor = new AttendanceAdaptor(AttendanceSummaryActivity.this,
                                attendanceModelList);
                        recyclerView.setAdapter(attendanceAdaptor);

                    } else {
                        Log.e("Status : ", status);
                        Log.e("Month : ", str_month);
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
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key", apikey);
                params.put("method", method);
                params.put("month", str_month);
                params.put("emplid", "1853");
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
        Log.e("Month==", String.valueOf(currentMonthPosition));

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
        Log.e("Month==", String.valueOf(currentMonthPosition));

    }

    private void setMonthData(int currentMonthPosition) {
        attendanceModelList.clear();
        attendance_summary_data(String.valueOf(currentMonthPosition));
    }
}
