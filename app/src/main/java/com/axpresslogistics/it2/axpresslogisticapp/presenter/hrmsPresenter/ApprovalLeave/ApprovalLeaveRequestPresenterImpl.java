package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApprovalLeave;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.ApprovalLeaveView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApprovalLeaveRequestPresenterImpl implements LoadListener, MainPresenter<ApprovalLeaveView> {
    MainInteractor mainInteractor;
    ApprovalLeaveView approvalLeaveView;

    public ApprovalLeaveRequestPresenterImpl(ApprovalLeaveView approvalLeaveView) {
        this.approvalLeaveView = approvalLeaveView;
    }


    @Override
    public void onSuccess(Object o) {
        approvalLeaveView.hideLoadingLayout();
        approvalLeaveView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        approvalLeaveView.hideLoadingLayout();
        approvalLeaveView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        approvalLeaveView.showLoadingLayout();
        mainInteractor = new ApprovalLeaveRequestPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method",approvalLeaveView.getMethod());
        param.put("key",approvalLeaveView.getkey());
        param.put("supervisior_emplid",approvalLeaveView.getSuperviser_id());
        Log.e("params",new Gson().toJson(param));
        return param;
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(ApprovalLeaveView approvalLeave) {
        this.approvalLeaveView = approvalLeave;
    }

    @Override
    public void unSubscribe() {
        approvalLeaveView = null;
    }
}
