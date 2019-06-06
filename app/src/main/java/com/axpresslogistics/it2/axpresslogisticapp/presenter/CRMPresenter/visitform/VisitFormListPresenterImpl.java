package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VisitFormListPresenterImpl implements LoadListener, MainPresenter<VisitFormListView> {
    private MainInteractor mainInteractor;
    private VisitFormListView visitFormListView;

    public VisitFormListPresenterImpl(VisitFormListView visitFormListView) {
        this.visitFormListView = visitFormListView;
    }

    @Override
    public void onSuccess(Object o) {
        visitFormListView.hideLoadingLayout();
        visitFormListView.showSuccess(o);
        
    }

    @Override
    public void onFailure(String errorMessage) {
        visitFormListView.hideLoadingLayout();
        visitFormListView.showFailure(errorMessage);

    }

    @Override
    public void init() {if(isValid())
        visitFormListView.showLoadingLayout();
        mainInteractor = new VisitFormListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(VisitFormListView visitFormListView) {
        this.visitFormListView = visitFormListView;
    }

    @Override
    public void unSubscribe() {
        visitFormListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","saved_visit_form");
        param.put("key",visitFormListView.getKey());
        param.put("emplid",visitFormListView.getEmplId());
        param.put("db_name",visitFormListView.getDBName());
        Log.e("Request",new Gson().toJson(param));
        return param;
    }

    private boolean isValid() {
        getBody();
        return true;
    }
}
