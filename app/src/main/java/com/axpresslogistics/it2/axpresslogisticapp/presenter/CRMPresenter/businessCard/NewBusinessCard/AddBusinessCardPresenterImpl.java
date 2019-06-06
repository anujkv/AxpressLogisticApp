package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.NewBusinessCard;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.AddBusinessCardView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AddBusinessCardPresenterImpl  implements LoadListener, MainPresenter<AddBusinessCardView> {
    private MainInteractor mainInteractor;
    private AddBusinessCardView addBusinessCardView;

    public AddBusinessCardPresenterImpl(AddBusinessCardView addBusinessCardView) {
        this.addBusinessCardView = addBusinessCardView;
    }

    @Override
    public void onSuccess(Object o) {

        addBusinessCardView.hideLoadingLayout();
        addBusinessCardView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        addBusinessCardView.hideLoadingLayout();
        addBusinessCardView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        addBusinessCardView.showLoadingLayout();
        mainInteractor = new AddBusinessCardPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(AddBusinessCardView addBusinessCardView) {
        this.addBusinessCardView = addBusinessCardView;

    }

    @Override
    public void unSubscribe() {
        addBusinessCardView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method","business_card_save");
        param.put("key",addBusinessCardView.getKey());
        param.put("created_by",addBusinessCardView.getCreatedBy());
        param.put("frontimage", addBusinessCardView.getFrontImage());
        param.put("backimage", addBusinessCardView.getBackImage());
        param.put("name", addBusinessCardView.getName());
        param.put("job_title", addBusinessCardView.getJobTitle());
        param.put("organization", addBusinessCardView.getCompany());
        param.put("mobile1", addBusinessCardView.getMobile1());
        param.put("mobile2", addBusinessCardView.getMobile2());
        param.put("telephone", addBusinessCardView.getTel());
        param.put("email", addBusinessCardView.getEmail());
        param.put("fax", addBusinessCardView.getFax());
        param.put("website", addBusinessCardView.getWeb());
        param.put("image_barcode", addBusinessCardView.getQRCode());
        param.put("address", addBusinessCardView.getAddress());
        Log.e("Params", new Gson().toJson(param));

        return param;
    }
}
