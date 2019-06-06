package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.BrokerListSearchView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BrokerSearchPresenterImpl implements LoadListener, MainPresenter<BrokerListSearchView> {
    private MainInteractor mainInteractor;
    private BrokerListSearchView listSearchView;

    public BrokerSearchPresenterImpl(BrokerListSearchView listSearchView) {
        this.listSearchView = listSearchView;
    }

    @Override
    public void onSuccess(Object o) {
        listSearchView.hideLoadingLayout();
        listSearchView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        listSearchView.hideLoadingLayout();
        listSearchView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        listSearchView.showLoadingLayout();
        mainInteractor = new BrokerSearchPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","search_broker");
        param.put("key",listSearchView.getKey());
        param.put("emplid",listSearchView.getEmplid());
        param.put("input",listSearchView.getInput());
        param.put("db_name",listSearchView.getDBName());
        Log.e("ParamsSearch",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(BrokerListSearchView brokerListSearchView) {
        this.listSearchView = brokerListSearchView;
    }

    @Override
    public void unSubscribe() {
        listSearchView = null;
    }
}
