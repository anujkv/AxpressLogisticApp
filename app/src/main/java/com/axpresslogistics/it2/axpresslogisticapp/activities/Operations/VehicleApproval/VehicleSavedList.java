package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleApproval;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.VehicleApprovalAdaptor.VehicleListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList.SavedvehiclList;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList.VehicleApprovalListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval.VehicleApprovalListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.VehicleApprovalListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class VehicleSavedList extends AppCompatActivity implements View.OnClickListener ,
        VehicleApprovalListView {
    ImageButton backbtn_toolbar, addbtn_toolbar, searchbtn_toolbar;
    TextView  title_toolbar;
    EditText searchedt_toolbar;
    ConstraintLayout no_data_availableLayout;
    ImageView refresh_image;
    MainPresenter presenter;
    RecyclerView recyclerVehicleView;
    List<SavedvehiclList> modelList = new ArrayList<>();
    VehicleListAdaptor vehicleListAdaptor;
    String employee_id, branch_code;
    ApiKey apiKey = new ApiKey();
    String key;
    String method;
//    ProgressDialog progressDialog;
    LoadingLayout loadingLayout = new LoadingLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_approval_saved_list);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(CONSTANT.VEHICLE_APPROVAL_LIST);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        searchbtn_toolbar = findViewById(R.id.searchbtn_toolbar);
        searchedt_toolbar = findViewById(R.id.searchedt_toolbar);
        addbtn_toolbar = findViewById(R.id.newbtn_toolbar);
        addbtn_toolbar.setVisibility(View.GONE);
        no_data_availableLayout = findViewById(R.id.no_data_availableLayout);
        refresh_image = findViewById(R.id.refresh_image);
        refresh_image.setOnClickListener(this);
        backbtn_toolbar.setOnClickListener(this);
        searchbtn_toolbar.setOnClickListener(this);
        employee_id = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        branch_code = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_BRANCH);

        recyclerVehicleView = findViewById(R.id.market_vehicle_RecyclerView);
        recyclerVehicleView.setHasFixedSize(true);


        key = apiKey.saltStr();
        market_vehical_Saved_List();
    }

    private void market_vehical_Saved_List(){
        method = "saved_vehicle_list";
        presenter = new VehicleApprovalListPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.searchbtn_toolbar:
                title_toolbar.setVisibility(View.GONE);
                searchedt_toolbar.setVisibility(View.VISIBLE);
                searchedt_toolbar.setTextColor(Color.WHITE);
                searchedt_toolbar.setFocusable(true);
                searchedt_toolbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        modelList.clear();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.e("ATCs : ", s.toString());
                    }
                });
                break;
            case R.id.refresh_image:
                refresh_page();
                break;
        }
    }
    private void refresh_page() {
        searchedt_toolbar.setText("");
        title_toolbar.setVisibility(View.VISIBLE);
        searchedt_toolbar.setVisibility(View.GONE);
        if(modelList.size()>0){
            modelList.clear();
            market_vehical_Saved_List();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh_page();
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getBranchCode() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPLOYEE_BRANCH);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(VehicleSavedList.this,CONSTANT.DB_NAME);
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(VehicleSavedList.this) {
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//                finish();
//            }};
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//
//        progressDialog.show();
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object object) {
        if(object instanceof VehicleApprovalListResponse){
            VehicleApprovalListResponse response = (VehicleApprovalListResponse) object;
            Log.e("Response",new Gson().toJson(response));

            if (response.getStatus().equals(CONSTANT.TRUE)) {
                modelList.addAll(response.getSavedvehiclLists());
                //  ProjectUtil.customToast(this, "Successfully");
                vehicleListAdaptor = new VehicleListAdaptor(this, modelList);
                recyclerVehicleView.setLayoutManager(new LinearLayoutManager(this));
                recyclerVehicleView.setAdapter(vehicleListAdaptor);
                if(modelList != null){
                    no_data_availableLayout.setVisibility(View.GONE);
                }

            } else {
                no_data_availableLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Data not available!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), "server not reachable", Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();
    }
}
