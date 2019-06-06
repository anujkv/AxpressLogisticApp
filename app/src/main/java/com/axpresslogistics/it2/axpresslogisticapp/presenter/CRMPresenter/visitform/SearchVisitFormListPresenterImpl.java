package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormListSearchView;

import java.util.HashMap;
import java.util.Map;

public class SearchVisitFormListPresenterImpl implements LoadListener, MainPresenter<VisitFormListSearchView> {
    private MainInteractor mainInteractor;
    private VisitFormListSearchView visitFormListSearchView;


    public SearchVisitFormListPresenterImpl(VisitFormListSearchView visitFormListSearchView) {
        this.visitFormListSearchView = visitFormListSearchView;
    }

    @Override
    public void onSuccess(Object o) {
        visitFormListSearchView.hideLoadingLayout();
        visitFormListSearchView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        visitFormListSearchView.hideLoadingLayout();
        visitFormListSearchView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        visitFormListSearchView.showLoadingLayout();
        mainInteractor = new SearchVisitFormListPresenter(getHeader(), getBody());
        mainInteractor.loadItems(this);
    }


    @Override
    public void subscribe(VisitFormListSearchView visitFormListSearchView) {
        this.visitFormListSearchView = visitFormListSearchView;
    }

    @Override
    public void unSubscribe() {
        visitFormListSearchView = null;
    }


    public Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method", "search");
        param.put("key", visitFormListSearchView.getKey());
        param.put("input", visitFormListSearchView.getInput());
        param.put("db_name",visitFormListSearchView.getDBName());
        return param;
    }
}