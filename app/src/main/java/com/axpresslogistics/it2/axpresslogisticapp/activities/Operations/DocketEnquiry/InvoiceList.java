package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.DocketEnquiry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.InvoiceDocketAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceDetail;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.InvoicePresenter.InvoicePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.InvoiceEnquiryView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InvoiceList extends AppCompatActivity implements InvoiceEnquiryView {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    InvoiceDocketAdaptor invoiceDocketAdaptor;
    List<InvoiceDetail> invoiceDocketModelList;
    String invoice_no,db_name;
    final ApiKey apiKey = new ApiKey();
    String key = apiKey.saltStr();
    final String apikey = apiKey.saltStr();
    MainPresenter Presenter;
    ProgressDialog progressDialog;
    LoadingLayout loadingLayout = new LoadingLayout();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list);
        Toolbar toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        progressBar = findViewById(R.id.progressBar);
        lable.setText(CONSTANT.INVOICE);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        ImageButton mapbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.docketlistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        invoiceDocketModelList = new ArrayList<>();

        try {
            Intent i = getIntent();
            Bundle bundle = i.getExtras();
            if (bundle != null) {
                invoice_no = bundle.getString("invoice_no");
                Log.e("INVOICE: ",invoice_no);
                db_name = bundle.getString("db_name");
                showInvoiceLIst();
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInvoiceLIst() {
        key = apiKey.saltStr();
        Presenter = new InvoicePresenterImpl(this);
        Presenter.init();
    }

    @Override
    public String getMethod() {
        return "invoice_details";
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getInvoiceNo() {
        return invoice_no;
    }

    @Override
    public String getDBName() {
        Preferences.setPreference(getApplicationContext(),CONSTANT.DB_NAME,db_name);
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
    public void showSuccess(Object o) {
        InvoiceResponse response = (InvoiceResponse) o;
        Log.e("InvoiceRRRResponse",new Gson().toJson(response));
        if(response!=null){
            if(response.getStatus().equals(CONSTANT.TRUE)){
                invoiceDocketModelList.addAll(response.getInvoiceDetails());
                invoiceDocketAdaptor = new InvoiceDocketAdaptor(this,
                        invoiceDocketModelList);
                recyclerView.setAdapter(invoiceDocketAdaptor);
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();
        loadingLayout.hideLoadingLayout();
    }
}
