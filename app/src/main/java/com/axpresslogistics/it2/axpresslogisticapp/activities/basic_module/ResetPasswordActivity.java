package com.axpresslogistics.it2.axpresslogisticapp.activities.basic_module;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel.ResetPasswordResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.ResetPasswordPresenter.ResetPasswordPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.ResetPasswordView;
import com.google.gson.Gson;

import static android.widget.Toast.LENGTH_SHORT;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener,
        ResetPasswordView {
    EditText editTextResetEmail, editTextResetID;
    TextView textViewBackLinkId, textViewSubmitLinkId;
    String strEmail, strEmpId;
    ProgressBar progressBar;
    String key;
    ProgressDialog progressDialog;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.HRMS);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editTextResetEmail = findViewById(R.id.editTextResetEmail);
        editTextResetID = findViewById(R.id.editTextResetID);
        textViewBackLinkId = findViewById(R.id.textViewBackLinkId);
        textViewSubmitLinkId = findViewById(R.id.textViewSubmitLinkId);
        progressBar = findViewById(R.id.progressBar);
        editTextResetID.setFocusable(true);

        textViewBackLinkId.setOnClickListener(this);
        textViewSubmitLinkId.setOnClickListener(this);

    }


    private void resetPassword() {
        ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        presenter = new ResetPasswordPresenterImpl(this);
        presenter.init();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewSubmitLinkId:
                //Check Emp ID is empty or not...
                if (editTextResetID.getText().toString().trim().isEmpty() &&
                        editTextResetID.getText().toString().trim().equals(CONSTANT.BLANK)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ENTER_EMPLOYEE_ID,
                            LENGTH_SHORT).show();
                } else {
                    strEmpId = editTextResetID.getText().toString().trim();
                    if (editTextResetEmail.getText().toString().trim().isEmpty() &&
                            editTextResetEmail.getText().toString().trim().equals(CONSTANT.BLANK)) {
                        Toast.makeText(getApplicationContext(), CONSTANT.ENTER_EMAIL_ID,
                                LENGTH_SHORT).show();
                    } else {
                        strEmail = editTextResetEmail.getText().toString().trim();
                        resetPassword();
                    }
                }
                break;

            case R.id.textViewBackLinkId:
                finish();
                break;
        }
    }

    @Override
    public String getEmplid() {
        return strEmpId;
    }

    @Override
    public String getEmail() {
        return editTextResetEmail.getText().toString().trim();
    }

    @Override
    public String getDOB() {
        return CONSTANT.BLANK;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void showLoadingLayout() {
        progressDialog = new ProgressDialog(this) {
            @Override
            public void onBackPressed() {
                progressDialog.dismiss();
            }
        };
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void hideLoadingLayout() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof ResetPasswordResponse) {
            Log.e("Response",new Gson().toJson(o));
            ResetPasswordResponse resetPasswordResponse = (ResetPasswordResponse) o;
            if (resetPasswordResponse.getStatus().equals(CONSTANT.TRUE)) {
                Toast.makeText(getApplicationContext(), CONSTANT.PASSWORD_DEFAULT_SET,
                        Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(getApplicationContext(), CONSTANT.WRONG_CREDENTIAL, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
}
