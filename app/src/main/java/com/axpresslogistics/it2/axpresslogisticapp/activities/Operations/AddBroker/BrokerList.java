package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddBroker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.BrokerAdaptor.BrokerAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.BrokerAdaptor.SearchBrokerListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.Broker;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.BrokerListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.BrokerSearchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.Brokersearch;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList.BrokerListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList.BrokerSearchPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.BrokerListSearchView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.BrokerListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BrokerList extends AppCompatActivity implements View.OnClickListener, BrokerListView, BrokerListSearchView {
    ImageButton backbtn_toolbar, addbtn_toolbar, searchbtn_toolbar;
    TextView  title_toolbar;
    EditText searchedt_toolbar;
    ConstraintLayout no_data_availableLayout;
    ImageView refresh_image;
    List<Brokersearch> brokersearchList;
    RecyclerView recyclerBrokerView, search_recyclerView;
    BrokerAdaptor brokerAdaptor;
    SearchBrokerListAdaptor searchAdapter;
    List<Broker> brokerList;

    String broker_code_from_broker_list = null;
    ProgressDialog progressDialogCall;
    LoadingLayout loadingLayout = new LoadingLayout();
    MainPresenter presenter;
    ApiKey apikey = new ApiKey();
    String key = apikey.saltStr();
    String input;
    String method= "savedbrokerlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(CONSTANT.BROKER_LIST);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
        backbtn_toolbar.setOnClickListener(this);
        addbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);

        brokerList = new ArrayList<Broker>();
        brokersearchList = new ArrayList<Brokersearch>();
        recyclerBrokerView = findViewById(R.id.brockerRecyclerView);
        recyclerBrokerView.setHasFixedSize(true);
        recyclerBrokerView.setLayoutManager(new LinearLayoutManager(this));
        key = apikey.saltStr();
        search_recyclerView = findViewById(R.id.brockerRecyclerView);
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        brokerList();
    }

    private void brokerList() {
        if (!brokerList.isEmpty()) {
            brokerList.clear();
        }
        presenter = new BrokerListPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newbtn_toolbar:
                startActivity(new Intent(this, AddBroker.class));
                break;
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.refresh_image:
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        brokersearchList.clear();
                        brokerList.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                        hitBrokerSearchAPi(s.toString());
                    }
                });
                break;
        }
    }

    private void hitBrokerSearchAPi(String toString) {
        input = toString;

        if(!brokerList.isEmpty()){
            brokerList.clear();
        }
        if(!brokersearchList.isEmpty()){
            brokersearchList.clear();
        }
        method = "search_broker";
        presenter = new BrokerSearchPresenterImpl(this);
        presenter.init();
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.DB_NAME);
    }

    @Override
    public void showLoadingLayout() {
//        progressDialogCall = new ProgressDialog(BrokerList.this) {
//            @Override
//            public void onBackPressed() {
//                progressDialogCall.dismiss();
//            }
//        };
//        progressDialogCall.setMessage("Loading");
//        progressDialogCall.setCancelable(false);
//        progressDialogCall.show();
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();

    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof BrokerListResponse) {
//            Log.e("ResponseList",new Gson().toJson(object));
            BrokerListResponse response = (BrokerListResponse) object;
//            Log.e("ResponseListresponse",new Gson().toJson(response));

            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    brokerList.clear();
                    brokersearchList.clear();
                    no_data_availableLayout.setVisibility(View.GONE);
                    brokerList.addAll(response.getBroker());
                    brokerAdaptor = new BrokerAdaptor(getApplicationContext(), brokerList);
                    recyclerBrokerView.setAdapter(brokerAdaptor);
                } else {
                    no_data_availableLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        else if(object instanceof BrokerSearchResponse){
//            Log.e("ResponseSearch",new Gson().toJson(object));
            BrokerSearchResponse response =(BrokerSearchResponse) object;
//            Log.e("ResponseSerchResponse",new Gson().toJson(response));
            if(response != null){
                if(response.getStatus().equals(CONSTANT.TRUE)){
                    brokerList.clear();
                    brokersearchList.clear();
                    brokersearchList.addAll(response.getBrokersearch());
                    searchAdapter = new SearchBrokerListAdaptor(getApplicationContext(), brokersearchList);
                    search_recyclerView.setAdapter(searchAdapter);
                    if(response.getBrokersearch() != null){
                        no_data_availableLayout.setVisibility(View.GONE);
                    }else{
                        no_data_availableLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    no_data_availableLayout.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
