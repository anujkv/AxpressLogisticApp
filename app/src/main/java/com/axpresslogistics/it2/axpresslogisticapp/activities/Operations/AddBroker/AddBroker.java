package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddBroker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.AddBroker.AddBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.FetchBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.UpdatBrokerResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.AddBrokerPresenter.AddBrokerPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList.BrokerFetchDetailsPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList.BrokerUpdatePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.DateTime;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.AddBrokerView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.FetchBrokerView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.UpdateBrokerView;
import com.google.gson.Gson;

public class AddBroker extends AppCompatActivity implements View.OnClickListener, AddBrokerView,
        UpdateBrokerView, FetchBrokerView {
    ImageButton backbtn_toolbar, savebtn_toolbar;
    String method , input, branch_code;
    EditText edt_branch, edtbrokerName, edtContactNo, edtContactAltNo, edtEmail, edtAddress,
            edt_bank_account_no, edt_bank_name, edt_pan_no, edt_name_on_pancard, edtBankIFSC,
            edtCreatedBy, edtCreatedOn;
    String str_branch, strbrokerName, strContactNo, strContactAltNo, strEmail, strAddress,
            str_bank_account_no, str_bank_name, str_pan_no, str_name_on_pancard, strBankIFSC,
            strCreatedBy, strCreatedOn = "",strModificationDate, strbroker_status, str_broker_code, function_method = "";

    RadioButton radio_active_id, radio_inactive_id, radio_btn_id;
    String broker_code_from_broker_list = null;

    int MAX_LENGTH = 10;
    MainPresenter presenter;
    DateTime dateTime = new DateTime();

    final ApiKey apiKey = new ApiKey();
    final String key = apiKey.saltStr();
    ProgressDialog progressDialog;
    LoadingLayout loadingLayout = new LoadingLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_broker);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.ADD_BROKER);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        branch_code = Preferences.getPreference(this, CONSTANT.EMPLOYEE_BRANCH);
        init();
        Intent intent = getIntent();
        broker_code_from_broker_list = intent.getStringExtra("broker_code");
        function_method = intent.getStringExtra("method");
        if(function_method !=null && function_method.equals("update_broker") ){
            method = "update_broker";
            function_method = "broker_detail_fetch";
            fetch_broker();
        }else{
            method="add_broker";
        }
    }

    private void init() {
        edt_branch = findViewById(R.id.edt_branch);
        str_branch = Preferences.getPreference(AddBroker.this, CONSTANT.EMPLOYEE_BRANCH);
        edt_branch.setText(str_branch);
        strCreatedBy = Preferences.getPreference(AddBroker.this, CONSTANT.EMPID);
        edtbrokerName = findViewById(R.id.edtbrokerName);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtContactNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtContactAltNo = findViewById(R.id.edtContactAltNo);
        edtContactAltNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_bank_account_no = findViewById(R.id.edt_bank_account_no);
        edt_bank_name = findViewById(R.id.edt_bank_name);
        edt_pan_no = findViewById(R.id.edt_pan_no);
        edt_name_on_pancard = findViewById(R.id.edt_name_on_pancard);
        edtBankIFSC = findViewById(R.id.edtBankIFSC);
        edtCreatedBy = findViewById(R.id.edtCreatedBy);
        edtCreatedOn = findViewById(R.id.edtCreatedOn);
        radio_active_id = findViewById(R.id.radiobtn_active_id);
        radio_inactive_id = findViewById(R.id.radiobtn_inactive_id);
    }

    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radiobtn_active_id:
                if (checked) {
                    strbroker_status = "Y";
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "Active", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radiobtn_inactive_id:
                if (checked){
                    strbroker_status = "N";
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "InActive", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;

            case R.id.mapbtn_toolbar:
                if(isValid()){
                    save();
                }
//                mandatoryCheck();
                break;
        }

    }

    private boolean isValid() {
        addbroker();

        if (strbroker_status == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.CHOOSE_THE_STATUS_TYPE,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (strbrokerName == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_BROKER_NAME,
                    Toast.LENGTH_SHORT).show();
            edtbrokerName.setHint(CONSTANT.MANDATORY);
            edtbrokerName.setHintTextColor(Color.RED);
            return false;
        }
        if (strContactNo == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CONTACT_NO,
                    Toast.LENGTH_SHORT).show();
            edtContactNo.setHint(CONSTANT.MANDATORY);
            edtContactNo.setHintTextColor(Color.RED);
            return false;
        }if(strContactNo!=null){
            if(strContactNo.length()<MAX_LENGTH){
                Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CORRECT_CONTACT_NO,
                        Toast.LENGTH_SHORT).show();
                edtContactNo.setHintTextColor(Color.RED);
                return false;
            }
        }
        if (strEmail == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_EMAIL,
                    Toast.LENGTH_SHORT).show();
            edtEmail.setHint(CONSTANT.MANDATORY);
            edtEmail.setHintTextColor(Color.RED);
        }if(strEmail!=null){
            if(!strEmail.contains("@")){
                Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_CORRECT_EMAIL,
                        Toast.LENGTH_SHORT).show();
                edtContactNo.setHintTextColor(Color.RED);
                return false;
            }
        }
        if (strAddress == null) {
            Toast.makeText(getApplicationContext(), CONSTANT.ENTER_THE_ADDRESS,
                    Toast.LENGTH_SHORT).show();
            edtAddress.setHint(CONSTANT.MANDATORY);
            edtAddress.setHintTextColor(Color.RED);
            return false;
        } else {

            return true;
        }
    }

    private void mandatoryCheck() {

    }

    private void save() {
        if(method.equals("add_broker")){
            add_function_api_call();
        }
        else if(function_method.equals("update_broker") || method.equals("update_broker")){
            update_function_api_call();
        }
        else if(broker_code_from_broker_list != null || !broker_code_from_broker_list.equals("")){
            update_function_api_call();
        }
    }

    private void add_function_api_call() {
        method = "add_broker";
        presenter = new AddBrokerPresenterImpl(this);
        presenter.init();
    }

    public void  fetch_broker(){
        method = "broker_detail_fetch";
        presenter = new BrokerFetchDetailsPresenterImpl(this);
        presenter.init();
    }

    private void update_function_api_call() {
        method = "update_broker";
        presenter = new BrokerUpdatePresenterImpl(this);
        presenter.init();
    }

    private void addbroker() {
        strbrokerName = edtbrokerName.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strContactAltNo = edtContactAltNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_bank_account_no = edt_bank_account_no.getText().toString().trim();
        str_bank_name = edt_bank_name.getText().toString().trim();
        str_pan_no = edt_pan_no.getText().toString().trim();
        str_name_on_pancard = edt_name_on_pancard.getText().toString().trim();
        strBankIFSC = edtBankIFSC.getText().toString().trim();
        strCreatedBy = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        if(strCreatedOn.equals("")){
            strCreatedOn = dateTime.currentDateTime();
        }
        strModificationDate = dateTime.currentDateTime();
    }

    @Override
    public String getCreated_by() {
        return strCreatedBy;
    }

    @Override
    public String getAddreass() {
        return strAddress;
    }

    @Override
    public String getPan_no() {
        return str_pan_no;
    }

    @Override
    public String getBank_Account_No() {
        return str_bank_account_no;
    }

    @Override
    public String getModified_On() {
        return strModificationDate;
    }

    @Override
    public String getCreated_On() {
        return strCreatedOn;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getAlternate_COntact() {
        return strContactAltNo;
    }

    @Override
    public String getBranch() {
        return str_branch;
    }

    @Override
    public String getBank_name() {
        return str_bank_name;
    }

    @Override
    public String getEmail_id() {
        return strEmail;
    }

    @Override
    public String getBank_Ifsc() {
        return strBankIFSC;
    }

    @Override
    public String getContact() {
        return strContactNo;
    }

    @Override
    public String getBroker_Code() {
        return broker_code_from_broker_list;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getBroker_name() {
        return strbrokerName;
    }

    @Override
    public String getName_on_PanCard() {
        return str_name_on_pancard;
    }

    @Override
    public String getActive_Status() {
        return strbroker_status;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.DB_NAME);
    }

    @Override
    public String getBroker_code() {
        return broker_code_from_broker_list;
    }

    @Override
    public String getModified_by() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
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

        if (object instanceof AddBrokerResponse) {
            AddBrokerResponse response = (AddBrokerResponse) object;
            Log.e("ResponseAdd", new Gson().toJson(response));
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ADD_BROKER_SUCCESSFULLY,
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.getStatus().equals(CONSTANT.ALREADY_EXISTS)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ALREADY_EXISTS,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), CONSTANT.UNSUCCESSFULLY,
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if(object instanceof UpdatBrokerResponse){
            UpdatBrokerResponse response = (UpdatBrokerResponse) object;
            Log.e("ResponseUpdate", new Gson().toJson(response));
            if(response != null){
                if(response.getStatus().equals(CONSTANT.TRUE)){
                    Toast.makeText(getApplicationContext(),CONSTANT.DATA_UPDATE,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),CONSTANT.UNSUCCESSFULLY,
                            Toast.LENGTH_SHORT).show();
                }

            }
        }

        else if (object instanceof FetchBrokerResponse) {
            FetchBrokerResponse response = (FetchBrokerResponse) object;
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    if (response.getBranch() == null) {
                        edt_branch.setText(branch_code);
                    } else {
                        edt_branch.setText(response.getBranch());
                    }
                    str_broker_code = response.getBrokerCode();
                    edtbrokerName.setText(response.getBrokerName());
                    edtContactNo.setText(response.getContact());
                    edtContactAltNo.setText(response.getAlternateContact());
                    edtEmail.setText(response.getEmailId());
                    edtAddress.setText(response.getAddress());
                    strbroker_status = response.getActiveStatus();
                    if (strbroker_status != null && !strbroker_status.equals(CONSTANT.BLANK)) {
                        if (strbroker_status.equals("Y")) {
                            radio_active_id.setChecked(true);
                        } else {
                            radio_inactive_id.setChecked(true);
                        }
                    }
                    strCreatedBy = response.getCreatedBy();
                    strCreatedOn = response.getCreatedOn();
                    edt_bank_account_no.setText(response.getBankAccountNo());
                    edt_bank_name.setText(response.getBankName());
                    edt_pan_no.setText(response.getPanNo());
                    edt_name_on_pancard.setText(response.getNameOnPancard());
                    edtBankIFSC.setText(response.getBankIfsc());
                }
            } else {
                Toast.makeText(getApplicationContext(), CONSTANT.DATA_NOT_FOUND,
                        Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
