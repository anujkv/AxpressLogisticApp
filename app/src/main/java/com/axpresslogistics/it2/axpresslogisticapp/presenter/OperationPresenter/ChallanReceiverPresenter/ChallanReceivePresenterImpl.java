package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.ChallanReceiverPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.ChallanRec.ChallanReceiveView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ChallanReceivePresenterImpl implements LoadListener, MainPresenter<ChallanReceiveView> {
    private MainInteractor mainInteractor;
    private ChallanReceiveView challanReceiveView;

    public ChallanReceivePresenterImpl(ChallanReceiveView challanReceiveView) {
        this.challanReceiveView = challanReceiveView;
    }


    @Override
    public void onSuccess(Object o) {
        challanReceiveView.hideLoadingLayout();
        challanReceiveView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        challanReceiveView.hideLoadingLayout();
        challanReceiveView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        challanReceiveView.showLoadingLayout();
        mainInteractor = new ChallanReceivePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","challan_reciver");
        param.put("key",challanReceiveView.getKey());
        param.put("barcode",challanReceiveView.getBarcode());
        param.put("db_name",challanReceiveView.getDBName());
        Log.e("ChallanReceiverParams",new Gson().toJson(param));

        return param;
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(ChallanReceiveView challanReceiveView) {
        this.challanReceiveView = challanReceiveView;
    }

    @Override
    public void unSubscribe() {
        challanReceiveView = null;
    }
}
