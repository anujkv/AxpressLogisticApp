package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.NewVisitFormView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class NewVisitFormPresenterImpl  implements LoadListener, MainPresenter<NewVisitFormView> {
    private MainInteractor mainInteractor;
    private NewVisitFormView newVisitFormView;

    public NewVisitFormPresenterImpl(NewVisitFormView newVisitFormView) {
        this.newVisitFormView = newVisitFormView;
    }

    @Override
    public void onSuccess(Object o) {
        newVisitFormView.hideLoadingLayout();
        newVisitFormView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        newVisitFormView.hideLoadingLayout();
        newVisitFormView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        newVisitFormView.showLoadingLayout();
        mainInteractor = new NewVisitFormPresenter(getHeader(), getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(NewVisitFormView newVisitFormView) {
        this.newVisitFormView = newVisitFormView;
    }

    @Override
    public void unSubscribe() {
        newVisitFormView = null;
    }

    public Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method","customer_visit_add");
        param.put("key",newVisitFormView.getKey());
        param.put("emplid",newVisitFormView.getEmp_id());
        param.put("address",newVisitFormView.getAddress());
        param.put("visit_for",newVisitFormView.getVisitFor());
        param.put("scope",newVisitFormView.getScope());
        param.put("visit_date",newVisitFormView.getVisitDate());
        param.put("remark",newVisitFormView.getRemark());
        param.put("contact_person",newVisitFormView.getContactPerson());
        param.put("email_id",newVisitFormView.getEmailid());
        param.put("contact",newVisitFormView.getContact());
        param.put("status",newVisitFormView.getStatus());
        param.put("visit_type",newVisitFormView.getVisitType());
        param.put("other_employee_name",newVisitFormView.getOtherEmpName());
        param.put("customer",newVisitFormView.getCustomer());
        param.put("product",newVisitFormView.getProduct());
        param.put("db_name",newVisitFormView.getDBName());
        Log.e("Request",new Gson().toJson(param));
        return param;
    }
}
