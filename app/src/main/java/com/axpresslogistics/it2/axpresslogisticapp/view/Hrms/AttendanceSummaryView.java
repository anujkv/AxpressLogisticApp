package com.axpresslogistics.it2.axpresslogisticapp.view.Hrms;


import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface AttendanceSummaryView extends MainView {
    String getEmpid();

    String getMethod();

    String getKey();

    String getMonth();

    String getYear();
}
