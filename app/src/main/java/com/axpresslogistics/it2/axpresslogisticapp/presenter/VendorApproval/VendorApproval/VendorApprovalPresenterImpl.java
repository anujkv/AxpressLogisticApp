package com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorApproval;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval.VendorApprovalRequest;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorApprovalView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VendorApprovalPresenterImpl implements MainPresenter<VendorApprovalView>, LoadListener {
    private MainInteractor mainInteractor;
    private VendorApprovalView vendorApprovalView;
    VendorApprovalRequest vendorApprovalRequest; 

    public VendorApprovalPresenterImpl(VendorApprovalView vendorApprovalView) {
        this.vendorApprovalView = vendorApprovalView;
    }

    @Override
    public void onSuccess(Object o) {
        vendorApprovalView.hideLoadingLayout();
        vendorApprovalView.showSuccess(o);
        Log.e("Response", new Gson().toJson(o));

    }

    @Override
    public void onFailure(String errorMessage) {
        vendorApprovalView.hideLoadingLayout();
        vendorApprovalView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        {
            if (isValid()){
                vendorApprovalView.showLoadingLayout();
                mainInteractor = new VendorApprovalPresenter(getHeader(),vendorApprovalRequest);
                mainInteractor.loadItems(this);
            }
        }
    }

    private boolean isValid() {
//        getBody();
//        return true;
        vendorApprovalRequest = new VendorApprovalRequest();
        vendorApprovalRequest.setApprovalStatus(vendorApprovalView.getApprovalStatus());
        vendorApprovalRequest.setDbName(vendorApprovalView.getDBName());
        vendorApprovalRequest.setEmplid(vendorApprovalView.getEmplid());
        vendorApprovalRequest.setMethod("vendor_vehicle_approveORreject");
        vendorApprovalRequest.setRequestNo(vendorApprovalView.getRequestNo());
        vendorApprovalRequest.setVendorVehicleAttachRequestRateDetailses(vendorApprovalView.getVendorVehicleAttachRequestRateDetailses());
        Log.e("Params", new Gson().toJson(vendorApprovalRequest));
        return true;
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("approval_status", vendorApprovalView.getApprovalStatus());
        param.put("method", "vendor_vehicle_approveORreject");
        param.put("request_no",vendorApprovalView.getRequestNo());
        param.put("emplid",vendorApprovalView.getEmplid());
        param.put("db_name",vendorApprovalView.getDBName());
        param.put("VendorVehicleAttachRequestRateDetailses",new Gson().toJson(vendorApprovalView.getVendorVehicleAttachRequestRateDetailses()));
        Log.e("params", new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(VendorApprovalView vendorApprovalView) {
        this.vendorApprovalView = vendorApprovalView;
    }

    @Override
    public void unSubscribe() {
        vendorApprovalView = null;
    }
}