package com.axpresslogistics.it2.axpresslogisticapp.presenter.Login;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.LoginView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenterImpl implements LoadListener, MainPresenter<LoginView> {
    private MainInteractor mainInteractor;
    private LoginView loginView;
//    private LoginRequest loginRequest;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }


    @Override
    public void init() {
        if(isVaild()){
            loginView.showLoadingLayout();
            mainInteractor = new LoginPresenter(getHeader(),getBody());
            mainInteractor.loadItems(this);
        }
    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","login");
        param.put("key",loginView.getkey());
        param.put("username",loginView.getUsername());
        param.put("pwd",loginView.getPassword());
        param.put("refreshed_token",loginView.getrefreshtoken());
        param.put("imei_no",loginView.getimei());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    private boolean isVaild() {
        getBody();
        return true;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void unSubscribe() {
        loginView = null;
    }

    @Override
    public void onSuccess(Object o) {
        loginView.hideLoadingLayout();
        loginView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.hideLoadingLayout();
        loginView.showFailure(errorMessage);

    }
}
