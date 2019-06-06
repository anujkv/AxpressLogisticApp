package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.BrokerListView;

import java.util.HashMap;
import java.util.Map;

public class BrokerListPresenterImpl implements LoadListener, MainPresenter<BrokerListView> {
    private MainInteractor mainInteractor;
    private BrokerListView brokerListView;

    public BrokerListPresenterImpl(BrokerListView brokerListView) {
        this.brokerListView = brokerListView;
    }

    @Override
    public void onSuccess(Object o) {
        brokerListView.hideLoadingLayout();
        brokerListView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        brokerListView.hideLoadingLayout();
        brokerListView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        brokerListView.showLoadingLayout();
        mainInteractor = new BrokerListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(BrokerListView brokerListView) {
        this.brokerListView = brokerListView;
    }

    @Override
    public void unSubscribe() {
        brokerListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","savedbrokerlist");
        param.put("key",brokerListView.getKey());
        param.put("emplid",brokerListView.getEmplid());
        param.put("db_name",brokerListView.getDBName());
        return param;
    }
}
