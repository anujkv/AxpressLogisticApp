package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.DocketEnquiry;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.MapsActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.Challan;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.DR;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.DocketResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy.PodDocketResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.DocketPresenter.DocketPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.PODDocketSavePresenter.PodDocketSavePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.DocketTrackingAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ImageConverter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.DocketEnquiryView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Docket_InvoiceView.PodDocketView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DocketTracking extends AppCompatActivity implements View.OnClickListener, DocketEnquiryView
        , PodDocketView {
    TextView txtDocket_no, txtDocket_date, txtDocket_from, txtDocket_to, txtDocketConsignor, txtDocketConsignee,
            txtDocket_status, txtDRS_no, txtDRS_date, txtDRS_receiving, txtDRS_received_by, txtDRS_status;
    //TextView seprate for firstListofItems details...
    TextView txt_challenFirst_no_ID, txt_challenFirst_dateID, txt_challanFirst_fromID, show_pod_link,
            txt_challanFirst_toID, txt_vehicalFirst_noID, txt_vehicalFirst_statusID, show_more_click_link;

    String strDocket_no, strDocket_date, strDocket_from, strDocket_to, strConsinor_ID, strDocketConsignee,
            strDocket_status, strDRS_no, strDRS_date, strDRS_receiving, strDRS_received_by, strDRS_status;
    //TextView seprate for firstListofItems details...
    String str_challenFirst_no_ID, str_challenFirst_dateID, str_challanFirst_fromID,
            str_challanFirst_toID, str_vehicalFirst_noID, str_vehicalFirst_statusID;

    private RecyclerView recyclerView;
    private DocketTrackingAdaptor adapter;
    JSONObject jObj;
    int no_of_challan, no_of_drs;
    Boolean FLAG = false, POD_FLAG = false;
    ProgressDialog progressDialog;
    CardView challanCardView, pod_details_CV;
    LinearLayout linearLayout;
    ImageButton backbtn_toolbar, mapbtn_toolbar;
    String input;
    MainPresenter presenter;
    String method, db_name;
    ApiKey apiKey = new ApiKey();
    String key;
    LoadingLayout loadingLayout = new LoadingLayout();
    TextView txt_dwdno;
    ImageConverter imageConverter = new ImageConverter();
    SubsamplingScaleImageView pod_iv;

    private List<Challan> challans = new ArrayList<Challan>();
    private List<DR> drsList = new ArrayList<DR>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docket_tracking);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.DOCKET_TRACKING_DETAILS);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        mapbtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        mapbtn_toolbar.setOnClickListener(this);
        declareVariable();
        try {
            Intent i = getIntent();
            Bundle bundle = i.getExtras();
            if (bundle != null) {
                input = bundle.getString("docketEnquiryResponse");
                method = bundle.getString("method");
                db_name = bundle.getString("db_name");
                presenter = new DocketPresenterImpl(this);
                presenter.init();
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void declareVariable() {
        pod_iv = (SubsamplingScaleImageView) findViewById(R.id.pod_iv);
//        pod_iv.setOnClickListener(this);
        txt_dwdno = findViewById(R.id.txt_dwdno);
//        pod_iv = findViewById(R.id.pod_iv);
        pod_details_CV = findViewById(R.id.pod_details_CV);
        show_pod_link = findViewById(R.id.show_pod_link);
        show_pod_link.setOnClickListener(this);
        linearLayout = findViewById(R.id.docketLinearLayoutId);
        txtDocket_no = findViewById(R.id.txt_docketID);
        txtDocketConsignor = findViewById(R.id.txt_consinor_ID);
        txtDocketConsignee = findViewById(R.id.txt_consigneeID);
        txtDocket_date = findViewById(R.id.txt_docketDateID);
        txtDocket_from = findViewById(R.id.txt_fromID);
        txtDocket_to = findViewById(R.id.txt_toID);
        txtDocket_status = findViewById(R.id.txt_statusID);

        txtDRS_no = findViewById(R.id.txt_drs_challan_noID);
        txtDRS_date = findViewById(R.id.txt_drsDateID);
        txtDRS_receiving = findViewById(R.id.txt_receiving_dateID);
        txtDRS_received_by = findViewById(R.id.txt_drs_received_byID);
        txtDRS_status = findViewById(R.id.txt_drs_statusID);

        txt_challenFirst_no_ID = findViewById(R.id.txt_challenFirst_no_ID);
        txt_challenFirst_dateID = findViewById(R.id.txt_challenFirst_dateID);
        txt_challanFirst_fromID = findViewById(R.id.txt_challanFirst_fromID);
        txt_challanFirst_toID = findViewById(R.id.txt_challanFirst_toID);
        txt_vehicalFirst_noID = findViewById(R.id.txt_vehicalFirst_noID);
        txt_vehicalFirst_statusID = findViewById(R.id.txt_vehicalFirst_statusID);

        show_more_click_link = findViewById(R.id.show_more_click_link);
        challanCardView = findViewById(R.id.challenCardView);

        recyclerView = findViewById(R.id.challanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        String status_delivered = "delivered";
        String status_at_rest = "at rest";
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
//                Log.d("strDocket_status", strDocket_status);
                if (status_delivered.equals(strDocket_status.toLowerCase()) ||
                        status_at_rest.equals(strDocket_status.toLowerCase())) {
                    showDialogbox();
//                    Intent intent = new Intent(DocketTracking.this, MapsActivity.class);
//                    intent.putExtra("docket", strDocket_no);
//                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DocketTracking.this, MapsActivity.class);
                    intent.putExtra("docket", strDocket_no);
                    startActivity(intent);
                }

            case R.id.show_pod_link:
                if (isFalse()) {
                    pod_details_CV.setVisibility(View.VISIBLE);
                    show_pod_link.setText(CONSTANT.POD_HIDE);
                    POD_FLAG = true;
                } else {
                    pod_details_CV.setVisibility(View.GONE);
                    show_pod_link.setText(CONSTANT.POD_SHOW);
                    POD_FLAG = false;
                }
                break;
        }
    }

    private boolean isFalse() {
        if (POD_FLAG.equals(false)) {
            presenter = new PodDocketSavePresenterImpl(this);
            presenter.init();
            return true;
        } else {
            show_pod_link.setText(CONSTANT.SHOW_MORE);
            return false;
        }

    }

    private void showDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Docket : " + strDocket_no);
        builder.setMessage("Docket has been " + strDocket_status + ",Location disabled");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.support.v7.app.AlertDialog b = builder.create();
        b.show();
    }

    @Override
    public String getKey() {
        return apiKey.saltStr();
    }

    @Override
    public String getDwbNo() {
        return strDocket_no;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getInput() {
        return input;
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
        if (object instanceof DocketResponse) {
            DocketResponse docketEnquiryResponse = (DocketResponse) object;

//            Log.e("docketEnquiryResponse", new Gson().toJson(docketEnquiryResponse));
            if (docketEnquiryResponse.getStatus().equals(CONSTANT.TRUE)) {
                txtDocket_no.setText(docketEnquiryResponse.getDocketNo());
                txtDocketConsignor.setText(docketEnquiryResponse.getConsignorName());
                txtDocketConsignee.setText(docketEnquiryResponse.getConsigneeName());
                txtDocket_date.setText(docketEnquiryResponse.getDocketDate());
                txtDocket_from.setText(docketEnquiryResponse.getDocketFrom());
                txtDocket_to.setText(docketEnquiryResponse.getDocketTo());
                txtDocket_status.setText(docketEnquiryResponse.getDocketStatus());

                try {
                    drsList.addAll(docketEnquiryResponse.getDRS());
                    no_of_drs = drsList.size();
                    for (int i = 0; i < no_of_drs; i++) {

                        txtDRS_no.setText(drsList.get(i).getDrsNo());
                        txtDRS_date.setText(drsList.get(i).getDrsDate());
                        txtDRS_receiving.setText(drsList.get(i).getDrsReceivedDate());
                        txtDRS_received_by.setText(drsList.get(i).getDrsReceivedBy());
                        txtDRS_status.setText(drsList.get(i).getDrsStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    challans.addAll(docketEnquiryResponse.getChallan());
                    no_of_challan = challans.size();
                    for (int i = 0; i < no_of_challan; i++) {
                        txt_challenFirst_no_ID.setText(challans.get(i).getChallanNo());
                        txt_challenFirst_dateID.setText(challans.get(i).getChallanDate());
                        txt_challanFirst_fromID.setText(challans.get(i).getChallanFrom());
                        txt_challanFirst_toID.setText(challans.get(i).getChallanTo());
                        txt_vehicalFirst_noID.setText(challans.get(i).getVehicleNo());
                        txt_vehicalFirst_statusID.setText(challans.get(i).getStatus());
                    }
                    adapter = new DocketTrackingAdaptor(getApplicationContext(), challans);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                show_more_click_link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (FLAG.equals(false)) {
                            recyclerView.setVisibility(View.VISIBLE);
                            show_more_click_link.setText(CONSTANT.SHOW_LESS);
                            FLAG = true;
                        } else if (FLAG.equals(true)) {
                            recyclerView.setVisibility(View.GONE);
                            show_more_click_link.setText(CONSTANT.SHOW_MORE);
                            linearLayout.invalidate();
                            FLAG = false;
                        }
                    }
                });
            }
        } else if (object instanceof PodDocketResponse) {
            PodDocketResponse podDocketResponse = (PodDocketResponse) object;
            txt_dwdno.setText(podDocketResponse.getDwbno());
            pod_iv.setImage(ImageSource.bitmap(imageConverter.StringToBitMap(podDocketResponse.getImage())));
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), "Docket " + input + " not found!", Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();
    }
}



