package com.axpresslogistics.it2.axpresslogisticapp.presenter.UploadDoc;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.UploadDocument.UploadDocumentsView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UploadDocumentsPresenterImpl implements MainPresenter<UploadDocumentsView>, LoadListener {

    private MainInteractor interactor;
    private UploadDocumentsView uploadDocumentsView;

    public UploadDocumentsPresenterImpl(UploadDocumentsView uploadDocumentsView) {
        this.uploadDocumentsView = uploadDocumentsView;
    }

    @Override
    public void onSuccess(Object o) {
        uploadDocumentsView.hideLoadingLayout();
        uploadDocumentsView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        uploadDocumentsView.hideLoadingLayout();
        uploadDocumentsView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        {if(isValid())
            uploadDocumentsView.showLoadingLayout();
            interactor = (MainInteractor) new UploadDocumentsPresenter(getHeader(),getBody());
            interactor.loadItems(this);
        }
    }


    @Override
    public void subscribe(UploadDocumentsView uploadDocumentsView) {
        this.uploadDocumentsView = uploadDocumentsView;
    }

    @Override
    public void unSubscribe() {
        uploadDocumentsView = null;
    }

    public Map<String,String> getHeader(){
        Map<String,String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String,String> getBody(){
        Map<String,String> param = new HashMap<>();
        param.put("emplid",uploadDocumentsView.getEmplid());
        param.put("aadharcard",uploadDocumentsView.getAadharcardImage());
        param.put("pancard",uploadDocumentsView.getPancardImage());
        param.put("photograph",uploadDocumentsView.getPhotographImage());
        Log.e("params",new Gson().toJson(param));
        return param;
    }
    private boolean isValid() {
        getBody();
        return true;
    }
}
