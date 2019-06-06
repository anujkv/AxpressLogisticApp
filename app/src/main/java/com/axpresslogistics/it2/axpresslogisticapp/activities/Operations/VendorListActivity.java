package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.vendorApproval.VendorListAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList.VendorList;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList.VendorViewListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.VendorApproval.VendorList.VendorListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView.VendorViewList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorListActivity extends AppCompatActivity implements VendorViewList {
    @BindView(R.id.vendorRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_data_available)
    TextView no_data_available;

    @BindView(R.id.app_bar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.title_toolbar)
    TextView lable;
    ProgressDialog progressDialog;

    ImageView backbtn_toolbar;
    List<VendorList> vendorLists;
    VendorListAdaptor vendorListAdaptor;
    MainPresenter presenter;
    String db_name = "AxpressERP";
    LoadingLayout loadingLayout = new LoadingLayout();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        lable.setText(CONSTANT.VENDORLIST);
        backbtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_arrow_back));
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        db_name = intent.getStringExtra("db_name");
        recyclerView = findViewById(R.id.vendorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        vendorLists = new ArrayList<>();
        uploadVendorList();

    }

    private void uploadVendorList() {
        presenter = new VendorListPresenterImpl(this);
        presenter.init();
    }

    @Override
    public String getEmplid() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getDBName() {
        return db_name;
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
        if (object instanceof VendorViewListResponse) {
            VendorViewListResponse response = (VendorViewListResponse) object;
            Log.e("response",new Gson().toJson(response));
            Preferences.setPreference(VendorListActivity.this, CONSTANT.DB_NAME,
                    response.getDb_name());
            if (response != null) {
                no_data_available.setVisibility(View.GONE);
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    vendorLists.addAll(response.getVendorList());
                    vendorListAdaptor = new VendorListAdaptor(this, vendorLists);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(vendorListAdaptor);

                } else {
                    Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT).show();
                    no_data_available.setVisibility(View.VISIBLE);
                }
            }else {
                Toast.makeText(getApplicationContext(),"Server not reachable",Toast.LENGTH_SHORT).show();
                no_data_available.setVisibility(View.VISIBLE);
            }
        }

    }
    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),"server not reachable",Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(vendorLists.size()>0){
            vendorLists.clear();
            uploadVendorList();
        }
    }

}

