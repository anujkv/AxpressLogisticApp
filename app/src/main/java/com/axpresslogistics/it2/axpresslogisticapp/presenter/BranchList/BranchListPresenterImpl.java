package com.axpresslogistics.it2.axpresslogisticapp.presenter.BranchList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BranchListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BranchListPresenterImpl implements MainPresenter<BranchListView>, LoadListener {
    private MainInteractor interactor;
    private BranchListView branchListView;

    public BranchListPresenterImpl(BranchListView newTicketView) {
        this.branchListView = newTicketView;
    }

    @Override
    public void onSuccess(Object o) {
        branchListView.hideLoadingLayout();
        branchListView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        branchListView.hideLoadingLayout();
        branchListView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        if(isValid())
        branchListView.showLoadingLayout();
        interactor = new BranchListPresenter(getBody(),getHeader());
        interactor.loadItems(this);
    }

    public Map<String,String> getHeader(){
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String,String> getBody(){
        Map<String,String> param = new HashMap<>();
        param.put("method","to_branch_list");
        param.put("key",branchListView.getKey());
        param.put("db_name",branchListView.getDBName());
        Log.e("params",new Gson().toJson(param));
        return param;
    }

    private boolean isValid() {
        getBody();
        return true;
    }

    @Override
    public void subscribe(BranchListView branchListView) {
        this.branchListView = branchListView;
    }

    @Override
    public void unSubscribe() {
        branchListView = null;
    }
}
