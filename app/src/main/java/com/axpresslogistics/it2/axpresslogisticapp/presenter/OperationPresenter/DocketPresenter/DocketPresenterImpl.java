package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.DocketPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.DocketEnquiryView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DocketPresenterImpl implements LoadListener, MainPresenter<DocketEnquiryView> {
    private MainInteractor mainInteractor;
    private DocketEnquiryView  docketEnquiryView;

    public DocketPresenterImpl(DocketEnquiryView docketEnquiryView) {
        this.docketEnquiryView = docketEnquiryView;
    }

    @Override
    public void onSuccess(Object o) {
        docketEnquiryView.hideLoadingLayout();
        docketEnquiryView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        docketEnquiryView.hideLoadingLayout();
        docketEnquiryView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        docketEnquiryView.showLoadingLayout();
        mainInteractor = new DocketPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    public Map<String,String> getHeader(){
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String,String> getBody(){
        Map<String,String> param = new HashMap<>();
        param.put("method","docket");
        param.put("key",docketEnquiryView.getKey());
        param.put("docket_no",docketEnquiryView.getInput());
        param.put("db_name",docketEnquiryView.getDBName());

        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(DocketEnquiryView docketEnquiryView) {
        this.docketEnquiryView = docketEnquiryView;
    }

    @Override
    public void unSubscribe() {
        docketEnquiryView = null;
    }
}
