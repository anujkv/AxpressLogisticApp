package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.PODScanPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.PODScanV.PODScannerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PodScanPresenterImpl implements LoadListener, MainPresenter<PODScannerView> {

    private MainInteractor mainInteractor;
    private PODScannerView podScannerView;

    public PodScanPresenterImpl(PODScannerView podScannerView) {
        this.podScannerView = podScannerView;
    }

    @Override
    public void onSuccess(Object o) {
        podScannerView.hideLoadingLayout();
        podScannerView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        podScannerView.hideLoadingLayout();
        podScannerView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        podScannerView.showLoadingLayout();
        mainInteractor = new PodScanPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    private Map<String,String> getBody() {
        Map<String,String> param = new HashMap<>();
        param.put("docket_no",podScannerView.getDocketNo());
        param.put("emplid",podScannerView.getEmplid());
        param.put("image",podScannerView.getImage());
//        param.put("imageType",podScannerView.getImageType());
        param.put("imageType",".PNG");
        param.put("db_name",podScannerView.getDBName());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    private  Map<String,String> getHeader() {
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(PODScannerView podScannerView) {
        this.podScannerView = podScannerView;
    }

    @Override
    public void unSubscribe() {
        podScannerView = null;
    }
}
