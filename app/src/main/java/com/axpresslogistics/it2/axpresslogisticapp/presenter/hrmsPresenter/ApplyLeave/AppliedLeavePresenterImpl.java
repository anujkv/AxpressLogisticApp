package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.AppliedLeavedView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AppliedLeavePresenterImpl implements LoadListener, MainPresenter<AppliedLeavedView> {
    MainInteractor mainInteractor;
    AppliedLeavedView appliedLeavedView;

    public AppliedLeavePresenterImpl(AppliedLeavedView appliedLeavedView) {
        this.appliedLeavedView = appliedLeavedView;
    }

    @Override
    public void onSuccess(Object o) {
        appliedLeavedView.hideLoadingLayout();
        appliedLeavedView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        appliedLeavedView.hideLoadingLayout();
        appliedLeavedView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        appliedLeavedView.showLoadingLayout();
        mainInteractor = new AppliedLeavePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method",appliedLeavedView.getMethod());
        param.put("key",appliedLeavedView.getKey());
        param.put("emplid",appliedLeavedView.getEmpId());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(AppliedLeavedView appliedLeavedView) {
        this.appliedLeavedView = appliedLeavedView;
    }

    @Override
    public void unSubscribe() {
        appliedLeavedView = null;
    }
}
