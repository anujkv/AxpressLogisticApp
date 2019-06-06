package com.axpresslogistics.it2.axpresslogisticapp.presenter.Profile;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ProfileView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ProfilePresenterImpl implements LoadListener, MainPresenter<ProfileView> {
    private MainInteractor mainInteractor;
    private ProfileView profileView;

    public ProfilePresenterImpl(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void onSuccess(Object o) {
        profileView.hideLoadingLayout();
        profileView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        profileView.hideLoadingLayout();
        profileView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        profileView.showLoadingLayout();
        mainInteractor = new ProfilePresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void unSubscribe() {
        profileView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method",profileView.getMethod());
        param.put("key",profileView.getKey());
        param.put("emplid",profileView.getEmpid());
        Log.e("Params",new Gson().toJson(param));
        return param;
    }
}
