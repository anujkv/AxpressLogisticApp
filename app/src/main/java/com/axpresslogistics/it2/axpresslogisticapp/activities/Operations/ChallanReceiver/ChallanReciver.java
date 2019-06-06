package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.ChallanReceiver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive.ChallanCloseResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive.ChallanReceiveResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.ChallanReceiverPresenter.ChallanClosingPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.ChallanReceiverPresenter.ChallanReceivePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.DateTime;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.ChallanRec.ChallanCloseView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.ChallanRec.ChallanReceiveView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ChallanReciver extends AppCompatActivity implements ChallanReceiveView, ChallanCloseView {
    TextView challan_no, challan_type, challan_created_date, challan_closing_date, mode, from_branch,
            to_branch, vehicle_no, tcs_no, awb_no, seal_no, msg_for_update_txt, msg_for_received_txt;
    EditText remark;
    Button submit;

    String str_challan_no, str_challan_type, str_challan_created_date, str_challan_closing_date,
            str_mode, str_from_branch, str_to_branch, str_vehicle_no, str_tcs_no, str_awb_no, str_seal_no,
            str_remark = "", str_barcode = "", emplid;

    LinearLayout linearLayout;
    DateTime dateTime = new DateTime();
    String db_name;
    String key = "", emp_branch, method;
    TextView update_msg_erp;
    AlertDialog.Builder builderfrom, builderto;
    AlertDialog dialog;
    private IntentIntegrator barcode;
    MainPresenter presenter;
    LoadingLayout loadingLayout = new LoadingLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan_reciver);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.CHALLAN_RECIVER);
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        emplid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        init();

        barcode = new IntentIntegrator(this);
        barcode.initiateScan();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_to_branch = to_branch.getText().toString().trim();
                try {
                    if (str_to_branch.equals(emp_branch)) {
                        Log.e("empbranch", str_to_branch + "==" + emp_branch);
                        submitDetails();
                        challanClose();
                    } else {
                        msg_for_update_txt.setVisibility(View.GONE);
                        msg_for_received_txt.setVisibility(View.VISIBLE);
                        msg_for_received_txt.setText("Sorry, Package not reached on destination!");
                    }
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                }
            }
        });
    }

    private void challanClose() {
        method = "challan_closing";
        presenter = new ChallanClosingPresenterImpl(this);
        presenter.init();

    }

    private void submitDetails() {
        str_challan_no = challan_no.getText().toString().trim();
        str_challan_type = challan_type.getText().toString().trim();
        str_challan_created_date = challan_created_date.getText().toString().trim();
        str_challan_closing_date = dateTime.currentDateTime();
        challan_closing_date.setText(str_challan_closing_date);
        str_mode = mode.getText().toString().trim();
        str_from_branch = from_branch.getText().toString().trim();
        str_to_branch = to_branch.getText().toString().trim();
        str_vehicle_no = vehicle_no.getText().toString().trim();
        str_tcs_no = tcs_no.getText().toString().trim();
        str_awb_no = awb_no.getText().toString().trim();
        str_seal_no = seal_no.getText().toString().trim();
        str_remark = remark.getText().toString().trim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                finish();
            } else {
                challan_reciver(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void challan_reciver(String contents) {
        method = "challan_reciver";
        str_barcode = contents;
        presenter = new ChallanReceivePresenterImpl(this);
        presenter.init();
    }

    private void init() {
        challan_no = findViewById(R.id.challan_no);
        challan_type = findViewById(R.id.challan_type);
        challan_created_date = findViewById(R.id.create_date);
        challan_closing_date = findViewById(R.id.closing_date);
        mode = findViewById(R.id.mode);
        from_branch = findViewById(R.id.from_branch);
        to_branch = findViewById(R.id.to_branch);
        vehicle_no = findViewById(R.id.vehicle_no);
        tcs_no = findViewById(R.id.tcs_no);
        awb_no = findViewById(R.id.awb_no);
        seal_no = findViewById(R.id.seal_no);
        remark = findViewById(R.id.edit_remark);
        submit = findViewById(R.id.challan_closing_btn);
        msg_for_update_txt = findViewById(R.id.msg_for_update_txt);
        msg_for_received_txt = findViewById(R.id.msg_for_received_txt);
        linearLayout = findViewById(R.id.recived_btn_layout);
        submit.setText(CONSTANT.SUBMIT);
    }

    @Override
    public String getBarcode() {
        return str_barcode;
    }

    @Override
    public String getClosing_date() {
        return str_challan_closing_date;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(ChallanReciver.this,CONSTANT.DB_NAME);
//        return  "AxpressERP";
    }


    @Override
    public String getChallan_no() {
        return str_challan_no;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getEmp_id() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getRemark() {
        return str_remark;
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
        if (object instanceof ChallanReceiveResponse) {
            ChallanReceiveResponse response = (ChallanReceiveResponse) object;
            Log.e("ResponseReceiver",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    // ProjectUtil.customToast(this, "Successfully");
                    try {
                        challan_no.setText(response.getChallanNo());
                        challan_type.setText(response.getType());
                        challan_created_date.setText(response.getCreatedDate());
                        challan_closing_date.setText(response.getClosingDate());
                        mode.setText(response.getMode());
                        from_branch.setText(response.getFromBranch());
                        to_branch.setText(response.getToBranch());
                        vehicle_no.setText(response.getVehicleNo());
                        tcs_no.setText(response.getTcsNo());
                        awb_no.setText(response.getAwbNo());
                        seal_no.setText(response.getSealNo());
                        remark.setText(response.getRemark());

                        str_challan_closing_date = response.getClosingDate();
                        if (str_challan_closing_date.equals("")) {
                            linearLayout.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout.setVisibility(View.VISIBLE);
                            msg_for_received_txt.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.GONE);
                            msg_for_update_txt.setVisibility(View.INVISIBLE);
                            msg_for_received_txt.setText(CONSTANT.CHALLAN_RECEIVED + ", " + str_challan_closing_date);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Scanned barcode data not found",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Data not available or its too old challan", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT).show();
            }
        } else if (object instanceof ChallanCloseResponse) {
            ChallanCloseResponse response = (ChallanCloseResponse) object;
            Log.e("ResponseClose",new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    //  ProjectUtil.customToast(this, "Successfully");
                    showShortMsg();

                } else {
                    Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void showShortMsg() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.fragment_challan_short_dialog, null);
        update_msg_erp = alertLayout.findViewById(R.id.update_msg_erp);

        //FROM..............
        builderfrom = new AlertDialog.Builder(alertLayout.getContext());
        builderfrom.setTitle("Update Short/Extra in ERP");
        builderfrom.setView(alertLayout);
        builderfrom.setCancelable(false);

        builderfrom.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dialog = builderfrom.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(), CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
