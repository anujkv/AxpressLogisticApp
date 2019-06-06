package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormHistoryView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VisitFormHistoryPresenterImpl implements LoadListener, MainPresenter<VisitFormHistoryView> {
    private MainInteractor mainInteractor;
    private VisitFormHistoryView visitFormHistoryView;

    public VisitFormHistoryPresenterImpl(VisitFormHistoryView visitFormHistoryView) {
        this.visitFormHistoryView = visitFormHistoryView;
    }

    @Override
    public void onSuccess(Object o) {
        visitFormHistoryView.hideLoadingLayout();
        visitFormHistoryView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        visitFormHistoryView.hideLoadingLayout();
        visitFormHistoryView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        visitFormHistoryView.showLoadingLayout();
        mainInteractor = new VisitFormHistoryPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(VisitFormHistoryView visitFormHistoryView) {
        this.visitFormHistoryView = visitFormHistoryView;
    }

    @Override
    public void unSubscribe() {
        visitFormHistoryView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","show_visit");
        param.put("key",visitFormHistoryView.getKey());
        param.put("ref_no",visitFormHistoryView.getRefNo());
        param.put("db_name",visitFormHistoryView.getDBName());
        Log.e("RequsetHistory",new Gson().toJson(param));
        return param;
    }

}
