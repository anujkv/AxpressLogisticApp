package com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.businessCard.UpdateBusinessCard;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView.UpdateBusinessCardDetailsView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UpdateBusinessCardPresenterImpl implements LoadListener, MainPresenter<UpdateBusinessCardDetailsView> {
    private MainInteractor mainInteractor;
    private UpdateBusinessCardDetailsView updateBusinessCardDetailsView;

    public UpdateBusinessCardPresenterImpl(UpdateBusinessCardDetailsView updateBusinessCardDetailsView) {
        this.updateBusinessCardDetailsView = updateBusinessCardDetailsView;
    }

    @Override
    public void onSuccess(Object o) {

        updateBusinessCardDetailsView.hideLoadingLayout();
        updateBusinessCardDetailsView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
        updateBusinessCardDetailsView.hideLoadingLayout();
        updateBusinessCardDetailsView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        updateBusinessCardDetailsView.showLoadingLayout();
        mainInteractor = new UpdateBusinessCardPresenter(getHeader(), getBody());
        mainInteractor.loadItems(this);

    }

    @Override
    public void subscribe(UpdateBusinessCardDetailsView updateBusinessCardDetailsView) {
        this.updateBusinessCardDetailsView = updateBusinessCardDetailsView;
    }


    @Override
    public void unSubscribe() {
        updateBusinessCardDetailsView = null;
    }

    public Map<String, String> getHeader() {
        Map<String, String> param = new HashMap<>();
        param.put("content-type", "application/json");
        return param;
    }

    public Map<String, String> getBody() {
        Map<String, String> param = new HashMap<>();
        param.put("method", "business_card_update");
        param.put("key", updateBusinessCardDetailsView.getKey());
        param.put("card_id", updateBusinessCardDetailsView.getCardId());
        param.put("created_by", updateBusinessCardDetailsView.getCreatedBy());
        param.put("frontimage", updateBusinessCardDetailsView.getFrontImage());
        param.put("backimage", updateBusinessCardDetailsView.getBackImage());
        param.put("name", updateBusinessCardDetailsView.getName());
        param.put("job_title", updateBusinessCardDetailsView.getJobTitle());
        param.put("organization", updateBusinessCardDetailsView.getCompany());
        param.put("mobile1", updateBusinessCardDetailsView.getMobile1());
        param.put("mobile2", updateBusinessCardDetailsView.getMobile2());
        param.put("telephone", updateBusinessCardDetailsView.getTel());
        param.put("email", updateBusinessCardDetailsView.getEmail());
        param.put("fax", updateBusinessCardDetailsView.getFax());
        param.put("website", updateBusinessCardDetailsView.getWeb());
        param.put("image_barcode", updateBusinessCardDetailsView.getQRCode());
        param.put("address", updateBusinessCardDetailsView.getAddress());
        Log.e("Params", new Gson().toJson(param));

        return param;
    }
}
