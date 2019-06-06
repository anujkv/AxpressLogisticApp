package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConstants {
    public static final String BASE_URL = "http://webapi.axpresslogistics.com/api/";
    public static final String TEST_BASE_URL = "http://192.168.1.7/webapi.axpresslogistics.com/api/";

    public static final String POD_BASE_URL = "http://172.19.192.140/webapi.axpresslogistics.com/api/";


    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
