package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.ChallanReceiverPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.ChallanRec.ChallanCloseView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ChallanClosingPresenterImpl  implements LoadListener, MainPresenter<ChallanCloseView> {
    private MainInteractor mainInteractor;
    private ChallanCloseView challanCloseView;

    public ChallanClosingPresenterImpl(ChallanCloseView challanCloseView) {
        this.challanCloseView = challanCloseView;
    }

    @Override
    public void onSuccess(Object o) {
        challanCloseView.hideLoadingLayout();
        challanCloseView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        challanCloseView.hideLoadingLayout();
        challanCloseView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        challanCloseView.showLoadingLayout();
        mainInteractor = new ChallanClosingPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","challan_closing");
        param.put("key",challanCloseView.getKey());
        param.put("closing_date",challanCloseView.getClosing_date());
        param.put("challan_no",challanCloseView.getChallan_no());
        param.put("emplid",challanCloseView.getEmp_id());
        param.put("remark",challanCloseView.getRemark());
        param.put("db_name",challanCloseView.getDBName());
        Log.e("ReceiverParams",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(ChallanCloseView challanCloseView) {
        this.challanCloseView = challanCloseView;
    }

    @Override
    public void unSubscribe() {
        challanCloseView = null;
    }
}
