package com.axpresslogistics.it2.axpresslogisticapp.presenter.HRApprovalPresent;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.HRApprovalView;

import java.util.HashMap;
import java.util.Map;

public class HRApprovalPresenterImpl implements LoadListener, MainPresenter<HRApprovalView> {
    MainInteractor mainInteractor;
    HRApprovalView hrApprovalView;

    public HRApprovalPresenterImpl(HRApprovalView hrApprovalView) {
        this.hrApprovalView = hrApprovalView;
    }

    @Override
    public void onSuccess(Object o) {
        hrApprovalView.hideLoadingLayout();
        hrApprovalView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        hrApprovalView.hideLoadingLayout();
        hrApprovalView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        hrApprovalView.showLoadingLayout();
        mainInteractor = new HRApprovalPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    private Map<String, String> getHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("content-type","application/json");
        return params;
    }

    private Map<String, String> getBody() {
        Map<String, String> params = new HashMap<>();
        params.put("method",hrApprovalView.getHRMethod());
        params.put("key",hrApprovalView.getKey());
        params.put("emplid",hrApprovalView.getEmplid());
        params.put("data",hrApprovalView.getData());
        params.put("address_type",hrApprovalView.getAddressType());
        return params;
    }

    @Override
    public void subscribe(HRApprovalView hrApprovalView) {
        this.hrApprovalView = hrApprovalView;
    }

    @Override
    public void unSubscribe() {
        hrApprovalView = null;
    }
}
