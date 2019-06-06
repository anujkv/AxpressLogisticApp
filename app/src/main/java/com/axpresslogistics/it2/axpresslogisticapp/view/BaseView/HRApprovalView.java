package com.axpresslogistics.it2.axpresslogisticapp.view.BaseView;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface HRApprovalView extends MainView {
    String getHRMethod();
    String getKey();
    String getEmplid();
    String getData();
    String getAddressType();
}
