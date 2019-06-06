package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.UpdateBrokerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BrokerUpdatePresenterImpl implements MainPresenter<UpdateBrokerView>, LoadListener {
    private MainInteractor interactor;
    private UpdateBrokerView updateBrokerView;

    public BrokerUpdatePresenterImpl(UpdateBrokerView updateBrokerView) {
        this.updateBrokerView = updateBrokerView;
    }

    @Override
    public void onSuccess(Object o) {
        updateBrokerView.hideLoadingLayout();
        updateBrokerView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        updateBrokerView.hideLoadingLayout();
        updateBrokerView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        {if(isValid())
            updateBrokerView.showLoadingLayout();
            interactor = new BrokerUpdatePresenter(getBody(),getHeader());
            interactor.loadItems(this);
        }
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method",updateBrokerView.getMethod());
        param.put("key",updateBrokerView.getKey());
        param.put("modified_by",updateBrokerView.getModified_by());
        param.put("pan_no",updateBrokerView.getPan_no());
        param.put("bank_account_no",updateBrokerView.getBank_Account_No());
        param.put("modified_on",updateBrokerView.getModified_by());
        param.put("alternate_contact",updateBrokerView.getAlternate_COntact());
        param.put("branch",updateBrokerView.getBranch());
        param.put("bank_name",updateBrokerView.getBank_name());
        param.put("email_id",updateBrokerView.getEmail_id());
        param.put("bank_ifsc",updateBrokerView.getBank_Ifsc());
        param.put("contact",updateBrokerView.getContact());
        param.put("broker_name",updateBrokerView.getBroker_name());
        param.put("name_on_pancard",updateBrokerView.getName_on_PanCard());
        param.put("active_status",updateBrokerView.getActive_Status());
        param.put("address",updateBrokerView.getAddreass());
        param.put("broker_code",updateBrokerView.getBroker_code());
        param.put("db_name",updateBrokerView.getDBName());
        Log.e("UpdateResponse",new Gson().toJson(param));
        return param;
    }


    private boolean isValid() {
        getBody();
        return true;
    }

    @Override
    public void subscribe(UpdateBrokerView updateBrokerView) {
        this.updateBrokerView = updateBrokerView;
    }

    @Override
    public void unSubscribe() {
        updateBrokerView = null;
    }
}
