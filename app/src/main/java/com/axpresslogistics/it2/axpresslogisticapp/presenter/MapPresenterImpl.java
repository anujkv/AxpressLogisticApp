package com.axpresslogistics.it2.axpresslogisticapp.presenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.view.MapView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MapPresenterImpl implements LoadListener, MainPresenter<MapView> {
    MainInteractor mainInteractor;
    MapView mapView;

    public MapPresenterImpl(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void onSuccess(Object o) {
        mapView.hideLoadingLayout();
        mapView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        mapView.hideLoadingLayout();
        mapView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        mapView.showLoadingLayout();
        mainInteractor = new MapPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    private Map<String, String> getHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("content-type","application/json");
        return params;
    }

    private Map<String, String> getBody() {
        Map<String, String> params = new HashMap<>();
        params.put("docket_no",mapView.getDocketNo());
        params.put("method","map");
        params.put("key",mapView.getKey());
        Log.e("Params",new Gson().toJson(params));
        return params;
    }

    @Override
    public void subscribe(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void unSubscribe() {
        mapView = null;
    }
}
