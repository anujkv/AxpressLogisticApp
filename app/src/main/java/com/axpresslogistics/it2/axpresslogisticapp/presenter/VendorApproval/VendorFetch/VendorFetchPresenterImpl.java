package com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorFetch;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorFetchDetailsView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VendorFetchPresenterImpl implements MainPresenter<VendorFetchDetailsView>, LoadListener {
    private MainInteractor mainInteractor;
    private VendorFetchDetailsView vendorFetchDetailsView;

    public VendorFetchPresenterImpl(VendorFetchDetailsView vendorFetchDetailsView) {
        this.vendorFetchDetailsView = vendorFetchDetailsView;
    }

    @Override
    public void onSuccess(Object o) {
        vendorFetchDetailsView.hideLoadingLayout();
        vendorFetchDetailsView.showSuccess(o);
        Log.e("Response", new Gson().toJson(o));

    }

    @Override
    public void onFailure(String errorMessage) {
        vendorFetchDetailsView.hideLoadingLayout();
        vendorFetchDetailsView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        {
            if (isValid()){
                vendorFetchDetailsView.showLoadingLayout();
                mainInteractor = new VendorFetchPresenter(getHeader(),getBody());
                mainInteractor.loadItems(this);
            }
        }
    }

    private boolean isValid() {
        getBody();
        return true;
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method", "vendor_vehicle_detail");
        param.put("request_no", vendorFetchDetailsView.getRequestNo());
        param.put("emplid",vendorFetchDetailsView.getEmplid());
        param.put("key",vendorFetchDetailsView.getKey());
        param.put("db_name",vendorFetchDetailsView.getDBName());
//        param.put("db_name","AxpressERP");
        Log.e("params", new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(VendorFetchDetailsView vendorFetchDetailsView) {
        this.vendorFetchDetailsView = vendorFetchDetailsView;
    }

    @Override
    public void unSubscribe() {
        vendorFetchDetailsView = null;
    }

}
