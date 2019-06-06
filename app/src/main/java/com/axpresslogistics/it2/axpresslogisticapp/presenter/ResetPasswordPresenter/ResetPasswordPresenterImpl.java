package com.axpresslogistics.it2.axpresslogisticapp.presenter.ResetPasswordPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.Profile.ProfileImageUploadPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ResetPasswordView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordPresenterImpl implements LoadListener, MainPresenter<ResetPasswordView> {
    MainInteractor mainInteractor;
    ResetPasswordView resetPasswordView;

    public ResetPasswordPresenterImpl(ResetPasswordView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Override
    public void onSuccess(Object o) {
        resetPasswordView.hideLoadingLayout();
        resetPasswordView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        resetPasswordView.hideLoadingLayout();
        resetPasswordView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        resetPasswordView.showLoadingLayout();
        mainInteractor = new ResetPasswordPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    private Map<String, String> getBody() {

        Map<String, String> params = new HashMap<>();
        params.put("method","reset");
        params.put("key",resetPasswordView.getKey());
        params.put("employee_id",resetPasswordView.getEmplid());
        params.put("employee_email",resetPasswordView.getEmail());
        params.put("employee_dob",resetPasswordView.getDOB());
        Log.e("Request",new Gson().toJson(params));
        return params;

    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(ResetPasswordView reserPasswordView) {
        this.resetPasswordView = reserPasswordView;
    }

    @Override
    public void unSubscribe() {
        resetPasswordView = null;
    }
}
