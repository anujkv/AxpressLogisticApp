package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.BusinessCardList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.BusinessCardListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BusinessCardListPresenterImpl implements LoadListener, MainPresenter<BusinessCardListView> {
    private MainInteractor mainInteractor;
    private BusinessCardListView businessCardListView;

    public BusinessCardListPresenterImpl(BusinessCardListView businessCardListView) {
        this.businessCardListView = businessCardListView;
    }

    @Override
    public void onSuccess(Object o) {
        businessCardListView.hideLoadingLayout();
        businessCardListView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        businessCardListView.hideLoadingLayout();
        businessCardListView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        businessCardListView.showLoadingLayout();
        mainInteractor = new BusinessCardListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(BusinessCardListView businessCardListView) {
        this.businessCardListView = businessCardListView;
    }

    @Override
    public void unSubscribe() {
        businessCardListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","business_card_list");
        param.put("key",businessCardListView.getKey());
        param.put("created_by",businessCardListView.getCreatedBy());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }
}
