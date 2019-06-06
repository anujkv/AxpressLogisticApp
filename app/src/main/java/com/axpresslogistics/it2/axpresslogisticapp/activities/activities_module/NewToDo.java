package com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.ContactAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.MemberContactAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelList;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListSendModelResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.todoadd.ToDoListAddPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.todolistsend.ToDoListSendPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDiListSendView;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoListAddView;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ProjectUtil;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NewToDo extends AppCompatActivity implements View.OnClickListener, ToDoListAddView, ToDiListSendView {
    private static final int NOTIFICATION = 1;
    Context context;

    String reminderUriString = "content://com.android.calendar/reminders";
    Button done_btn, search_btn;
    RecyclerView recyclerView;
    // ToDoListAddModelList
    List<ToDoListAddModelList> modelList = new ArrayList<ToDoListAddModelList>();
    List<ToDoListAddModelList> memberList = new ArrayList<ToDoListAddModelList>();
    List<ToDoListAddModelList> list;
    ContactAdaptor contactAdaptor;
    MemberContactAdaptor memberAdaptor;
    ToDoListAddModelList contactModel, contactModel1, memberModel;
    ImageButton close_id, save, addContact_btn;
    ImageView on_alarm, off_alarm;
    EditText to_do_add, reminder_date, reminder_time, search_contact;
    TextView reminder_text_id;
    String to_do_note, date_msg = "", date_final_msg = "", time_msg = "", created_time, created_by;
    Boolean DATE_FLAG = false, TIME_FLAG = false;
    String date = "";
    String reminder_note = "";
    String responseMethod = "";
    AlertDialog.Builder builder;
    AlertDialog dialog;
    RecyclerView contact_members_recyclerview;
    StringBuilder data_contact;
    int mode, hours, min, sec, stopYear, stopMonth, stopDay, stopHours, stopMin;
    Switch switch_reminder;
    Calendar myCalendar;
    LinearLayout reminder_date_time;
    private int mYear, mMonth, mDay, mHour, mMinute, startYear, startMonth, startDay;
    ProgressDialog progressDialog;
    String str_switch_reminder;
    final static int req1 = 1;
    public String a = "0";
    int H = 0, M = 0;
    View alertLayout;
    MainPresenter presenter;
    String apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);

        init();
        FirebaseInstanceId.getInstance().getToken();
        final ApiKey obj = new ApiKey();
        apikey = obj.saltStr();
        switch_reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reminder_date.setText("");
                    reminder_time.setText("");
                    enable_reminder();
                    switch_reminder.setTextOn("ON");
                } else {
                    disable_reminder();
                    switch_reminder.setTextOn("OFF");
                    reminder_date.setText("");
                    reminder_time.setText("");
                }
            }
        });
    }

    private void contact_data_fetch() {
        presenter = new ToDoListAddPresenterImpl(this);
        presenter.init();

    }

    private void init() {
        close_id = findViewById(R.id.close_id);
        close_id.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        addContact_btn = findViewById(R.id.addContact_btn);
        addContact_btn.setOnClickListener(this);
        addContact_btn.setClickable(false);
        contact_members_recyclerview = findViewById(R.id.contact_members_recyclerview);
        contact_members_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        contact_members_recyclerview.setHasFixedSize(true);
        //  memberList = new ArrayList<>();

        on_alarm = findViewById(R.id.on_alarm);
        off_alarm = findViewById(R.id.off_alarm);
        created_by = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);

        to_do_add = findViewById(R.id.to_do_add);
        reminder_text_id = findViewById(R.id.reminder_text_id);
        reminder_date = findViewById(R.id.edittext_date);
        reminder_date.setOnClickListener(this);
        reminder_time = findViewById(R.id.edittext_time);
        reminder_time.setOnClickListener(this);
        switch_reminder = findViewById(R.id.switch_reminder);
        myCalendar = Calendar.getInstance();
        reminder_date_time = findViewById(R.id.reminder_date_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_id:
                finish();
                break;
            case R.id.save:
                conditions();
                break;
            case R.id.edittext_date:
                if (DATE_FLAG.equals(true)) {
                    calender();
                }
                break;
            case R.id.edittext_time:
                if (TIME_FLAG.equals(true)) {
                    watch();
                }
                break;
            case R.id.addContact_btn:
                memberList.clear();
                contact_data_fetch();
                break;
        }
    }

    private void contact_list() {
        LayoutInflater inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.activity_add_contacts, null);
        recyclerView = alertLayout.findViewById(R.id.contact_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //  modelList = new ArrayList<>();

        builder = new AlertDialog.Builder(alertLayout.getContext());
        builder.setTitle("Contacts");
        builder.setView(alertLayout);
        builder.setCancelable(false);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                progressDialog.dismiss();
                data_contact = new StringBuilder();
                try {
                    list = ((ContactAdaptor) contactAdaptor).getContactModels();
                    for (int i = 0; i < list.size(); i++) {
                        contactModel1 = (ToDoListAddModelList) list.get(i);
                        if (contactModel1.isSelected()) {
                            //get list in data_contact;
                            data_contact.append("\n").append(contactModel1.getEmplid());
                            data_contact.append(" - ").append(contactModel1.getName());
                            memberModel = new ToDoListAddModelList(
                                    contactModel1.getEmplid(),
                                    contactModel1.getName(),
                                    contactModel1.getContact(),
                                    contactModel1.getEmail(),
                                    contactModel1.getDeptCode(),
                                    contactModel1.getBranchCode());
                            memberList.add(memberModel);
                        }
                    }
                    memberAdaptor = new MemberContactAdaptor(getApplicationContext(), memberList);
                    contact_members_recyclerview.setAdapter(memberAdaptor);
                    Log.e("ListContact", memberList.toString());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }


    private void conditions() {
        to_do_note = to_do_add.getText().toString().trim();
        if (to_do_note.equals("") || to_do_note == null) {
            to_do_add.setError("enter the to do note!");
            to_do_add.setTextColor(Color.YELLOW);
        } else {
            if (switch_reminder.isChecked()) {
                if (!date_msg.equals("") && !time_msg.equals("")) {
                    try {
                        String mm = "reminder";
                        save(mm);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    reminder_date.setError("set the reminder date & time");
                }
            } else {
                String mm = "note";
                save(mm);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void disable_reminder() {
        DATE_FLAG = false;
        TIME_FLAG = false;
        reminder_text_id.setText("");
        addContact_btn.setClickable(false);
        addContact_btn.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        memberList.clear();
        on_alarm.setVisibility(View.GONE);
        off_alarm.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enable_reminder() {
        DATE_FLAG = true;
        TIME_FLAG = true;
        addContact_btn.setClickable(true);

        on_alarm.setVisibility(View.VISIBLE);
        off_alarm.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addContact_btn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
        } else {
            addContact_btn.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        }
    }

    private void watch() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mode = c.get(Calendar.AM_PM);
        stopHours = mHour;
        stopMin = mMinute;
        final String AM_PM;
        if (mode == 0) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if (hourOfDay < 10) {
                            H = Integer.parseInt("0" + hourOfDay);
                        } else {
                            H = hourOfDay;
                        }
                        if (minute < 10) {
                            M = Integer.parseInt("0" + minute);
                        } else {
                            M = minute;
                        }
                        Log.e("Hour", String.valueOf(hourOfDay));
                        Log.e("Min", String.valueOf(minute));
                        Log.e("H", String.valueOf(H));
                        reminder_time.setText(H + ":" + M);
                        time_msg = H + ":" + M;
                        reminder_text_id.setText("");
                        if ((date_final_msg == null) || (date_final_msg.equals(""))) {
                            String current_date = "Reminder set on " + mDay + "-" + mMonth + "-" + mYear;
                            reminder_text_id.setText(String.format("%s,  %s", current_date, time_msg));

                        } else {
                            reminder_text_id.setText(String.format("%s,  %s", date_final_msg, time_msg));
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void calender() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Date time = c.getTime();
        hours = time.getHours();
        min = time.getMinutes();
        startYear = mYear;
        startMonth = mMonth + 1;
        startDay = mDay;
        created_time = String.valueOf(time);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        stopYear = year;
                        stopMonth = monthOfYear + 1;
                        stopDay = dayOfMonth;
                        reminder_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date_msg = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        set_date(date_msg);
                        watch();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void set_date(String date_msg) {
        SimpleDateFormat newformat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat oldformat = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate;

        try {
            myDate = oldformat.parse(String.valueOf(date_msg));
            date = newformat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date_final_msg = "Reminder set on " + date;
        reminder_text_id.setText(date_final_msg);
        reminder_date.setText(date);
        Log.d("date_msg", date_msg);
    }

    private void set_Remainder() {
        presenter = new ToDoListSendPresenterImpl(this);
        presenter.init();

    }

    private void save(String mm) {
        if (mm.equals("reminder")) {
            //store in sqlite & set alarm
            to_do_note = to_do_add.getText().toString().trim();
            push_reminder(to_do_note, date_msg, time_msg, memberList);
            finish();

        } else {
            finish();
        }
    }


    private void push_reminder(String to_do_note, String date_msg, String time_msg, List<ToDoListAddModelList> memberList) {
        this.to_do_note = to_do_note;
        this.date_msg = date_msg;
        this.time_msg = time_msg;
        this.memberList = memberList;

        Intent i = new Intent("MY_INTENT");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.HOUR, 00);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
        if (switch_reminder.isChecked()) {
            str_switch_reminder = "on";
            Log.e("Current Time", "IS CHECKED");
            set_Remainder();
            // Remainder(to_do_note, str_switch_reminder, created_by, date + " " + time_msg, memberList);
        } else {
            str_switch_reminder = "off";
            Log.e("Current Time", "IS Not CHECKED");
            Date currentTime = Calendar.getInstance().getTime();
            String current_date = String.valueOf(currentTime);
            Log.e("Current Time", current_date);
            Log.e("Current Date Time", current_date);
            set_Remainder();
            //Remainder(to_do_note, str_switch_reminder, created_by, current_date, memberList);
        }
    }


    public void scheduleAlarm(String title, String body) {
        Log.e("BODY", body);
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, H);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, M);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, to_do_note);
        Log.e("ALARM", H + ":" + M);
        startActivity(intent);
    }


    @Override
    public String getCreated_by() {
        return created_by;
    }

    @Override
    public String getDate_time() {

        return date + " " + time_msg;
    }

    @Override
    public String getKey() {
        return apikey;
    }

    @Override
    public String getMethod() {

        return "contact_list";
    }

    @Override
    public String getNote() {
        return to_do_note;
    }

    @Override
    public String getReminder_Switch() {
        return str_switch_reminder;
    }

    @Override
    public List<ToDoListAddModelList> getContact() {
        return memberList;
    }

    @Override
    public String getkey() {
        return apikey;
    }

    @Override
    public void showLoadingLayout() {


    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {

        if (object instanceof ToDoListAddModelResponse) {
            ToDoListAddModelResponse response = (ToDoListAddModelResponse) object;
            if (response != null) {
                if (response.getMethod().equals("contact_list")) {
                    if (modelList.size() != 0) {
                        modelList.clear();
                    }
                   // ProjectUtil.customToast(this, "Successfully");
                    contact_list();
                    modelList.addAll(response.getContact());
                    contactAdaptor = new ContactAdaptor(this, modelList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(contactAdaptor);
                    recyclerView.setVisibility(View.VISIBLE);


                } else {
                    ProjectUtil.customToast(this, "Data not available");
                }
            }
        }

        else if (object instanceof ToDoListSendModelResponse) {
                       ToDoListSendModelResponse toDoListSendModelResponse = (ToDoListSendModelResponse) object;
                    if (toDoListSendModelResponse != null) {
                        if (toDoListSendModelResponse.getMethod().equals("reminder")) {
                          /*  if (modelList.size() != 0) {
                                modelList.clear();
                            }*/

                          //  ProjectUtil.customToast(this, "Successfully");
                            scheduleAlarm(to_do_note, date + " " + time_msg);
                            Toast.makeText(NewToDo.this,"reminder set successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(NewToDo.this,"Some thing Went Wrong! try Again", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        ProjectUtil.customToast(this, "Data not available");


                }

            }


    }

    @Override
    public void showFailure(String error) {

    }
}