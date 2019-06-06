package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.EditLeaveView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LeaveEditPresenterImpl implements LoadListener, MainPresenter<EditLeaveView> {
    private EditLeaveView editLeaveView;
    private MainInteractor mainInteractor;

    public LeaveEditPresenterImpl(EditLeaveView editLeaveView) {
        this.editLeaveView = editLeaveView;
    }

    @Override
    public void onSuccess(Object o) {
        editLeaveView.hideLoadingLayout();
        editLeaveView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        editLeaveView.hideLoadingLayout();
        editLeaveView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        editLeaveView.showLoadingLayout();
        mainInteractor = new LeaveEditPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","edit_leave");
        param.put("key",editLeaveView.getKey());
        param.put("emplid",editLeaveView.getEmpId());
        param.put("pin_no",editLeaveView.getPin_no());
        param.put("type",editLeaveView.getType());
        param.put("from",editLeaveView.getFrom_date());
        param.put("to",editLeaveView.getTo_date());
        param.put("days",editLeaveView.getdays());
        param.put("reason",editLeaveView.getReason().replace("'","-"));
        param.put("applied_date",editLeaveView.getApplied_date());
        param.put("id", editLeaveView.getId());
        param.put("approval_flag", "pushback");

        Log.e("ParamsApply",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(EditLeaveView editLeaveView) {
        this.editLeaveView= editLeaveView;
    }

    @Override
    public void unSubscribe() {
        editLeaveView=null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }
}
