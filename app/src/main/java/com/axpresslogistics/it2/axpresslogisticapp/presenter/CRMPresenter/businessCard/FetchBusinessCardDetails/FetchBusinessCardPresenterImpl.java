package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.FetchBusinessCardDetails;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.FetchBusinessCardView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FetchBusinessCardPresenterImpl implements LoadListener, MainPresenter<FetchBusinessCardView> {
    private MainInteractor mainInteractor;
    private FetchBusinessCardView fetchBusinessCardView;

    public FetchBusinessCardPresenterImpl(FetchBusinessCardView fetchBusinessCardView) {
        this.fetchBusinessCardView = fetchBusinessCardView;
    }

    @Override
    public void onSuccess(Object o) {
        fetchBusinessCardView.hideLoadingLayout();
        fetchBusinessCardView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        fetchBusinessCardView.hideLoadingLayout();
        fetchBusinessCardView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        fetchBusinessCardView.showLoadingLayout();
        mainInteractor = new FetchBusinessCardPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(FetchBusinessCardView fetchBusinessCardView) {
        this.fetchBusinessCardView = fetchBusinessCardView;

    }

    @Override
    public void unSubscribe() {
        fetchBusinessCardView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","show_business_card");
        param.put("key",fetchBusinessCardView.getKey());
        param.put("card_id", fetchBusinessCardView.getCardId());
        Log.e("Params", new Gson().toJson(param));

        return param;
    }
}
