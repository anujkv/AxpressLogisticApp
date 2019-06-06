package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.vendorApproval.VendorAmountDetailsAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval.VendorApprovalRequest;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval.VendorApprovalResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorFetchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorVehicleAttachRequestRateDetailse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorApproval.VendorApprovalPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorFetch.VendorFetchPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorApprovalView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorFetchDetailsView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorApprovalActivity<refreshData> extends AppCompatActivity implements View.OnClickListener,
        VendorApprovalView, VendorFetchDetailsView {
    @BindView(R.id.app_bar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.title_toolbar)
    TextView lable;

    ImageView backbtn_toolbar;

    @BindView(R.id.txt_pancardId)
    EditText txt_pancard;

    @BindView(R.id.txt_closeDateId)
    EditText txt_closeDate;
    @BindView(R.id.txt_vehical_noID)
    EditText txt_vehicleNo;
    @BindView(R.id.txt_size_of_vehicle_id)
    EditText txt_sizeOfVehicle;
    @BindView(R.id.txt_owner_name_id)
    EditText txt_ownerName;
    @BindView(R.id.txt_vendor_name)
    EditText txt_vendor_name;
    @BindView(R.id.txt_address_id)
    EditText txt_address;
    @BindView(R.id.txt_contant_id)
    EditText txt_contact;
    @BindView(R.id.txt_vendor_bill_location_id)
    EditText txt_vendor_bill_location;
    @BindView(R.id.txt_join_date_id)
    EditText txt_join_date;
    MainPresenter presenter;

    Button approveBtn, rejectBtn;
    String approvalStatus;
    String requestNo;
    ApiKey apiKey = new ApiKey();
    String key;
//    ProgressDialog progressDialog;
    List<VendorVehicleAttachRequestRateDetailse> detailseList = new ArrayList<>();
    VendorAmountDetailsAdaptor detailsAdaptor;
    RecyclerView recyclerView;
//    VendorApprovalRequest vendorApprovalRequest;
    LoadingLayout loadingLayout = new LoadingLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_approval);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        lable.setText(CONSTANT.VENDOR_APPROVAL);
        backbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_arrow_back));
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        approveBtn = findViewById(R.id.vendorApproveBtn);
        rejectBtn = findViewById(R.id.vendorRejectBtn);
        approveBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.vendorAmountDetailsRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailsAdaptor);

        Intent intent = getIntent();
        requestNo = intent.getStringExtra("request_code");
        try {
            if (requestNo != null) {
                fetchVandorDetails();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void fetchVandorDetails() {
        key = apiKey.saltStr();
        presenter = new VendorFetchPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vendorApproveBtn:
                approvalStatus = "A";
                vendorApproved();
                finish();
                break;

            case R.id.vendorRejectBtn:
                    approvalStatus = "R";
                    vendorApproved();
                    finish();
                    break;
        }
    }

    private void vendorApproved() {
        presenter = new VendorApprovalPresenterImpl(this);
        presenter.init();
    }

    @Override
    public String getRequestNo() {
        return requestNo;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public List<VendorVehicleAttachRequestRateDetailse> getVendorVehicleAttachRequestRateDetailses() {
        return detailseList;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.DB_NAME);
    }

    @Override
    public String getApprovalStatus() {
        return approvalStatus;
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.showLoadingLayout(this);
    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof VendorApprovalResponse) {
            VendorApprovalResponse response = (VendorApprovalResponse) object;
            Log.e("response", new Gson().toJson(response));
            if (response.getStatus().equals("Approved")) {
                Toast.makeText(getApplicationContext(), "Vendor has been Approved Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else if (response.getStatus().toLowerCase().contains("reject")) {
                Toast.makeText(getApplicationContext(), "Vendor has been Reject Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "server not reachable", Toast.LENGTH_SHORT).show();
            }
        } else if (object instanceof VendorFetchResponse) {
            VendorFetchResponse response = (VendorFetchResponse) object;
            Log.e("response", new Gson().toJson(response));
            if (response.getStatus().equals(CONSTANT.TRUE)) {
                txt_pancard.setText(response.getPanNo());
                txt_closeDate.setText(response.getCloseDate());
                txt_vehicleNo.setText(response.getVehicleNo());
                txt_sizeOfVehicle.setText(response.getSizeOfVehicle());
                txt_ownerName.setText(response.getOwnerName());
                txt_vendor_name.setText(response.getVendorName());
                txt_address.setText(response.getAddress());
                txt_contact.setText(response.getContactNo());
                txt_vendor_bill_location.setText(response.getVendorBillLocation());
                txt_join_date.setText(response.getJoinDate());

                try {
                    detailseList.addAll(response.getVendorVehicleAttachRequestRateDetailses());
                    detailsAdaptor = new VendorAmountDetailsAdaptor(getApplicationContext(), detailseList);

                    recyclerView.setAdapter(detailsAdaptor);

                } catch (Exception e) {
                    e.printStackTrace();
                    loadingLayout.hideLoadingLayout();
                }
            } else {
                Toast.makeText(getApplicationContext(), CONSTANT.data_not_found, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }

}

