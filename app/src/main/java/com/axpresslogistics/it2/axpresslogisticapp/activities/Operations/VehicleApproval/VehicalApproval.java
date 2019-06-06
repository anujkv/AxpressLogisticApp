package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleApproval;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.BrokerAdaptor.BrokerApprovalAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.ApprovedVehicle.VehicleApprovedListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchApprovalStatusModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchApprovalStatusModelList;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchApprovalStatusModelListOfList;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval.FetchApprovalStatusPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.VehicleApproval.VehicleApprovedListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.LoadingLayout;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.FetchApprovalStatusView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.VehicleApprovalListView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView.VehicleApprovedListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicalApproval extends AppCompatActivity implements View.OnClickListener, FetchApprovalStatusView,
        VehicleApprovedListView, VehicleApprovalListView {

    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    ImageButton backbtn_toolbar, savebtn_toolbar;
    CardView broker1_rate_details, broker2_rate_details, broker3_rate_details;
    EditText edt_vehicle_req_code, edt_vehicle_req_date, edt_from_branch, edt_to_branch, edt_loading_point,
            edt_unloading_point, edtspinner_req_type, edtspinner_goods_type, edt_actual_wt_of_goods,
            edtspinner_vehicle_type,
            spinner_broker_selection1;
    Button btn_broker_selection1;
    String method, str_empid, methodAPI, vehicle_req_code, broker_status, BROKER_RATE = "0", BROKER_NAME = "",
            BROKER_CODE, SELECTION_STATUS, BROKER_REMARK = "";

    String str_vehicle_req_code, str_request_date, str_from_branch, str_to_branch, str_loading_point,
            str_unloading_point, str_req_type, str_goods_type, str_act_wt_of_goods,
            str_vehicle_type, str_approved_status;
    int no_of_brokers;
    BrokerApprovalAdaptor fetchapprovalAdaptor;
//    List<FetchApprovalStatusModel> models = new ArrayList<>();
    List<FetchApprovalStatusModelList> modelList = new ArrayList<>();
    List<FetchApprovalStatusModelListOfList> modelListlist = new ArrayList<>();

    MainPresenter presenter;

    List list;
    RecyclerView recyclerView;
    final ApiKey apiKey = new ApiKey();
    final String key = apiKey.saltStr();
//    BrokerApprovalAdaptor brokerApprovalAdaptor;
//    ProgressDialog progressDialog;
    LoadingLayout loadingLayout = new LoadingLayout();
    String db_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_approval);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.VEHICLE_APPROVAL);
        ButterKnife.bind(this);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        init();

        recyclerView = findViewById(R.id.broker_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        str_from_branch = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPLOYEE_BRANCH);
        str_empid = Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
        vehicle_req_code = getIntent().getStringExtra("vehicle_request_code");

        try {
            if (vehicle_req_code != null) {
                methodAPI = getIntent().getStringExtra("method");
                fetch_approval_details();
                set_data_in_fields();
//                edt_from_branch.setText(str_from_branch);
                Log.e("from_branch<", str_from_branch);

//            set_data_in_fields();
            } else {
                Log.e("from_branch>", str_from_branch);
//                edt_from_branch.setText(str_from_branch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String APPROVED_BROKER_CODE = getIntent().getStringExtra("broker_code");
        if (APPROVED_BROKER_CODE != null) {
            BROKER_RATE = getIntent().getStringExtra("broker_rate");
            BROKER_CODE = getIntent().getStringExtra("broker_code");
            BROKER_NAME = getIntent().getStringExtra("broker_name");
            SELECTION_STATUS = getIntent().getStringExtra("approved_status");
            BROKER_REMARK = getIntent().getStringExtra("broker_remark");
        }
    }

    private void init() {
        edt_to_branch = findViewById(R.id.edt_to_branch);
        edt_loading_point = findViewById(R.id.edt_loading_point);
        edt_unloading_point = findViewById(R.id.edt_unloading_point);
        edtspinner_req_type = findViewById(R.id.spinner_req_type);
        edtspinner_goods_type = findViewById(R.id.spinner_goods_type);
        edt_actual_wt_of_goods = findViewById(R.id.edt_actual_wt_of_goods);
        edtspinner_vehicle_type = findViewById(R.id.spinner_vehicle_type);

        broker1_rate_details = findViewById(R.id.broker1_rate_details);
        broker2_rate_details = findViewById(R.id.broker2_rate_details);
        broker3_rate_details = findViewById(R.id.broker3_rate_details);

        edt_vehicle_req_code = findViewById(R.id.edt_vehicle_req_code);
        edt_vehicle_req_date = findViewById(R.id.edt_vehicle_req_date);
        edt_from_branch = findViewById(R.id.edt_from_branch);

        btn_broker_selection1 = findViewById(R.id.btn_broker_selection);

        spinner_broker_selection1 = findViewById(R.id.spinner_broker_selection);
    }

    private void set_data_in_fields() {
        edt_vehicle_req_code.setText(str_vehicle_req_code);
        edt_vehicle_req_date.setText(str_request_date);
        edt_from_branch.setText(str_from_branch);
        edt_to_branch.setText(str_to_branch);
        edt_loading_point.setText(str_loading_point);
        edt_unloading_point.setText(str_unloading_point);
        edtspinner_req_type.setText(str_req_type);
        edtspinner_goods_type.setText(str_goods_type);
        edt_actual_wt_of_goods.setText(str_act_wt_of_goods);
        edtspinner_vehicle_type.setText(str_vehicle_type);
    }

    private void fetch_approval_details() {
        presenter = new FetchApprovalStatusPresenterImpl(this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                getList();
                approved_Status();
                //approved();
                break;
        }
    }

    private void approved_Status() {
        presenter = new VehicleApprovedListPresenterImpl(this);
        presenter.init();
    }

    private void getList() {
        list = fetchapprovalAdaptor.clicked_details();
        if (!list.isEmpty()) {
            BROKER_CODE = (String) list.get(0);
            BROKER_NAME = (String) list.get(1);
            BROKER_RATE = (String) list.get(2);
            SELECTION_STATUS = (String) list.get(3);
            BROKER_REMARK = (String) list.get(4);
            Toast.makeText(getApplicationContext(), BROKER_NAME, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getActual_wt_of_goods() {
        return str_act_wt_of_goods;
    }

    @Override
    public String getFromBranch() {
        return str_from_branch;
    }

    @Override
    public String getRequestCode() {
        return str_vehicle_req_code;
    }

    @Override
    public String getLoading_point() {
        return str_loading_point.toUpperCase();
    }

    @Override
    public String getapproved_status() {
        return SELECTION_STATUS;
    }

    @Override
    public String getbroker_remark() {
        if (BROKER_REMARK.trim().equals("") || BROKER_REMARK == null) {
            BROKER_REMARK = "SPACE";
        }
        return BROKER_REMARK;
    }

    @Override
    public String getbroker_code() {
        return BROKER_CODE;
    }

    @Override
    public String getrequirement_type() {
        return str_req_type;
    }

    @Override
    public String getunloading_point() {
        return str_unloading_point.toUpperCase();
    }

    @Override
    public String getToBranch() {
        return str_to_branch;
    }

    @Override
    public String getbroker_name() {
        return BROKER_NAME;
    }

    @Override
    public String getbroker_rate() {
        return BROKER_RATE;
    }


    @Override
    public String getVehical_request_code() {
        return vehicle_req_code;
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
    public String getBranchCode() {
        return str_from_branch;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(VehicalApproval.this, CONSTANT.DB_NAME);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(this) {
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//            }
//        };
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        loadingLayout.showLoadingLayout(this);

    }

    @Override
    public void hideLoadingLayout() {
        loadingLayout.hideLoadingLayout();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof FetchApprovalStatusModel) {
            Log.e("ResponseFetch", new Gson().toJson(object));
            FetchApprovalStatusModel fetchApprovalStatusModel = (FetchApprovalStatusModel) object;
            Log.e("ResponseFetchInside", new Gson().toJson(fetchApprovalStatusModel));
            if (fetchApprovalStatusModel.getStatus().equals(CONSTANT.TRUE)) {
                modelList.addAll(fetchApprovalStatusModel.getVpa());
                broker_status = fetchApprovalStatusModel.getBrokerStatus();
                db_name = fetchApprovalStatusModel.getDb_name();
                if (broker_status.equals(CONSTANT.B)) {
                    str_approved_status = "BApproved";
                } else if (broker_status.equals(CONSTANT.L)) {
                    str_approved_status = "Approved";
                }
                if (fetchApprovalStatusModel.getStatus().equals(CONSTANT.TRUE)) {
                    for (int i = 0; i < modelList.size(); i++) {
                        Log.e("str_vehicle_req_code", modelList.get(i).getVehicleRequestCode());
                        str_vehicle_req_code = modelList.get(i).getVehicleRequestCode();
                        edt_vehicle_req_code.setText(modelList.get(i).getVehicleRequestCode());
                        str_request_date = modelList.get(i).getRequestDate();
                        edt_vehicle_req_date.setText(modelList.get(i).getRequestDate());
                        str_from_branch = modelList.get(i).getFromBranch();
                        edt_from_branch.setText(modelList.get(i).getFromBranch());
                        str_to_branch = modelList.get(i).getToBranch();
                        edt_to_branch.setText(modelList.get(i).getToBranch());
                        str_loading_point = modelList.get(i).getLoadingPoint();
                        edt_loading_point.setText(modelList.get(i).getLoadingPoint());
                        str_unloading_point = modelList.get(i).getUnloadingPoint();
                        edt_unloading_point.setText(modelList.get(i).getUnloadingPoint());
                        str_req_type = modelList.get(i).getRequirementType();

                        if (str_req_type.equals("LOCAL")) {
                            str_req_type = "Local";
                        } else if (str_req_type.equals("LONG")) {
                            str_req_type = "Long Route";
                        }

                        edtspinner_req_type.setText(str_req_type);
                        str_goods_type = modelList.get(i).getGoodsType();
                        edtspinner_goods_type.setText(modelList.get(i).getGoodsType());
                        str_act_wt_of_goods = modelList.get(i).getActualWtOfGoods();
                        edt_actual_wt_of_goods.setText(modelList.get(i).getActualWtOfGoods());
                        str_vehicle_type = modelList.get(i).getVehicleType();
                        edtspinner_vehicle_type.setText(modelList.get(i).getVehicleType());
                        // object.getString(CONSTANT.BROKER_STATUS));
                        broker_status = fetchApprovalStatusModel.getBrokerStatus();
                        modelListlist.addAll(modelList.get(i).getBselectiondetail());

                        String broker_status = modelListlist.get(i).getLoggedin_member();
                        no_of_brokers = modelListlist.size();
                        fetchapprovalAdaptor = new BrokerApprovalAdaptor(getApplicationContext(), modelListlist, broker_status);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(fetchapprovalAdaptor);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry data couldn't load!", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (object instanceof VehicleApprovedListResponse) {
            VehicleApprovedListResponse response = (VehicleApprovedListResponse) object;

            if (response.getStatus().equals(CONSTANT.TRUE)) {
                Toast.makeText(getApplicationContext(), "Approval send,Successful!"
                        , Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Approval not send,Unsuccessful"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable, Toast.LENGTH_SHORT).show();
        loadingLayout.hideLoadingLayout();

    }
}
