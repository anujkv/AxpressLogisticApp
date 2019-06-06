package com.axpresslogistics.it2.axpresslogisticapp.view.BaseView;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface ResetPasswordView extends MainView {
    String getEmplid();
    String getEmail();
    String getDOB();
    String getMethod();
    String getKey();

}
