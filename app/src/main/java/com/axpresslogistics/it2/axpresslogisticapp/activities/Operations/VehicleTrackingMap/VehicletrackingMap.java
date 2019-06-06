package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleTrackingMap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.axpresslogistics.it2.axpresslogisticapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class VehicletrackingMap extends AppCompatActivity implements View.OnClickListener {
    WebView vehiclewebview;
    EditText search_vehicle_edittext;
    ImageButton backbtn_toolbar, searchbtn_toolbar;
    String vehicle_no = "HR47C4094";
    String from_date = "2018-09-21 09:47";
    String to_date = "2018-09-21 14:00";
//    String webview_url = "http://219.90.67.196/jsp/VehicleTracking.jsp?vehicle_no=HR47C4094&from_datetime=2018-09-21 09:47&to_datetime=2018-09-21 09:47";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicletracking_map);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        search_vehicle_edittext = findViewById(R.id.searchedt_toolbar);
//        TextView lable = findViewById(R.id.title_toolbar);
//        lable.setText(CONSTANT.VEHICLE_TRACKING);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);
        vehiclewebview = findViewById(R.id.vehiclewebview);

        calling_map(vehicle_no);

    }

    private void calling_map(String vehicle_no) {
        vehiclewebview.getSettings().setJavaScriptEnabled(true);
        to_date = currentDate();
        vehiclewebview.loadUrl("http://219.90.67.196/jsp/VehicleTracking.jsp?vehicle_no=" +
                vehicle_no + "&from_datetime=" + from_date + "&to_datetime=" + to_date);
        vehiclewebview.setHorizontalScrollBarEnabled(false);
    }

    private String currentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.searchbtn_toolbar:
//                search_vehicle_edittext.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                        inputListModelList.clear();
////                        visitModelList.clear();
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        Log.e("ATCs : ", s.toString());
////                        hitSearchAPi(s.toString());
//                    }
//                });
//                if(search_vehicle_edittext.getText() != null){
                vehicle_no = search_vehicle_edittext.getText().toString().toUpperCase().trim();
                Log.e("text>",vehicle_no);
                search_vehicle_edittext.setText(vehicle_no);
                calling_map(vehicle_no);
//                }
                break;
        }
    }
}
