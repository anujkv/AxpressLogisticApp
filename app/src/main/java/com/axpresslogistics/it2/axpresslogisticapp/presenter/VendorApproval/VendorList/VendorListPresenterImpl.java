package com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorViewList;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VendorListPresenterImpl implements MainPresenter<VendorViewList>, LoadListener {
    private MainInteractor mainInteractor;
    private VendorViewList vendorViewList;

    public VendorListPresenterImpl(VendorViewList vendorViewList) {
        this.vendorViewList = vendorViewList;
    }

    @Override
    public void onSuccess(Object o) {
        vendorViewList.hideLoadingLayout();
        vendorViewList.showSuccess(o);
        Log.e("Response", new Gson().toJson(o));

    }

    @Override
    public void onFailure(String errorMessage) {
        vendorViewList.hideLoadingLayout();
        vendorViewList.showFailure(errorMessage);

    }

    @Override
    public void init() {
        {
            if (isValid()){
                vendorViewList.showLoadingLayout();
                mainInteractor = new VendorListPresenter(getHeader(),getBody());
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
        param.put("emplid", vendorViewList.getEmplid());
        param.put("method", "vendor_list");
        param.put("db_name",vendorViewList.getDBName());
//        param.put("db_name","AxpressERP");
        Log.e("params", new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(VendorViewList vendorViewList) {
        this.vendorViewList = vendorViewList;
    }

    @Override
    public void unSubscribe() {
        vendorViewList = null;
    }

}
