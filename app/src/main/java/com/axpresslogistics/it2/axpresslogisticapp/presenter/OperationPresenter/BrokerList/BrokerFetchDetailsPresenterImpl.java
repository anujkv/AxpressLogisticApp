package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.FetchBrokerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BrokerFetchDetailsPresenterImpl implements MainPresenter<FetchBrokerView>, LoadListener {
    private MainInteractor interactor;
    private FetchBrokerView fetchBrokerView;

    public BrokerFetchDetailsPresenterImpl(FetchBrokerView fetchBrokerView) {
        this.fetchBrokerView = fetchBrokerView;
    }

    @Override
    public void onSuccess(Object o) {
        fetchBrokerView.hideLoadingLayout();
        fetchBrokerView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        fetchBrokerView.hideLoadingLayout();
        fetchBrokerView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        if (isValid())
            fetchBrokerView.showLoadingLayout();
        interactor = new BrokerFetchDetailsPresenter(getBody(), getHeader());
        interactor.loadItems(this);
    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method", "broker_detail_fetch");
        param.put("broker_code", fetchBrokerView.getBroker_Code());
        param.put("key", fetchBrokerView.getKey());
        param.put("db_name",fetchBrokerView.getDBName());
        Log.e("ParamsFetch",new Gson().toJson(param));
        return param;
    }

    private boolean isValid() {
        getBody();
        return true;
    }

    public Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    @Override
    public void subscribe(FetchBrokerView fetchBrokerView) {
        this.fetchBrokerView = fetchBrokerView;
    }


    @Override
    public void unSubscribe() {
        fetchBrokerView = null;
    }
}
