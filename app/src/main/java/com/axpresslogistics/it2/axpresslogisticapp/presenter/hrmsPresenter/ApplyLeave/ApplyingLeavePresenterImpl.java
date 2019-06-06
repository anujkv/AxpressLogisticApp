package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.ApplyingLeaveView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApplyingLeavePresenterImpl implements LoadListener, MainPresenter<ApplyingLeaveView> {
    MainInteractor mainInteractor;
    ApplyingLeaveView applyingLeaveView;

    public ApplyingLeavePresenterImpl(ApplyingLeaveView applyingLeaveView) {
        this.applyingLeaveView = applyingLeaveView;
    }

    @Override
    public void onSuccess(Object o) {
        applyingLeaveView.hideLoadingLayout();
        applyingLeaveView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        applyingLeaveView.hideLoadingLayout();
        applyingLeaveView.showFailure(errorMessage);
}


    @Override
    public void init() {
        applyingLeaveView.showLoadingLayout();
        mainInteractor = new ApplyingLeavePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","apply_leave");
        param.put("key",applyingLeaveView.getKey());
        param.put("emplid",applyingLeaveView.getEmpId());
        param.put("pin_no",applyingLeaveView.getPin_no());
        param.put("type",applyingLeaveView.getType());
        param.put("from",applyingLeaveView.getFrom_date());
        param.put("to",applyingLeaveView.getTo_date());
        param.put("days",applyingLeaveView.getdays());
        param.put("reason",applyingLeaveView.getReason().replace("'","-"));
        param.put("applied_date",applyingLeaveView.getApplied_date());

        Log.e("ParamsApply",new Gson().toJson(param));
        return param;
    }

    private Map<String, String> getHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("content-type","application/json");
        return params;
    }

    @Override
    public void subscribe(ApplyingLeaveView applyingLeaveView) {
        this.applyingLeaveView = applyingLeaveView;
    }

    @Override
    public void unSubscribe() {
        applyingLeaveView = null;
    }
}