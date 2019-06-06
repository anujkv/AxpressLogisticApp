package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.MarkAttendance;


import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.MarkAttendance.MarkAttendanceRequest;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.MarkAttendanceView;

import java.util.HashMap;
import java.util.Map;

public class MarkAttendancePresenterImpl  implements LoadListener, MainPresenter<MarkAttendanceView> {
    private MainInteractor mainInteractor;
    private MarkAttendanceView markAttandanceView;
    private MarkAttendanceRequest markAttandanceRequest;

    public MarkAttendancePresenterImpl(MarkAttendanceView markAttandanceView) {
        this.markAttandanceView = markAttandanceView;
    }

    @Override
    public void onSuccess(Object o) {
        markAttandanceView.hideLoadingLayout();
        markAttandanceView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        markAttandanceView.hideLoadingLayout();
        markAttandanceView.showFailure(errorMessage);
    }

//    @Override
//    public void init() {
//        if(isValid()){
//            markAttandanceView.showLoadingLayout();
//            mainInteractor = new MarkAttendancePresenter(markAttandanceRequest,getHeader());
//            mainInteractor.loadItems(this);
//        }
//    }
//
//    @Override
//    public void subscribe(MarkAttendanceView markAttendanceView) {
//        this.markAttandanceView = markAttendanceView;
//    }
//
//    @Override
//    public void unSubscribe() {
//        markAttandanceView = null;
//    }

    private boolean isValid() {
        markAttandanceRequest =new MarkAttendanceRequest();
        markAttandanceRequest.setLatitude(markAttandanceView.getLatitude());
        markAttandanceRequest.setLongitude(markAttandanceView.getLongitude());
        markAttandanceRequest.setDate_time(markAttandanceView.getDate_time());
        markAttandanceRequest.setEmp_id(markAttandanceView.getEmp_id());
        markAttandanceRequest.setKey(markAttandanceView.getKey());
        markAttandanceRequest.setLocation(markAttandanceView.getLocation());
        markAttandanceRequest.setMethod(markAttandanceView.getMethod());
        return true;
    }


    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }

    @Override
    public void init() {
        if(isValid()){
            markAttandanceView.showLoadingLayout();
            mainInteractor = new MarkAttendancePresenter(markAttandanceRequest,getHeader());
            mainInteractor.loadItems(this);
        }
    }

    @Override
    public void subscribe(MarkAttendanceView markAttendanceView) {
        this.markAttandanceView = markAttendanceView;
    }

    @Override
    public void unSubscribe() {
        markAttandanceView = null;
    }
}
