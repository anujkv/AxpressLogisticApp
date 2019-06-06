package com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.ToDoListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.ToDoListModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListModelList;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.todolist.ToDoListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoListView;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ProjectUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ToDoList extends AppCompatActivity implements ToDoListView {
    public static final int NEW_TODO_REQUEST_CODE = 200;
    String emplid;
    RecyclerView recyclerView;
    LinearLayout layout;
    List<ToDoListModelList> toDoListModels = new ArrayList<ToDoListModelList>();
    ToDoListAdaptor toDoListAdaptor;

    FloatingActionButton floatingActionButton;
    boolean isNewTodo = false;
    MainPresenter presenter;
    String apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("To Do List");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ApiKey obj = new ApiKey();
        apikey = obj.saltStr();
        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        init();
        FirebaseMessaging.getInstance().subscribeToTopic("NEW_REMINDER");
        show_list();

          floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ToDoList.this,
                        NewToDo.class), NEW_TODO_REQUEST_CODE);
            }



        });
    }

    private void show_list(){
        presenter = new ToDoListPresenterImpl(this);
        presenter.init();

    }

    private void checkDate(String datee) {
        String Datee = datee;
        Date alarmDate = new Date();

        String start_dt = "12/15/2018 6:25:00 PM";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
        Date date = null;
        try {
            date = (Date)formatter.parse(start_dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss a");
        String finalString = newFormat.format(date);
        Log.e("Time",finalString);
    }

    private void init() {
        floatingActionButton = findViewById(R.id.fab);
        layout = findViewById(R.id.no_list_layout);

        recyclerView = findViewById(R.id.todo_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoListModels = new ArrayList<ToDoListModelList>(); }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh();
    }

    private void refresh() {
        if(toDoListModels.size()>0){
            toDoListModels.clear();
            show_list();
        }
    }

    @Override
    public String getEmp_id()
    {
        return Preferences.getPreference(this, CONSTANT.EMPID);
    }

    @Override
    public String getKey()
    {
        return apikey;
    }

    @Override
    public String getMethod() {

        return "saved_reminder_list";
    }

    @Override
    public void showLoadingLayout() {


    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {

        if(object instanceof ToDoListModel)
        {
            ToDoListModel response = (ToDoListModel)object;
            if(response!=null) {
                if(response.getMethod().equals("saved_reminder_list")) {
                    if(toDoListModels.size()!=0) {
                        toDoListModels.clear();
                    }
                 //   ProjectUtil.customToast(this, "Successfully");
                    toDoListModels.addAll(response.getReminderList());
                    toDoListAdaptor = new ToDoListAdaptor(this,toDoListModels);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(toDoListAdaptor);
                    recyclerView.setVisibility(View.VISIBLE);


                } else{
                    ProjectUtil.customToast(this, "Data not available");

                }
            }

        }

    }

    @Override
    public void showFailure(String error) {

    }
}
