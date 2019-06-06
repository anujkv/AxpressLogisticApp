package com.axpresslogistics.it2.axpresslogisticapp.activities.HRMS;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.HRMS.LeaveAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.AppliedLeavedResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.ApplyingLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.Leave;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.EditLeave.EditLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave.AppliedLeavePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave.ApplyingLeavePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave.LeaveEditPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.model.AppliedLeaveModel;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.AppliedLeavedView;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.ApplyingLeaveView;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.EditLeaveView;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class ApplyLeaveActivity extends AppCompatActivity implements View.OnClickListener, AppliedLeavedView,
        ApplyingLeaveView, EditLeaveView {
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.leaveAppliedRecyclerView)
    RecyclerView leaveRecyclerView;
    ProgressDialog progressDialog;

    Intent intent;
    String formattedDate, formattedYearTime;
    CalendarView calendarView, calendarView1;
    String date2, date, fromDate, FROM_DATE, toDate, TO_DATE, leaveReason, strleave_type, strPin_no, leaveType, ID = null,
            METHOD, KEY, EMPLID, emplid;
    EditText input_leave_from, input_leave_to;
    int dayfrom, monthfrom, yearfrom, dayto, monthto, yearto;
    TextView total_leave_days, txt_datanotfound;
    EditText editTextReason_of_leave;
    AlertDialog.Builder builderfrom, builderto;
    Spinner spinner_apply_leave;
    int daysDifference = 0;
    SimpleDateFormat df, dy;
    AlertDialog dialog;
    String notApplied = "Leave Not Applied", applied = "Leave Applied", LEAVE_DAYS = null;

    ArrayList<Leave> leaveArrayList = new ArrayList<>();
    private LeaveAdaptor leaveAdaptor;
    Date selectedDatefrom, selectedDateto, currentDate;
    DateFormat formatter;
    Button okButton;
    int inputResonCount = 0;
    int inputDateCount = 0;
    int selectorInputCount = 0;
    Timestamp timestamp = null;
    Boolean connected = false;
    MainPresenter presenter;
    String method;
    ApiKey apiKey = new ApiKey();
    String key;
    String approval_flag;

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
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        checkNetworkconnection();
        currentDateFormate();

        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);

        calendarView = findViewById(R.id.calendarView);

//        calendarView.setMinDate(System.currentTimeMillis());
        txt_datanotfound = findViewById(R.id.txt_nofounddata);
        leaveRecyclerView = findViewById(R.id.leaveAppliedRecyclerView);
        leaveRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaveRecyclerView.setHasFixedSize(true);
        leaveArrayList = new ArrayList<>();
        uploadleavelist();
        Intent intent = getIntent();
        ID = intent.getStringExtra("id");
        try {
            if (ID != null) {
                Toast.makeText(getApplicationContext(), "ID " + ID, Toast.LENGTH_SHORT).show();
                METHOD = intent.getStringExtra("method");
                calendarView_method();
            } else {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void currentDateFormate() {
        currentDate = Calendar.getInstance().getTime();
        int last2month = currentDate.getMonth() - 1;

        df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        dy = new SimpleDateFormat("yyyy hh:mm:ss");
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(currentDate);
        formattedYearTime = dy.format(currentDate);
        String previous2month = "01" + "-" + last2month + "-" + formattedYearTime;
        Log.e("fDATE", formattedDate);
        Log.e("fDATE", previous2month);
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
        try {
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
            Log.e("EMPL ID", EMPLID);
            createDialogBox();
        } catch (Exception e) {
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

        if (fromDate != null) {
            input_leave_from.setText("");
            input_leave_to.setText("");
            total_leave_days.setText("");
            editTextReason_of_leave.setText("");
        } else {
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
                if (ID != null) {
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
                } catch (Exception e) {
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

                try {
                    Log.e("selectedDatefrom", String.valueOf(selectedDatefrom));
                    if (String.valueOf(selectedDatefrom) != null && !String.valueOf(selectedDatefrom).equals("null")) {
                        calendarView1.setMinDate(selectedDatefrom.getTime());
                        Log.e("setMinDate", String.valueOf(selectedDatefrom));
                    } else {
                        calendarView1.setMinDate(current.getTime());
                        Log.e("setMinDatecurrent", String.valueOf(current));
                    }

                } catch (NullPointerException e) {
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

        if (ID != null) {
//            input_leave_from.setText("");
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
                            Log.e("FROM:", date);
                            input_leave_from.setText(date);
                            try {
                                selectedDatefrom = formatter.parse(date);
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

    private String convert_date(String date) {
        SimpleDateFormat newformat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat oldformat = new SimpleDateFormat("yyyy-MM-dd");

        Date myDate;
        try {
//            int month = mMonth + 1;
            myDate = oldformat.parse(date);
            date = newformat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

            case "Half-Day":
                leaveType = "HD";
                strPin_no = "909";
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
                Log.e("charSAfter", String.valueOf(s));
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
        FROM_DATE = convert_date(fromDate);
        TO_DATE = convert_date(toDate);
        Log.e("FROM<>>", fromDate);
        Log.e("TO<>>", toDate);

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

        leaveReason = editTextReason_of_leave.getText().toString();
        if (METHOD == null) {
            key = apiKey.saltStr();
            method = "apply_leave";
            presenter = new ApplyingLeavePresenterImpl(this);
            presenter.init();
        } else {
            key = apiKey.saltStr();
            method = "edit_leave";
            presenter = new LeaveEditPresenterImpl(this);
            presenter.init();
            approval_flag = "pushback";
        }

    }

    private void refreshAppliedList() {
//        final ProgressDialog
        try {
            if (leaveArrayList.size() > 0) {
                leaveArrayList.clear();
                try {
                    uploadleavelist();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        progressDialog.dismiss();
    }

    private void uploadleavelist() {
        method = "leave_info";

        key = apiKey.saltStr();
        presenter = new AppliedLeavePresenterImpl(this);
        presenter.init();

    }

    public void validation(int count1, int count2, int count3) {
        if (count1 > 0 && count2 > 0 && count3 > 0) {
            okButton.setEnabled(true);
        } else {
            okButton.setEnabled(false);
        }
    }

    private boolean checkNetworkconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

        } else {
            connected = false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetworkconnection();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        return connected;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkNetworkconnection();
    }

    @Override
    public String getEmpId() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getReason() {
        return leaveReason.replace("'","-");
    }

    @Override
    public String getTo_date() {
        return TO_DATE;
    }

    @Override
    public String getFrom_date() {
        String from = input_leave_from.getText().toString().trim();
                    if (!fromDate.equals("")) {
                        return FROM_DATE;

                    }else {
                        return from;
                    }
    }

    @Override
    public String getType() {
        return leaveType;
    }

    @Override
    public String getdays() {
        return String.valueOf(daysDifference);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getapproval_flag() {
        return approval_flag;
    }

    @Override
    public String getPin_no() {
        return strPin_no;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getApplied_date() {
        return formattedDate;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void showLoadingLayout() {
        progressDialog = new ProgressDialog(this){
            @Override
            public void onBackPressed() {
                progressDialog.dismiss();
            }
        };
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
        if (object instanceof AppliedLeavedResponse) {
            AppliedLeavedResponse response = (AppliedLeavedResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    txt_datanotfound.setVisibility(View.GONE);
                    leaveArrayList.addAll(response.getLeave());
                    leaveAdaptor = new LeaveAdaptor(this, leaveArrayList);
                    leaveRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    leaveRecyclerView.setAdapter(leaveAdaptor);

                } else {
                    Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT).show();
                }
            }
        } else if (object instanceof ApplyingLeaveResponse) {
            ApplyingLeaveResponse response = (ApplyingLeaveResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    refreshAppliedList();
                } else {
                    Toast.makeText(getApplicationContext(), notApplied, Toast.LENGTH_SHORT).show();
                    uploadleavelist();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT).show();
            }
        }
        else if (object instanceof EditLeaveResponse) {
            EditLeaveResponse response = (EditLeaveResponse) object;
            Log.e("response",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), "Leave Applied!", Toast.LENGTH_SHORT).show();
                }
                refreshAppliedList();
            } else {
                Toast.makeText(getApplicationContext(), notApplied, Toast.LENGTH_SHORT).show();
                uploadleavelist();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
}
