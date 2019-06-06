package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.AddVehicleReq;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.AddVehicleView.AddVehicleReqView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AddVehicleReqPresenterImpl implements MainPresenter<AddVehicleReqView>, LoadListener {
    private MainInteractor interactor;
    private AddVehicleReqView addVehicleReqView;

    public AddVehicleReqPresenterImpl(AddVehicleReqView addVehicleReqView) {
        this.addVehicleReqView = addVehicleReqView;
    }

    @Override
    public void onSuccess(Object o) {
        addVehicleReqView.hideLoadingLayout();
        addVehicleReqView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        addVehicleReqView.hideLoadingLayout();
        addVehicleReqView.showFailure(errorMessage);

    }

    @Override
    public void init()
    {if(isValid())
        addVehicleReqView.showLoadingLayout();
        interactor = new AddVehicleReqPresenter(getBody(),getHeader());
        interactor.loadItems(this);
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","add_vehicle_req");
        param.put("key",addVehicleReqView.getKey());
        param.put("db_name",addVehicleReqView.getDBName());
        param.put("vehicle_type",addVehicleReqView.getVehicle_Type());
        param.put("from_branch",addVehicleReqView.getFrom_Branch());
        param.put("req_type",addVehicleReqView.getReq_Type());
        param.put("broker_code3",addVehicleReqView.getBroker_code3());
        param.put("broker_2_advance",addVehicleReqView.getbroker_2_advance());
        param.put("broker_1_advance",addVehicleReqView.getbroker_1_advance());
        param.put("broker_3_rate",addVehicleReqView.getbroker_3_rate());
        param.put("broker_code2",addVehicleReqView.getbroker_code2());
        param.put("broker_name3",addVehicleReqView.getbroker_name3());
        param.put("good_type",addVehicleReqView.getgood_type());
        param.put("db_name","AxpressERP");
        param.put("broker1_contact_no",addVehicleReqView.getbroker1_contact_no());
        param.put("vehicle_capacity",addVehicleReqView.getvehicle_capacity());
        param.put("broker_3_advance",addVehicleReqView.getbroker_3_advance());
        param.put("broker_2_remark",addVehicleReqView.getbroker_2_remark());
        param.put("actual_wt_of_goods",addVehicleReqView.getactual_wt_of_goods());
        param.put("loading_point",addVehicleReqView.getloading_point());
        param.put("broker_2_rate",addVehicleReqView.getbroker_2_rate());
        param.put("broker_name1",addVehicleReqView.getbroker_name1());
        param.put("broker_code1",addVehicleReqView.getbroker_code1());
        param.put("broker2_contact_no",addVehicleReqView.getbroker2_contact_no());
        param.put("broker_1_remark",addVehicleReqView.getbroker_1_remark());
        param.put("broker_3_remark",addVehicleReqView.getbroker_1_remark());
        param.put("market_vehicle_entry_status",addVehicleReqView.getmarket_vehicle_entry_status());
        param.put("broker2_contact_no",addVehicleReqView.getbroker2_contact_no());
        param.put("emplid",addVehicleReqView.getEmp_id());
        param.put("unloading_point",addVehicleReqView.getunloading_point());
        param.put("broker_1_rate",addVehicleReqView.getbroker_1_rate());
        param.put("to_branch",addVehicleReqView.getto_branch());
        param.put("touching_point",addVehicleReqView.gettouching_point());
        param.put("broker_name2",addVehicleReqView.getbroker_name2());
        param.put("broker3_contact_no",addVehicleReqView.getbroker3_contact_no());
        Log.e("ParamsAdd",new Gson().toJson(param));
        return param;
    }

    private boolean isValid() {
        getBody();
        return true;
    }

    @Override
    public void subscribe(AddVehicleReqView addVehicleReqView) {
        this.addVehicleReqView = addVehicleReqView;
    }

    @Override
    public void unSubscribe() {
        addVehicleReqView = null;
    }
}
