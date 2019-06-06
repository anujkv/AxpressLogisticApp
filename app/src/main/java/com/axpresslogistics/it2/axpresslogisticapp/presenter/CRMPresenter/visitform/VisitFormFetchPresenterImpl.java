package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormFetchView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VisitFormFetchPresenterImpl implements LoadListener, MainPresenter<VisitFormFetchView> {
    private MainInteractor mainInteractor;
    private VisitFormFetchView visitFormFetchView;

    public VisitFormFetchPresenterImpl(VisitFormFetchView visitFormFetchView) {
        this.visitFormFetchView = visitFormFetchView;
    }

    @Override
    public void onSuccess(Object o) {
        visitFormFetchView.hideLoadingLayout();
        visitFormFetchView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        visitFormFetchView.hideLoadingLayout();
        visitFormFetchView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        visitFormFetchView.showLoadingLayout();
        mainInteractor = new VisitFormFetchPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(VisitFormFetchView visitFormFetchView) {
        this.visitFormFetchView = visitFormFetchView;
    }

    @Override
    public void unSubscribe() {
        visitFormFetchView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }



    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","customer_visit_search");
        param.put("key",visitFormFetchView.getKey());
        param.put("ref_no",visitFormFetchView.getRef_No());
        param.put("input",visitFormFetchView.getInput());
        param.put("db_name",visitFormFetchView.getDBName());
        Log.e("Request",new Gson().toJson(param));
        return param;
    }
}
