package com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.InvoicePresenter;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.InvoiceEnquiryView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class InvoicePresenterImpl implements LoadListener, MainPresenter<InvoiceEnquiryView> {
    private MainInteractor mainInteractor;
    private InvoiceEnquiryView invoiceEnquriyView;

    public InvoicePresenterImpl(InvoiceEnquiryView invoiceEnquriyView) {
        this.invoiceEnquriyView = invoiceEnquriyView;
    }

    @Override
    public void onSuccess(Object o) {
        invoiceEnquriyView.hideLoadingLayout();
        invoiceEnquriyView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        invoiceEnquriyView.hideLoadingLayout();
        invoiceEnquriyView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        invoiceEnquriyView.showLoadingLayout();
        mainInteractor = new InvoicePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    public Map<String,String> getHeader(){
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String,String> getBody(){
        Map<String,String> param = new HashMap<>();
        param.put("method","invoice_details");
        param.put("key",invoiceEnquriyView.getKey());
        param.put("invoice_no",invoiceEnquriyView.getInvoiceNo());
        param.put("db_name",invoiceEnquriyView.getDBName());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(InvoiceEnquiryView invoiceEnquiryView) {
        this.invoiceEnquriyView = invoiceEnquiryView;
    }

    @Override
    public void unSubscribe() {
        invoiceEnquriyView = null;
    }
}
