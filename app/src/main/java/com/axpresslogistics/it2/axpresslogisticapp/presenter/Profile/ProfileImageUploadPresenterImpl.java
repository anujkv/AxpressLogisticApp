package com.axpresslogistics.it2.axpresslogisticapp.presenter.Profile;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ProfileImageUploadView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileImageUploadPresenterImpl implements LoadListener, MainPresenter<ProfileImageUploadView> {
    private MainInteractor mainInteractor;
    private ProfileImageUploadView imageUploadView;

    public ProfileImageUploadPresenterImpl(ProfileImageUploadView imageUploadView) {
        this.imageUploadView = imageUploadView;
    }

    @Override
    public void onSuccess(Object o) {
        imageUploadView.hideLoadingLayout();
        imageUploadView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        imageUploadView.hideLoadingLayout();
        imageUploadView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        imageUploadView.showLoadingLayout();
        mainInteractor = new ProfileImageUploadPresenter(getHeader(),getBody(), getImage(), getType());
        mainInteractor.loadItems(this);

    }

    private RequestBody getType() {
        return imageUploadView.getType();
    }

    private MultipartBody.Part getImage() {
        return imageUploadView.getImage();
    }

    private Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    private Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method",imageUploadView.getMethod());
        param.put("key",imageUploadView.getKey());
        param.put("emplid",imageUploadView.getEmplid());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }

    @Override
    public void subscribe(ProfileImageUploadView profileImageUploadView) {
        this.imageUploadView = profileImageUploadView;
    }

    @Override
    public void unSubscribe() {
        imageUploadView = null;
    }
}
