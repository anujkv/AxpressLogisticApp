package com.axpresslogistics.it2.axpresslogisticapp.view.BaseView;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ProfileImageUploadView extends MainView {
    String getMethod();
    String getEmplid();
    String getKey();
    MultipartBody.Part getImage();
    RequestBody getType();
}
