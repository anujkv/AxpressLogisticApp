package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingLayout {
    ProgressDialog progressDialog;

    public void showLoadingLayout(Context context) {
        progressDialog = new ProgressDialog(context) {
            @Override
            public void onBackPressed() {
                progressDialog.dismiss();
            }
        };
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }

    public void hideLoadingLayout(){
        progressDialog.dismiss();
    }
}
