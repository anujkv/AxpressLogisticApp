package com.axpresslogistics.it2.axpresslogisticapp.presenter.AppRolePresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.ResetPasswordPresenter.ResetPasswordPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.AppRoleView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AppRolePresenterImpl implements LoadListener, MainPresenter<AppRoleView> {
    MainInteractor mainInteractor;
    AppRoleView appRoleView;

    public AppRolePresenterImpl(AppRoleView appRoleView) {
        this.appRoleView = appRoleView;
    }


    @Override
    public void onSuccess(Object o) {
        appRoleView.hideLoadingLayout();
        appRoleView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        appRoleView.hideLoadingLayout();
        appRoleView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        appRoleView.showLoadingLayout();
        mainInteractor = new AppRolePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    private Map<String, String> getBody() {
        Map<String, String> params = new HashMap<>();
        params.put("emplid",appRoleView.getEmplid());
        params.put("method","app_role");
        params.put("key",appRoleView.getKey());
        Log.e("Params",new Gson().toJson(params));
        return params;
    }

    private Map<String, String> getHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("content-type","application/json");
        return params;
    }

    @Override
    public void subscribe(AppRoleView appRoleView) {
        this.appRoleView = appRoleView;
    }

    @Override
    public void unSubscribe() {
        appRoleView = null;
    }
}
