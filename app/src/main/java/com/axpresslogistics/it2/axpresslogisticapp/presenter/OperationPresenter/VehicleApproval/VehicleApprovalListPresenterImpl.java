package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.VehicleApprovalListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VehicleApprovalListPresenterImpl  implements LoadListener, MainPresenter<VehicleApprovalListView> {
    private MainInteractor mainInteractor;
    private VehicleApprovalListView vehicleApprovalListView;

    public VehicleApprovalListPresenterImpl(VehicleApprovalListView vehicleApprovalListView) {
        this.vehicleApprovalListView = vehicleApprovalListView;
    }

    @Override
    public void onSuccess(Object o) {
        vehicleApprovalListView.hideLoadingLayout();
        vehicleApprovalListView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        vehicleApprovalListView.hideLoadingLayout();
        vehicleApprovalListView.showFailure(errorMessage);

    }

    @Override
    public void init() {

        vehicleApprovalListView.showLoadingLayout();
        mainInteractor = new VehicleApprovalListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(VehicleApprovalListView vehicleApprovalListView) {
        this.vehicleApprovalListView = vehicleApprovalListView;
    }

    @Override
    public void unSubscribe() {
        vehicleApprovalListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","saved_vehicle_list");
        param.put("key",vehicleApprovalListView.getKey());
        param.put("emplid",vehicleApprovalListView.getEmplid());
        param.put("branch_code",vehicleApprovalListView.getBranchCode());
        param.put("db_name",vehicleApprovalListView.getDBName());
        Log.e("RequestApproval",new Gson().toJson(param));
        return param;
    }
}
