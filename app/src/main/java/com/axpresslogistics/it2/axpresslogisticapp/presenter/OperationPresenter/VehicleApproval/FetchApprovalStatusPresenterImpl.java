package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.FetchApprovalStatusView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FetchApprovalStatusPresenterImpl implements LoadListener, MainPresenter<FetchApprovalStatusView> {
    private MainInteractor mainInteractor;
    private FetchApprovalStatusView fetchApprovalStatusView;

    public FetchApprovalStatusPresenterImpl(FetchApprovalStatusView fetchApprovalStatusView)
    {
        this.fetchApprovalStatusView = fetchApprovalStatusView;
    }

    @Override
    public void onSuccess(Object o) {
        fetchApprovalStatusView.hideLoadingLayout();
        fetchApprovalStatusView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        fetchApprovalStatusView.hideLoadingLayout();
        fetchApprovalStatusView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        fetchApprovalStatusView.showLoadingLayout();
        mainInteractor = new FetchApprovalStatusPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(FetchApprovalStatusView fetchApprovalStatusView) {
        this.fetchApprovalStatusView = fetchApprovalStatusView;
    }

    @Override
    public void unSubscribe() {
        fetchApprovalStatusView = null;
    }


    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","fetch_approval_details");
        param.put("key",fetchApprovalStatusView.getKey());
        param.put("emplid",fetchApprovalStatusView.getEmplid());
        param.put("vehicle_request_code",fetchApprovalStatusView.getVehical_request_code());
        param.put("branch_code",fetchApprovalStatusView.getBranchCode());
        param.put("db_name",fetchApprovalStatusView.getDBName());
        Log.e("Request",new Gson().toJson(param));
        return param;
    }
}
