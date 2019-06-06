package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public abstract class BaseActivity extends AppCompatActivity implements MainView {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void showLoadingLayout() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingLayout() {
        if(progressDialog!= null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


}
