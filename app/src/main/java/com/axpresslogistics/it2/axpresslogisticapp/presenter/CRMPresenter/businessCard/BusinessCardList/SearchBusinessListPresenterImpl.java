package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.BusinessCardList;


import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.SearchBusinessCardListView;

import java.util.HashMap;
import java.util.Map;

public class SearchBusinessListPresenterImpl implements LoadListener, MainPresenter<SearchBusinessCardListView> {
    private MainInteractor mainInteractor;
    private SearchBusinessCardListView searchBusinessCardListView;

    public SearchBusinessListPresenterImpl(SearchBusinessCardListView searchBusinessCardListView) {
        this.searchBusinessCardListView = searchBusinessCardListView;
    }

    @Override
    public void onSuccess(Object o) {
        searchBusinessCardListView.hideLoadingLayout();
        searchBusinessCardListView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        searchBusinessCardListView.hideLoadingLayout();
        searchBusinessCardListView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        searchBusinessCardListView.showLoadingLayout();
        mainInteractor = new BusinessCardListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(SearchBusinessCardListView searchBusinessCardListView) {
        this.searchBusinessCardListView = searchBusinessCardListView;

    }

    @Override
    public void unSubscribe() {
        searchBusinessCardListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","card_search");
        param.put("key",searchBusinessCardListView.getKey());
        param.put("emplid",searchBusinessCardListView.getEmplid());
        param.put("input",searchBusinessCardListView.getInput());

        return param;
    }
}
