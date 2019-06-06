package com.axpresslogistics.it2.axpresslogisticapp.view.BaseView;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface LoginView extends MainView {

    String getMethod();

    String getUsername();

    String getPassword();

    String getimei();

    String getrefreshtoken();

    String getkey();
}
