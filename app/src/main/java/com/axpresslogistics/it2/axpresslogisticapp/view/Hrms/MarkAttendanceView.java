package com.axpresslogistics.it2.axpresslogisticapp.view.Hrms;


import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface MarkAttendanceView extends MainView {
    String getLatitude();

    String getLongitude();

    String getDate_time();

    String getEmp_id();

    String getKey();

    String getLocation();

    String getMethod();
}
