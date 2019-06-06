package com.axpresslogistics.it2.axpresslogisticapp.presenter.tododelete;

import android.util.Log;


import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListDeleteModel;
import com.axpresslogistics.it2.axpresslogisticapp.network.ApiClient;
import com.axpresslogistics.it2.axpresslogisticapp.network.PosApiInterface;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ToDOLIstDeletePresenter implements MainInteractor {
    private Map<String, String> header;
    private Map<String, String> body;
    private Gson gson;

    public ToDOLIstDeletePresenter(Map<String, String> header, Map<String, String> body) {
        this.header = header;
        this.body = body;
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public void loadItems(final LoadListener<Object> loadListener) {
        PosApiInterface posApiInterface = ApiClient.getClient(AppConstants.BASE_URL).create(PosApiInterface.class);
        final String json = gson.toJson(body);
        Log.i("json", json);
        posApiInterface.ToDoListDelete(header, body).
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                unsubscribeOn(Schedulers.io()).
                subscribe(new Action1<ToDoListDeleteModel>() {
                    @Override
                    public void call(ToDoListDeleteModel toDoListDeleteModel) {
                        if (toDoListDeleteModel != null) {
                            Log.i("Status",toDoListDeleteModel.getStatus());
                            loadListener.onSuccess(toDoListDeleteModel);
                        } else {
                            loadListener.onFailure("Some error occured ");
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            if (throwable instanceof HttpException) {
                                if (((HttpException) throwable).code() == 400) {
                                    try {
                                        String responseBody = ((retrofit2.adapter.rxjava.HttpException) throwable).response().errorBody().string();
                                        Log.d("responsebody", responseBody);


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    loadListener.onFailure("Some Error Occured");
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            loadListener.onFailure("check your internet connection");
                        }
                    }


                });


    }}

