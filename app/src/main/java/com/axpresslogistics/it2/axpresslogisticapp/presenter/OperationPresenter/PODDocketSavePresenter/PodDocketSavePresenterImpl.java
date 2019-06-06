package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.PODDocketSavePresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.PodDocketView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PodDocketSavePresenterImpl  implements LoadListener, MainPresenter<PodDocketView> {

    private MainInteractor mainInteractor;
    private PodDocketView podDocketView;

    public PodDocketSavePresenterImpl(PodDocketView podDocketView) {
        this.podDocketView = podDocketView;
    }

    @Override
    public void onSuccess(Object o) {
        podDocketView.hideLoadingLayout();
        podDocketView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        podDocketView.hideLoadingLayout();
        podDocketView.showFailure(errorMessage);
    }

    @Override
    public void init() {

        podDocketView.showLoadingLayout();
        mainInteractor = new PodDocketPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    public Map<String,String> getBody(){
        Map<String,String> param = new HashMap<>();
        param.put("method","PODShow");
        param.put("key",podDocketView.getKey());
        param.put("dwbno",podDocketView.getDwbNo());
        param.put("db_name",podDocketView.getDBName());

        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    public Map<String,String> getHeader(){
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void subscribe(PodDocketView podDocketView) {
        this.podDocketView = podDocketView;
    }

    @Override
    public void unSubscribe() {
        podDocketView = null;
    }
}
