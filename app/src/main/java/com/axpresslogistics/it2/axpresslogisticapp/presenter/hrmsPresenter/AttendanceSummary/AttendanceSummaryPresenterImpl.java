package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.AttendanceSummary;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.AttendanceSummaryView;

import java.util.HashMap;
import java.util.Map;

public class AttendanceSummaryPresenterImpl  implements LoadListener, MainPresenter<AttendanceSummaryView> {
    private MainInteractor mainInteractor;
    private AttendanceSummaryView attandanceSummaryView;

    public AttendanceSummaryPresenterImpl(AttendanceSummaryView attandanceSummaryView) {
        this.attandanceSummaryView = attandanceSummaryView;
    }

    @Override
    public void onSuccess(Object o) {
        attandanceSummaryView.hideLoadingLayout();
        attandanceSummaryView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        attandanceSummaryView.hideLoadingLayout();
        attandanceSummaryView.showFailure(errorMessage);
    }

    @Override
    public void init() {
        attandanceSummaryView.showLoadingLayout();
        mainInteractor = new AttendanceSummaryPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);

    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method",attandanceSummaryView.getMethod());
        param.put("key",attandanceSummaryView.getKey());
        param.put("emplid",attandanceSummaryView.getEmpid());
        param.put("month",attandanceSummaryView.getMonth());
        param.put("year",attandanceSummaryView.getYear());
        return param;
    }

    @Override
    public void subscribe(AttendanceSummaryView attendanceSummaryView) {
        this.attandanceSummaryView = attandanceSummaryView;
    }

    @Override
    public void unSubscribe() {
        attandanceSummaryView = null;
    }
}
