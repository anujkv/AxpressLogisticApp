package com.axpresslogistics.it2.axpresslogisticapp.presenter.hrmsPresenter.ApplyLeave;

import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
//import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.ApplyingLeaveRequest;
import com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApplyLeave.ApplyingLeaveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.network.ApiClient;
import com.axpresslogistics.it2.axpresslogisticapp.network.PosApiInterface;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.AppConstants;
import com.axpresslogistics.it2.axpresslogisticapp.view.Hrms.ApplyingLeaveView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ApplyingLeavePresenter implements MainInteractor {

    private Map<String, String> header;
    private Map<String, String> body;
    private ApplyingLeaveView applyingLeaveRequest;
    private Gson gson;


    public ApplyingLeavePresenter(Map<String, String> header, Map<String, String> body) {
        this.header = header;
        this.body = body;
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public void loadItems(final LoadListener<Object> loadListener) {
        final PosApiInterface posApiInterface = ApiClient.getClient(AppConstants.BASE_URL).create(PosApiInterface.class);

        gson = new GsonBuilder().disableHtmlEscaping().create();
        final String json = gson.toJson(applyingLeaveRequest);
        Log.i("request", json);

        posApiInterface.applyingleave(header, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<ApplyingLeaveResponse>() {
                    @Override
                    public void call(ApplyingLeaveResponse response) {
                        if (response != null) {

                            //   ProjectUtil.customToast(context, "response.getStatus()");
                            Log.d("Status", response.getStatus() + response.getId());
                            loadListener.onSuccess(response);
                        }
                        else {
                            loadListener.onFailure("Some error occurred.");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("Genrate", "" + throwable.getMessage());
                            if (throwable instanceof HttpException) {
                                if (((HttpException) throwable).code() == 400) {
                                    try {
                                        String responseBody = ((HttpException) throwable).response().errorBody().string();
                                        Log.d("responsebody", responseBody);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                loadListener.onFailure("Some error occurred.");

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            loadListener.onFailure("Check your Internet Connection");
                        }
                    }
                });
    }
}