package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.VehicleApprovedListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VehicleApprovedListPresenterImpl implements LoadListener, MainPresenter<VehicleApprovedListView> {
    private MainInteractor mainInteractor;
    private VehicleApprovedListView vehicleApprovedListView;

    public VehicleApprovedListPresenterImpl(VehicleApprovedListView vehicleApprovedListView) {
        this.vehicleApprovedListView = vehicleApprovedListView;
    }

    @Override
    public void onSuccess(Object o) {
        vehicleApprovedListView.hideLoadingLayout();
        vehicleApprovedListView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        vehicleApprovedListView.hideLoadingLayout();
        vehicleApprovedListView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        vehicleApprovedListView.showLoadingLayout();
        mainInteractor = new VehicleApprovedListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(VehicleApprovedListView vehicleApprovedListView) {
        this.vehicleApprovedListView = vehicleApprovedListView;

    }

    @Override
    public void unSubscribe() {
        vehicleApprovedListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","approved_method");
        param.put("key",vehicleApprovedListView.getKey());
        param.put("emplid",vehicleApprovedListView.getEmplid());
        param.put("branch_code",vehicleApprovedListView.getBranchCode());
        param.put("actual_wt_of_goods",vehicleApprovedListView.getActual_wt_of_goods());
        param.put("from_branch",vehicleApprovedListView.getFromBranch());
        param.put("request_code",vehicleApprovedListView.getRequestCode());
        param.put("loading_point",vehicleApprovedListView.getLoading_point());
        param.put("approved_status",vehicleApprovedListView.getapproved_status());
        param.put("broker_remark",vehicleApprovedListView.getbroker_remark());
        param.put("broker_code",vehicleApprovedListView.getbroker_code());
        param.put("requirement_type",vehicleApprovedListView.getrequirement_type());
        param.put("unloading_point",vehicleApprovedListView.getunloading_point());
        param.put("to_branch",vehicleApprovedListView.getToBranch());
        param.put("broker_name",vehicleApprovedListView.getbroker_name());
        param.put("broker_rate",vehicleApprovedListView.getbroker_rate());
        param.put("db_name",vehicleApprovedListView.getDBName());
        Log.e("ParamsApproved", new Gson().toJson(param));
        return param;
    }
}
