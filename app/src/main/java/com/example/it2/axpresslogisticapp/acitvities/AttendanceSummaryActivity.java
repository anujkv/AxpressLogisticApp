package com.example.it2.axpresslogisticapp.acitvities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttendanceSummaryActivity extends AppCompatActivity {

    String attendance_summary_url = "http://webapi.axpresslogistics.com/api/HRMS/attendance_summary";
    CalendarView calendarView;
    CompactCalendarView compactCalendarView;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    String date,TAG = "TAG : ";
    Intent intent;
    String jsonString, emplid, formattedDate;
    JSONObject jObj;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_summary);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Attendance Summary");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                String str_month = String.valueOf(month+1);
                attendance_summary_data(str_month);

            }
        });
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
                    String status = jsonObject.optString("status");
                    String apikeyResponse = jsonObject.optString("key");
                    String methodResp = jsonObject.optString("method");

                    if(status.equals("true") && apikeyResponse.equals(apikey)){
                        JSONArray jsonArray = jsonObject.getJSONArray("attendance");
                        Log.e("Status : ",status);
                        Log.e(" ,Month : ",str_month);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            object.getString("date");
                            object.getString("day_status");
                            object.getString("in_time");
                            object.getString("out_time");
                            object.getString("leave_type");
                            object.getString("pin_no");
                            object.getString("reason");
                            object.getString("applied_date");
//                            object.getString("applied_leave");\
                            Log.e("date", object.getString("date"));
                            Log.e("day_status", object.getString("day_status"));
                            Log.e("in_time", object.getString("in_time"));
                            Log.e("out_time", object.getString("out_time"));
                            Log.e("leave_type", object.getString("leave_type"));
                            Log.e("pin_no", object.getString("pin_no"));
                            Log.e("reason", object.getString("reason"));
                            Log.e("=======","==============");

                            if(object.getString("day_status").equals("A")){

//                                setAbsent(object.getString("date"));
                            }else if (object.getString("day_status").equals("S")){
                                setSunday(object.getString("date"));
                            }
                        }
                    }else{
                        Log.e("Status : ",status);
                        Log.e("Month : ",str_month);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }

            private void setSunday(String date) {
//                calendarView.set
                calendarView.setBackgroundColor(Color.RED);
            }

            private void setAbsent(String day_status) {
                calendarView.setBackgroundColor(Color.RED);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key",apikey);
                params.put("method",method);
                params.put("month", str_month);
                params.put("emplid","1853");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
