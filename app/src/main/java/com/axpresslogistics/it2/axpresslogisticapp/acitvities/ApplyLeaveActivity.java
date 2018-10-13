package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.AppliedLeaveAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.AppliedLeaveModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.DEVELOPMENT_URL;
import static com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT.URL;


public class ApplyLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    String applied_url = URL + "HRMS/leave_entry";
    String leave_info_url = URL + "HRMS/leave_search";

//    String applied_url = DEVELOPMENT_URL + "HRMS/leave_entry";
//    String leave_info_url = DEVELOPMENT_URL + "HRMS/leave_search";

    Intent intent;
    String formattedDate,formattedYearTime;
    CalendarView calendarView, calendarView1;
    String date2, date, fromDate, toDate, leaveReason, strleave_type, strPin_no, leaveType,ID = null,
            METHOD,KEY,EMPLID;
    EditText input_leave_from, input_leave_to;
    int dayfrom, monthfrom, yearfrom, dayto, monthto, yearto;
    TextView total_leave_days,txt_datanotfound;
    EditText editTextReason_of_leave;
    AlertDialog.Builder builderfrom, builderto;
    Spinner spinner_apply_leave;
    int daysDifference = 0;
    SimpleDateFormat df,dy;
    AlertDialog dialog;
    String notApplied = "Leave Not Applied", applied = "Leave Applied",LEAVE_DAYS = null;
    RecyclerView recyclerView;
    List<AppliedLeaveModel> appliedLeaveModelList;
    AppliedLeaveAdaptor leaveAdaptor;
    Date selectedDatefrom,selectedDateto,currentDate;
    DateFormat formatter;
    Button okButton;
    int inputResonCount = 0;
    int inputDateCount = 0;
    int selectorInputCount = 0;
    Timestamp timestamp = null;
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
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
        currentDateFormate();

        calendarView = findViewById(R.id.calendarView);
//        calendarView.setMinDate(System.currentTimeMillis());
        txt_datanotfound =findViewById(R.id.txt_nofounddata);
        recyclerView = findViewById(R.id.leaveAppliedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        appliedLeaveModelList = new ArrayList<>();
        uploadleavelist();
        Intent intent = getIntent();
        ID= intent.getStringExtra("id");
        try{
            if(ID != null){
                Toast.makeText(getApplicationContext(),"ID " + ID,Toast.LENGTH_SHORT).show();
                METHOD = intent.getStringExtra("method");
                calendarView_method();
            }else{
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, final int dayOfMonth) {

                        dayfrom = dayOfMonth;
                        monthfrom = month + 1;
                        yearfrom = year;
                        date = dayOfMonth + "-" + monthfrom + "-" + year;
                        try {
                            selectedDatefrom = formatter.parse(date);
                            System.out.println("Today is " + selectedDatefrom.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        createDialogBox();
                    }
                });
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void currentDateFormate() {
        currentDate = Calendar.getInstance().getTime();
        int last2month = currentDate.getMonth()-1;

        df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        dy = new SimpleDateFormat("yyyy hh:mm:ss");
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(currentDate);
        formattedYearTime = dy.format(currentDate);
        String previous2month = "01" +"-"+ last2month+"-"+formattedYearTime;
        Log.e("fDATE",formattedDate);
        Log.e("fDATE",previous2month);
        Log.e("current>>", String.valueOf(System.currentTimeMillis()));
        DateFormat dfp = new SimpleDateFormat("MM-dd-yyyy");
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = (Date) formatter.parse(previous2month);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            Log.e("fDATE>>", String.valueOf(timeStampDate.getTime()));




        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }

    }

    private void calendarView_method() {
        try{
            Intent intent = getIntent();
            ID = intent.getStringExtra("id");
            fromDate = intent.getStringExtra("from");
            toDate = intent.getStringExtra("to");
            strleave_type = intent.getStringExtra("type");
            LEAVE_DAYS = intent.getStringExtra("days");
            leaveReason = intent.getStringExtra("reason");
            strPin_no = intent.getStringExtra("pin_no");
            METHOD = intent.getStringExtra("method");
            KEY = intent.getStringExtra("key");
            EMPLID = intent.getStringExtra("emplid");
            createDialogBox();
        }catch(Exception e) {
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
                if (inputDateCount > 0 && inputResonCount > 0) {
                    validation(inputDateCount, inputResonCount, selectorInputCount);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(fromDate!=null){
            input_leave_from.setText("");
            input_leave_to.setText("");
            total_leave_days.setText("");
            editTextReason_of_leave.setText("");
        }
        else{
            input_leave_from.setText(date);
        }
        //FROM..............
        builderfrom = new AlertDialog.Builder(alertLayout.getContext());
        builderfrom.setTitle("Apply Leave");
        builderfrom.setView(alertLayout);
        builderfrom.setCancelable(false);

        builderfrom.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ID!=null){
                    finish();
                }
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builderfrom.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                try {
                    applied();
                    refreshAppliedList();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        checkEmptyFields();
        dialog = builderfrom.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        clickInputTOLEave();
    }

    private void clickInputTOLEave() {
        input_leave_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date current = Calendar.getInstance().getTime();
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.calendar_view, null);
                calendarView1 = alertLayout.findViewById(R.id.calendarViewdatePicker);

                try{
                    Log.e("selectedDatefrom", String.valueOf(selectedDatefrom));
                    if(String.valueOf(selectedDatefrom)!=null && !String.valueOf(selectedDatefrom).equals("null") ){
                        calendarView1.setMinDate(selectedDatefrom.getTime());
                        Log.e("setMinDate", String.valueOf(selectedDatefrom));
                    }else{
                        calendarView1.setMinDate(current.getTime());
                        Log.e("setMinDatecurrent", String.valueOf(current));
                    }

                }catch(NullPointerException e){
                    e.printStackTrace();
                    calendarView1.setMinDate(current.getTime());
                    Log.e("setMinDatecurrent", String.valueOf(current));
                }

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
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            selectedDateto = formatter.parse(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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

        if(ID != null){
            input_leave_from.setText("");
            input_leave_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date selectedDate = new Date();
                    LayoutInflater inflater = getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.calendar_view, null);
                    calendarView = alertLayout.findViewById(R.id.calendarViewdatePicker);
//                    calendarView.setMinDate(selectedDate.getTime());
                    Log.e("MIN TIME", String.valueOf(selectedDate.getTime()));
                    System.out.println(calendarView.getDate() + "  Current Time");
                    builderto = new AlertDialog.Builder(alertLayout.getContext());
                    builderto.setTitle("Apply Leave");
                    builderto.setView(alertLayout);
                    builderto.setCancelable(false);

                    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                            dayfrom = dayOfMonth;
                            monthfrom = month + 1;
                            yearfrom = year;
                            date = dayOfMonth + "-" + monthfrom + "-" + year;
                            Log.e("FROM:",date);
                            input_leave_from.setText(date);
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
                            input_leave_from.setText(date);
                            fromDate = date;
                            calculate_days();

                        }
                    });
                    AlertDialog dialog1 = builderto.create();
                    dialog1.show();
                }
            });
        }

    }

    private void mapLeaveCode(String strleave_type) {
        String type = strleave_type;
        switch (type) {
            case "Earned Leave":
                leaveType = "EL";
                strPin_no = "903";
                break;

            case "On Duty":
                leaveType = "OD";
                strPin_no = "905";
                break;

            case "Compensatory Off":
                leaveType = "CO";
                strPin_no = "907";
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
                Log.e("charSBEFORE", String.valueOf(s));
                Log.e("startBEFORE", String.valueOf(start));
                Log.e("countBEFORE", String.valueOf(count));
                Log.e("afterBEFORE", String.valueOf(after));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                inputDateCount = count;
                validation(inputDateCount, inputResonCount, selectorInputCount);

                Log.e("charSTextChanged", String.valueOf(s));
                Log.e("startTextChanged", String.valueOf(start));
                Log.e("countTextChanged", String.valueOf(count));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("charSAfter", String.valueOf(s));   }
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
        Log.e("FROM<>>",fromDate);
        Log.e("TO<>>",toDate);

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
            case R.id.mapbtn_toolbar:
                refreshAppliedList();
                break;
        }
    }

    private void applied() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        leaveReason = editTextReason_of_leave.getText().toString();
        ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();
        final String method;
        if(METHOD == null){
            method = "apply_leave";
        }else{
            method = "edit_leave";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, applied_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response Leave :",response);
                if (response != null && response.length() > 0) {
                    progressDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.optString("status");
                        String apiKeyResponse = object.optString("key");
                        Log.e("Edit Leave Response",response);

                        if (status.equals("true") && apiKeyResponse.equals(apikey)) {
                            refreshAppliedList();
                            Toast.makeText(getApplicationContext(), applied, Toast.LENGTH_SHORT).show();
                            if(method.equals("edit_leave")){
                                refreshAppliedList();
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), notApplied, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            uploadleavelist();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                } else {
                    Log.e("Response: ", response.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======",""+error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
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
                Log.e("method", method);
                Log.e("key", apikey);
                Log.e("emplid", Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                Log.e("from", fromDate);
                Log.e("to", toDate);
                Log.e("days", String.valueOf(daysDifference));
                Log.e("reason", leaveReason);
                Log.e("type", leaveType);
                Log.e("pin_no", strPin_no);
                Log.e("applied_date", formattedDate);

//                Log.e("ID>>",ID);
                if(method.equals("apply_leave")){
                    params.put("method", method);
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                    params.put("pin_no", strPin_no);
                    params.put("type", leaveType);
                    params.put("from", fromDate);
                    params.put("to", toDate);
                    params.put("days", String.valueOf(daysDifference));
                    params.put("reason", leaveReason);
                    params.put("applied_date", formattedDate);
                    return params;
                }
                else {
                    Log.e("ID>>",ID);
                    params.put("id",ID);
                    params.put("method", "edit_leave");
                    params.put("key", apikey);
                    params.put("emplid", Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                    params.put("pin_no", strPin_no);
                    params.put("type", leaveType);
                    params.put("from", input_leave_from.getText().toString().trim());
                    params.put("to", toDate);
                    params.put("days", String.valueOf(daysDifference));
                    params.put("reason", leaveReason);
                    params.put("approval_flag","pushback");
//                    params.put("applied_date", formattedDate);
                    return params;
                }

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
                try{
                    uploadleavelist();
                }catch(Exception e){
                    e.printStackTrace();
                }
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
                        Log.e("LeaveResponse", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.optString("status");
                            String apikeyResponse = object.optString("key");
                            JSONArray array = object.optJSONArray("leave");
                            Log.e("JSONArray", response);
                            if (status.equals("true") && apikeyResponse.equals(apikey1)) {
                                recyclerView.setVisibility(View.VISIBLE);
                                txt_datanotfound.setVisibility(View.GONE);
                                Log.e("ResponseforRecycle ", status);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    appliedLeaveModel = new AppliedLeaveModel(
                                            jsonObject.getString("id"),
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
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                txt_datanotfound.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Could not load data!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            recyclerView.setVisibility(View.GONE);
                            txt_datanotfound.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
                txt_datanotfound.setVisibility(View.VISIBLE);
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
                    txt_datanotfound.setText(CONSTANT.INTERNET_ERROR);
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                    txt_datanotfound.setText(CONSTANT.TIMEOUT_ERROR);

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", method);
                params.put("key", apikey1);
                params.put("emplid", Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
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
