package com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS;

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
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.HRMS.AttendanceSummaryAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary.Attendance;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.AttendanceSummary.AttendanceSummaryResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.AttendanceSummary.AttendanceSummaryPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.AttendanceSummaryView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AttendanceSummaryActivity extends AppCompatActivity implements View.OnClickListener,
        AttendanceSummaryView {

    CalendarView calendarView;
    RecyclerView recyclerView;
    List<Attendance> attendanceList;
    AttendanceSummaryAdaptor attendanceAdaptor;
    String str_month, month, str_year;
    int currentMonthPosition, currentYearPosition;
    ImageView calendarBackBtm, calendarForewordBtn;
    TextView calendarMonth_TextView;
    String currentMonthINTEXT, currentYear;
    ApiKey apiKey = new ApiKey();
    String key;
    String method;
    MainPresenter presenter;

    ProgressDialog progressDialog;

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
        currentYearPosition = Integer.parseInt(str_year);
        calendarBackBtm = findViewById(R.id.calendarbackbtn);
        calendarForewordBtn = findViewById(R.id.calendarforwardbtn);
        calendarMonth_TextView = findViewById(R.id.txt_calendarMonth);
        calendarBackBtm.setOnClickListener(this);
        calendarForewordBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.attCalendarviewRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        attendanceList = new ArrayList<>();
        attendance_summary_data(str_month,str_year);
        mapMonth(Integer.parseInt(str_month), Integer.parseInt(str_year));
        calendarView = findViewById(R.id.attCalendarView);
        calendarView.setWeekDayTextAppearance(R.color.colorPrimaryDark);

        Log.e("Calendar Date", String.valueOf(calendarView.getDate()));
        Log.e("Calendar Date", new Gson().toJson(calendarView.getDate()));
//        calendarView.
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                str_month = String.valueOf(month + 1);
                str_year = String.valueOf(year);
                attendance_summary_data(str_month,str_year);
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

        str_year = cy.format(c);
        Log.e("month", month);
        Log.e("year", currentYear);
    }

    public void getCurrentMonthYear(String date) {
        DateFormat inputFormatter1 = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        SimpleDateFormat cm = new SimpleDateFormat("MMM");
        SimpleDateFormat cy = new SimpleDateFormat("yyyy");
        month = df.format(date1);
        if (Integer.parseInt(df.format(date1)) < 10) {
            str_month = df.format(date1).replace("0", "");
        } else {
            str_month = df.format(date1);
        }

        currentMonthINTEXT = cm.format(date1);
        currentYear = cy.format(date1);

        str_year = cy.format(date1);
        Log.e("month", str_month);
        Log.e("year", currentYear);
    }

    private void attendance_summary_data(final String strMonth, final String strYear) {
        method = "attendance_summary";
        key = apiKey.saltStr();
        str_month = strMonth;
        str_year = strYear;
        presenter = new AttendanceSummaryPresenterImpl(this);
        presenter.init();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.calendarbackbtn:
                    setPreviousMonth();
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
        try{
            if(attendanceList.size() > 0){
                findDateYearMonth();
                int month = Integer.parseInt(str_month);
                if(month==12){
                    str_month = "1";
                    str_year = String.valueOf(Integer.parseInt(currentYear) + 1);
                    attendance_summary_data(str_month,str_year);
                }
                else if(month> 0 && month<12){
                    str_month = String.valueOf(Integer.parseInt(str_month) + 1);
                    attendance_summary_data(str_month,str_year);

                }
                else if(currentMonthPosition<Integer.parseInt(str_month)){
                    str_month = String.valueOf(Integer.parseInt(str_month) -1);
                    if(currentYearPosition<Integer.parseInt(str_year)){
                        str_year = String.valueOf(Integer.parseInt(str_year) - 1);
                        attendance_summary_data(str_month,str_year);
                    }else{
                        attendance_summary_data(str_month,str_year);
                    }
                }
            }else{
                if(currentMonthPosition<Integer.parseInt(str_month) && currentYearPosition<Integer.parseInt(str_year)){
                    str_month = String.valueOf(Integer.parseInt(str_month) - 1);
                    str_year = String.valueOf(Integer.parseInt(str_year) - 1);
                    attendance_summary_data(str_month,str_year);
                }
                else if(currentMonthPosition<Integer.parseInt(str_month) && currentYearPosition>Integer.parseInt(str_year)){
                    if(Integer.parseInt(str_month)>12){
                        str_month = "1";
                        str_year = String.valueOf(Integer.parseInt(str_year) + 1);
                        attendance_summary_data(str_month,str_year);
                    }else{
                        str_month = String.valueOf(Integer.parseInt(str_month) + 1);
                        attendance_summary_data(str_month,str_year);
                    }
                }
                else if(currentMonthPosition>=Integer.parseInt(str_month) && currentYearPosition>= Integer.parseInt(str_year)){
                    str_month = String.valueOf(Integer.parseInt(str_month)+1);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void findDateYearMonth() {
        String date = new Gson().toJson(attendanceList.get(0));
        String datee = null;
        JSONObject Mdate = null;
        try {
            Mdate = new JSONObject(date);
            datee = Mdate.getString("date");
            getCurrentMonthYear(datee);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Datee",date);
        Log.e("MDatee",datee);

    }

    private void setPreviousMonth() {
        try{
            if(attendanceList.size() > 0){
                findDateYearMonth();
                Log.e("<<",str_month + " " + str_year);
                int month = Integer.parseInt(str_month);
                if(month==1){
                    str_month = "12";
                    str_year = String.valueOf(Integer.parseInt(currentYear) - 1);
                    attendance_summary_data(str_month,str_year);
                    Log.e("month 12",str_month + " "+str_year);
                }else if(month> 0 && month<12){
                    str_month = String.valueOf(Integer.parseInt(str_month) - 1);
                    str_year = str_year;
                    Log.e("month 12<>0",str_month + " "+str_year);
                    attendance_summary_data(str_month,str_year);
                }else if(currentMonthPosition<Integer.parseInt(str_month)){
                    str_month = String.valueOf(Integer.parseInt(str_month) -1);
                    if(currentYearPosition<Integer.parseInt(str_year)){
                        Log.e("month elseY",str_month + " "+str_year);
                        str_year = String.valueOf(Integer.parseInt(str_year) - 1);
                        attendance_summary_data(str_month,str_year);
                    }else{                    Log.e("month elseNo",str_month + " "+str_year);
                        attendance_summary_data(str_month,str_year);
                    }
                }
            }
            else {
                    if(currentMonthPosition<Integer.parseInt(str_month) && currentYearPosition<Integer.parseInt(str_year)){
                        str_month = String.valueOf(Integer.parseInt(str_month)-1);
                        str_year = String.valueOf(Integer.parseInt(str_year) -1);
                        attendance_summary_data(str_month,str_year);
                    }
                    else if(currentMonthPosition<Integer.parseInt(str_month) && currentYearPosition<=Integer.parseInt(str_year)){
                        if(Integer.parseInt(str_month)==1){
                            str_month = "12";
                            str_year = String.valueOf(Integer.parseInt(str_year) -1);
                            attendance_summary_data(str_month,str_year);
                        }
                        else{
                            str_month = String.valueOf(Integer.parseInt(str_month) - 1);
                            attendance_summary_data(str_month,str_year);
                        }
                    }
                    else if(currentMonthPosition==Integer.parseInt(str_month) && currentYearPosition==Integer.parseInt(str_year)) {
                        if (attendanceList.size() == 0) {
                            str_month = String.valueOf(Integer.parseInt(str_month) - 1);
                            attendance_summary_data(str_month, str_year);
                        }
                    }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void mapMonth(int i,int y) {
        switch (i) {
            case 1:
                calendarMonth_TextView.setText("January " + y);
                break;
            case 2:
                calendarMonth_TextView.setText("February " + y);
                break;
            case 3:
                calendarMonth_TextView.setText("March " + y);
                break;
            case 4:
                calendarMonth_TextView.setText("April " + y);
                break;
            case 5:
                calendarMonth_TextView.setText("May " + y);
                break;
            case 6:
                calendarMonth_TextView.setText("June " + y);
                break;
            case 7:
                calendarMonth_TextView.setText("July " + y);
                break;
            case 8:
                calendarMonth_TextView.setText("August " + y);
                break;
            case 9:
                calendarMonth_TextView.setText("September " + y);
                break;
            case 10:
                calendarMonth_TextView.setText("October " + y);
                break;
            case 11:
                calendarMonth_TextView.setText("November " + y);
                break;
            case 12:
                calendarMonth_TextView.setText("December " + y);
                break;
        }
    }

    @Override
    public String getEmpid() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMonth() {
        return str_month;
    }

    @Override
    public String getYear() {
        return str_year;
    }

    @Override
    public void showLoadingLayout() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoadingLayout() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof AttendanceSummaryResponse){
            AttendanceSummaryResponse response = (AttendanceSummaryResponse)object;
            if(response!=null) {
                if(response.getStatus().equals(CONSTANT.TRUE)) {
                    if(attendanceList.size()!=0) {
                        attendanceList.clear();
                    }
                    AttendanceSummaryResponse jobResponse = ((AttendanceSummaryResponse) object);
                    attendanceList.addAll(jobResponse.getAttendance());
                    attendanceAdaptor = new AttendanceSummaryAdaptor(this,attendanceList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(attendanceAdaptor);
                    mapMonth(Integer.parseInt(str_month),Integer.parseInt(str_year));

                } else{
                    Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.try_again,Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
