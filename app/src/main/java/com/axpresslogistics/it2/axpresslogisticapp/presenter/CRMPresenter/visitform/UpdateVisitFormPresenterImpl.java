package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.UpdateVisitFormView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UpdateVisitFormPresenterImpl implements LoadListener, MainPresenter<UpdateVisitFormView> {
    MainInteractor mainInteractor;
    UpdateVisitFormView updateVisitFormView;

    public UpdateVisitFormPresenterImpl(UpdateVisitFormView updateVisitFormView) {
        this.updateVisitFormView = updateVisitFormView;
    }


    @Override
    public void onSuccess(Object o) {
        updateVisitFormView.hideLoadingLayout();
        updateVisitFormView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        updateVisitFormView.hideLoadingLayout();
        updateVisitFormView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        updateVisitFormView.showLoadingLayout();
        mainInteractor = new UpdateVisitFormPresenter(getHeader(), getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(UpdateVisitFormView updateVisitFormView) {
        this.updateVisitFormView = updateVisitFormView;
    }

    @Override
    public void unSubscribe() {
        updateVisitFormView = null;
    }

    public Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }


    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method", "customer_visit_update");
        param.put("key", updateVisitFormView.getKey());
        param.put("emplid", updateVisitFormView.getEmp_id());
        param.put("address", updateVisitFormView.getAddress());
        param.put("visit_for", updateVisitFormView.getVisitFor());
        param.put("scope", updateVisitFormView.getScope());
        param.put("visit_date", updateVisitFormView.getVisitDate());
        param.put("remark", updateVisitFormView.getRemark());
        param.put("contact_person", updateVisitFormView.getContactPerson());
        param.put("email_id", updateVisitFormView.getEmailid());
        param.put("contact", updateVisitFormView.getContact());
        param.put("status", updateVisitFormView.getVisitStatus());
        param.put("visit_type", updateVisitFormView.getVisitType());
        param.put("other_employee_name", updateVisitFormView.getOtherEmpName());
        param.put("customer", updateVisitFormView.getCustomer());
        param.put("product", updateVisitFormView.getProduct());
        param.put("ref_no", updateVisitFormView.getRef_no());
        param.put("db_name",updateVisitFormView.getDBName());
        Log.e("RequestUpdate", new Gson().toJson(param));
        return param;
    }
}
