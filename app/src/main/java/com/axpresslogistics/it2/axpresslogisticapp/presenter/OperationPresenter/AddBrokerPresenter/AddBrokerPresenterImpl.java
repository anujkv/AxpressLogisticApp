package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.AddBrokerPresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.AddBrokerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AddBrokerPresenterImpl implements MainPresenter<AddBrokerView>, LoadListener {
    private MainInteractor interactor;
    private AddBrokerView   addBrokerView;

    public AddBrokerPresenterImpl(AddBrokerView addBrokerView) {
        this.addBrokerView = addBrokerView;
    }

    @Override
    public void onSuccess(Object o) {
        addBrokerView.hideLoadingLayout();
        addBrokerView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        addBrokerView.hideLoadingLayout();
        addBrokerView.showFailure(errorMessage);

    }

    @Override
    public void init()
    {if(isValid())
        addBrokerView.showLoadingLayout();
        interactor = new AddBrokerPresenter(getBody(),getHeader());
        interactor.loadItems(this);
    }

    @Override
    public void subscribe(AddBrokerView addBrokerView) {
        this.addBrokerView = addBrokerView;
    }

    @Override
    public void unSubscribe() {
        addBrokerView = null;
    }

    private boolean isValid(){
        getBody();
        return true;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","add_broker");
        param.put("key",addBrokerView.getKey());
        param.put("created_by",addBrokerView.getCreated_by());
        param.put("pan_no",addBrokerView.getPan_no());
        param.put("bank_account_no",addBrokerView.getBank_Account_No());
        param.put("created_on",addBrokerView.getCreated_On());
        param.put("alternate_contact",addBrokerView.getAlternate_COntact());
        param.put("branch",addBrokerView.getBranch());
        param.put("bank_name",addBrokerView.getBank_name());
        param.put("email_id",addBrokerView.getEmail_id());
        param.put("bank_ifsc",addBrokerView.getBank_Ifsc());
        param.put("contact",addBrokerView.getContact());
        param.put("broker_name",addBrokerView.getBroker_name());
        param.put("name_on_pancard",addBrokerView.getName_on_PanCard());
        param.put("active_status",addBrokerView.getActive_Status());
        param.put("address",addBrokerView.getAddreass());
        param.put("db_name",addBrokerView.getDBName());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }
}
