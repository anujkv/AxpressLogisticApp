package com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddVehicle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response.BranchCodeName;
import com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response.BranchListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response.BranchModelListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response.Brnsearch;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.AddVehicleReq.AddVehicleResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.Broker;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.BrokerListResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.BranchList.BranchListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.AddVehicleReq.AddVehicleReqPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.OperationPresenter.BrokerList.BrokerListPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.BranchListView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.AddVehicleView.AddVehicleReqView;
import com.axpresslogistics.it2.axpresslogisticapp.view.operationView.Broker.BrokerListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVehicleReq extends AppCompatActivity implements View.OnClickListener, BrokerListView,
        BranchListView, AddVehicleReqView {
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_from_branch, edt_loading_point, edt_unloading_point, edt_actual_wt_of_goods,
            edt_vehicle_capacity, edt_broker_rate1, edt_broker_rate2, edt_broker_rate3, edt_advance1, edt_advance2,
            edt_advance3, edt_remark1, edt_remark2, edt_remark3, edtbrokerName1, edtbrokerName2, edtbrokerName3;
    Spinner spinner_to_branch, spinner_req_type, spinner_touching_point, spinner_goods_type, spinner_vehicle_type,
            spinner_broker_selection1, spinner_broker_selection2, spinner_broker_selection3;
    String str_vehicle_req_code, str_from_branch, str_to_branch, str_loading_point, str_unloading_point,
            str_actual_wt_of_goods, str_vehicle_capacity, str_req_type, str_touching_point,
            str_goods_type, str_vehicle_type, str_broker_name1, str_broker_name2, str_broker_name3;
    String method, emp_id, branch_code, broker_code1 = null, broker_code2 = null, broker_code3 = null;
    String str_contact_no1 = "", str_contact_no2 = "", str_contact_no3 = "";
    CardView broker1_rate_details, broker2_rate_details, broker3_rate_details;
    RecyclerView recyclerView;


    final ApiKey apiKey = new ApiKey();
    final String key = apiKey.saltStr();
    MainPresenter presenter;
    TextView lable;


    List<BranchCodeName> branchlist = new ArrayList<>();
    List<Broker> brokerlist = new ArrayList<>();
    List<String> list = new ArrayList<>();
    List<String> broker_list1 = new ArrayList<String>();
    List<String> broker_list2 = new ArrayList<String>();
    List<String> broker_list3 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_req);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.MARKET_VEHICLE_REQ);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        init();
        list.add(0, "Select");
        loadBranchList();
        loadBrokerList();

        str_from_branch = Preferences.getPreference(AddVehicleReq.this, CONSTANT.EMPLOYEE_BRANCH);
        edt_from_branch.setText(str_from_branch.trim());
        spinner_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                if (position > 0) {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                    Pattern mPattern = Pattern.compile("\\d+");
                    Matcher matcher = mPattern.matcher(str_vehicle_type);
                    if (matcher.find()) {
                        edt_vehicle_capacity.setText(matcher.group());
                        str_vehicle_capacity = edt_vehicle_capacity.getText().toString().trim();
                    }
                } else {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> brokerAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, broker_list1);
        brokerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_broker_selection1.setAdapter(brokerAdapter1);
        broker_list1.add(0, "select");
        ArrayAdapter<String> brokerAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, broker_list2);
        brokerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_broker_selection2.setAdapter(brokerAdapter2);
        broker_list2.add(0, "select");
        ArrayAdapter<String> brokerAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, broker_list3);
        brokerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_broker_selection3.setAdapter(brokerAdapter3);
        broker_list3.add(0, "select");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to_branch.setAdapter(dataAdapter);
        spinner_to_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_to_branch = spinner_to_branch.getSelectedItem().toString();
                if (position > 0) {
                    str_to_branch = parent.getItemAtPosition(position).toString();
                    branch_code = branch_code_matcher_regex(str_to_branch);
                    if(list.get(0)== "select"){
                        list.remove(0);
                    }


                } else {
                    str_vehicle_type = parent.getItemAtPosition(position).toString();
                    branch_code = branch_code_matcher_regex(str_vehicle_type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_broker_selection1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_broker_name1 = broker_name_matcher_regex(parent.getItemAtPosition(position).toString());
                broker_code1 = broker_code_matcher_regex(parent.getItemAtPosition(position).toString());
                if (broker_list1.get(0).equals("select")) {
                    broker_list1.remove(0);
                } else {
                    broker1_rate_details.setVisibility(View.VISIBLE);
                }

                if (broker_code1 == null || broker_code1.equals("")) {
                    broker_code1 = "";
                }
                if (str_broker_name1 == null || str_broker_name1.equals("")) {
                    str_broker_name1 = "";
                } else {
                    edtbrokerName1.setText(str_broker_name1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                str_broker_name1 = edtbrokerName1.getText().toString().trim();
                str_broker_name1 = broker_name_matcher_regex(str_broker_name1);
                edtbrokerName1.setText(str_broker_name1);
            }
        });

        spinner_broker_selection2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String broker = broker_name_matcher_regex(parent.getItemAtPosition(position).toString());
                broker_code2 = broker_code_matcher_regex(parent.getItemAtPosition(position).toString());
                edtbrokerName2.setText(broker);
                str_broker_name2 = broker;
                if (broker_list2.get(0).equals("select")) {
                    broker_list2.remove(0);
                } else {
                    broker2_rate_details.setVisibility(View.VISIBLE);
                }
                if (broker_code2 == null || broker_code2.equals("")) {
                    broker_code2 = "";
                }
                if (str_broker_name2 == null || str_broker_name2.equals("")) {
                    str_broker_name2 = "";
                } else {
                    edtbrokerName2.setText(str_broker_name2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                str_broker_name2 = edtbrokerName2.getText().toString().trim();
                str_broker_name2 = broker_name_matcher_regex(str_broker_name2);
                edtbrokerName2.setText(str_broker_name2);
            }
        });

        spinner_broker_selection3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String broker = broker_name_matcher_regex(parent.getItemAtPosition(position).toString());
                broker_code3 = broker_code_matcher_regex(parent.getItemAtPosition(position).toString());
                edtbrokerName3.setText(broker);
                str_broker_name3 = broker;
                if (broker_list3.get(0).equals("select")) {
                    broker_list3.remove(0);
                } else {
                    broker3_rate_details.setVisibility(View.VISIBLE);
                }
                if (broker_code3 == null || broker_code3.equals("")) {
                    broker_code3 = "";
                }
                if (str_broker_name3 == null || str_broker_name3.equals("")) {
                    str_broker_name3 = "";
                } else {
                    edtbrokerName3.setText(str_broker_name3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                str_broker_name3 = edtbrokerName3.getText().toString().trim();
                str_broker_name3 = broker_name_matcher_regex(str_broker_name3);
                edtbrokerName3.setText(str_broker_name3);
            }
        });
    }

    private String broker_code_matcher_regex(String s) {
        String name = null;
        Pattern mPattern = Pattern.compile("(\\d+)");
        Matcher matcher = mPattern.matcher(s);
        if (matcher.find()) {
            name = matcher.group().trim();
        }
        return name;
    }

    private String broker_name_matcher_regex(String s) {
        String name = null;
        name = s.split(" --")[0];
        return name;
    }

    private String branch_code_matcher_regex(String str_vehicle_type) {
        String string;
        string = str_vehicle_type;
        Pattern mPattern = Pattern.compile("(\\w+)\\s");
        Matcher matcher = mPattern.matcher(string);
        if (matcher.find()) {
            branch_code = matcher.group().trim().replace(" ", "");
        }
        return branch_code;
    }

    private void loadBrokerList() {
        presenter = new BrokerListPresenterImpl(this);
        presenter.init();

    }

    private void loadBranchList() {
        presenter = new BranchListPresenterImpl(this);
        presenter.init();
    }

    private void init() {
        edt_from_branch = findViewById(R.id.edt_from_branch);
        spinner_to_branch = findViewById(R.id.spinner_to_branch);
        edt_loading_point = findViewById(R.id.edt_loading_point);
        edt_loading_point.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_unloading_point = findViewById(R.id.edt_unloading_point);
        edt_unloading_point.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_actual_wt_of_goods = findViewById(R.id.edt_actual_wt_of_goods);
        spinner_req_type = findViewById(R.id.spinner_req_type);
        spinner_touching_point = findViewById(R.id.spinner_touching_point);
        spinner_goods_type = findViewById(R.id.spinner_goods_type);
        spinner_vehicle_type = findViewById(R.id.spinner_vehicle_type);
        edt_vehicle_capacity = findViewById(R.id.edt_vehicle_capacity);
        spinner_broker_selection1 = findViewById(R.id.spinner_broker_selection);
        spinner_broker_selection2 = findViewById(R.id.spinner_broker_selection2);
        spinner_broker_selection3 = findViewById(R.id.spinner_broker_selection3);

        broker1_rate_details = findViewById(R.id.broker1_rate_details);

        broker2_rate_details = findViewById(R.id.broker2_rate_details);

        broker3_rate_details = findViewById(R.id.broker3_rate_details);
        recyclerView = findViewById(R.id.recyclerview_brokerlist);

        edt_broker_rate1 = findViewById(R.id.edt_broker_rate1);
        edt_broker_rate2 = findViewById(R.id.edt_broker_rate2);
        edt_broker_rate3 = findViewById(R.id.edt_broker_rate3);

        edt_advance1 = findViewById(R.id.edt_advance1);
        edt_advance2 = findViewById(R.id.edt_advance2);
        edt_advance3 = findViewById(R.id.edt_advance3);

        edt_remark1 = findViewById(R.id.edt_remark1);
        edt_remark2 = findViewById(R.id.edt_remark2);
        edt_remark3 = findViewById(R.id.edt_remark3);

        edtbrokerName1 = findViewById(R.id.edtbrokerName1);
        edtbrokerName2 = findViewById(R.id.edtbrokerName2);
        edtbrokerName3 = findViewById(R.id.edtbrokerName3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                getdata();
                mandatoryCheck();
                break;
        }
    }

    private void mandatoryCheck() {
        Boolean condition_code = false, condition_rate = false;
        try {
            if (!broker_code1.equals("") && !broker_code2.equals("")) {
                condition_code = true;
            } else {
                Toast.makeText(getApplicationContext(), "Kindly select two brokers from list",
                        Toast.LENGTH_SHORT).show();
            }
            String str_rate1, str_rate2, str_rate3;
            str_rate1 = edt_broker_rate1.getText().toString().trim();
            str_rate2 = edt_broker_rate2.getText().toString().trim();
            str_rate3 = edt_broker_rate3.getText().toString().trim();
            if (str_rate1.equals("") && str_rate2.equals("") && str_rate3.equals("")) {
                Toast.makeText(getApplicationContext(), "Kindly fill the rate of brokers",
                        Toast.LENGTH_SHORT).show();
            } else if (str_rate2.equals("") && str_rate3.equals("")) {
                Toast.makeText(getApplicationContext(), "Kindly fill the rate of brokers",
                        Toast.LENGTH_SHORT).show();
            } else if (str_rate1.equals("") && str_rate3.equals("")) {
                Toast.makeText(getApplicationContext(), "Kindly fill the rate of brokers",
                        Toast.LENGTH_SHORT).show();
            } else {
                condition_rate = true;
            }

            if (condition_code.equals(true) && condition_rate.equals(true)) {
                //   save();
                add_Vehical();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void add_Vehical() {
        presenter = new AddVehicleReqPresenterImpl(this);
        presenter.init();
    }

    private void getdata() {
        str_loading_point = edt_loading_point.getText().toString().trim();
        str_unloading_point = edt_unloading_point.getText().toString().trim();
        str_actual_wt_of_goods = edt_actual_wt_of_goods.getText().toString().trim();
        if (str_loading_point.equals("") && str_unloading_point.equals("") && str_actual_wt_of_goods.equals("")) {
            Toast.makeText(getApplicationContext(), "Kindly fill the field",
                    Toast.LENGTH_SHORT).show();
        }

        str_req_type = spinner_req_type.getSelectedItem().toString().trim();
        if (str_req_type.toLowerCase().equals("select")) {
            Toast.makeText(getApplicationContext(), "Kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }
        str_touching_point = spinner_touching_point.getSelectedItem().toString().trim();
        if (str_touching_point.toLowerCase().equals("select")) {
            Toast.makeText(getApplicationContext(), "Kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }
        str_goods_type = spinner_goods_type.getSelectedItem().toString().trim();
        if (str_goods_type.toLowerCase().equals("select")) {
//            //TODO if user not select any list of item,do something
            Toast.makeText(getApplicationContext(), "kindly select the field",
                    Toast.LENGTH_SHORT).show();
        }
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
    public String getEmp_id() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
    }

    @Override
    public String getunloading_point() {
        return str_unloading_point;
    }

    @Override
    public String getbroker_1_rate() {
        return edt_broker_rate1.getText().toString().trim();
    }

    @Override
    public String getto_branch() {
        return branch_code;
    }

    @Override
    public String gettouching_point() {
        return str_touching_point;
    }

    @Override
    public String getbroker_name2() {
        return edtbrokerName2.getText().toString().trim();
    }

    @Override
    public String getbroker3_contact_no() {
        return str_contact_no3;
    }

    @Override
    public String getVehicle_Type() {
        return str_vehicle_type;
    }

    @Override
    public String getFrom_Branch() {
        return Preferences.getPreference(getApplicationContext(),CONSTANT.EMPLOYEE_BRANCH);
    }

    @Override
    public String getReq_Type() {
        return str_req_type;
    }

    @Override
    public String getBroker_code3() {
        return broker_code3;
    }

    @Override
    public String getbroker_2_advance() {

        if(edt_advance2.getText().toString().trim().equals("")){
            return "0";
        }
        else{
            return edt_advance2.getText().toString().trim();
        }
    }

    @Override
    public String getbroker_1_advance() {
        if(edt_advance1.getText().toString().trim().equals("")){
            return "0";
        }
        else{
            return edt_advance1.getText().toString().trim();
        }
    }

    @Override
    public String getbroker_3_rate() {
        return edt_broker_rate3.getText().toString().trim();
    }

    @Override
    public String getbroker_code2() {
        return broker_code2;
    }

    @Override
    public String getbroker_name3() {
        return edtbrokerName3.getText().toString().trim();
    }

    @Override
    public String getgood_type() {
        return str_goods_type;
    }

    @Override
    public String getbroker1_contact_no() {
        return str_contact_no1;
    }

    @Override
    public String getvehicle_capacity() {
        return str_vehicle_capacity;
    }

    @Override
    public String getbroker_3_advance() {
        return edt_advance3.getText().toString().trim();
    }

    @Override
    public String getbroker_2_remark() {
        return edt_remark2.getText().toString().trim();
    }

    @Override
    public String getactual_wt_of_goods() {
        return str_actual_wt_of_goods;
    }

    @Override
    public String getloading_point() {
        return str_loading_point;
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
    public String getbroker_2_rate() {
        return edt_broker_rate2.getText().toString().trim();
    }

    @Override
    public String getbroker_name1() {
        return edtbrokerName1.getText().toString().trim();
    }

    @Override
    public String getbroker_code1() {
        return broker_code1;
    }

    @Override
    public String getbroker2_contact_no() {
        return str_contact_no2;
    }

    @Override
    public String getbroker_1_remark() {
        return edt_remark1.getText().toString().trim();
    }

    @Override
    public String getbroker_3_remark() {
        return edt_remark3.getText().toString().trim();
    }

    @Override
    public String getmarket_vehicle_entry_status() {
        return "pending";
    }

    @Override
    public void showLoadingLayout() {
//        progressDialog = new ProgressDialog(this){
//            @Override
//            public void onBackPressed() {
//                progressDialog.dismiss();
//            }
//        };
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();


    }

    @Override
    public void hideLoadingLayout() {
//        progressDialog.dismiss();
    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof BrokerListResponse) {
            BrokerListResponse respose = (BrokerListResponse) object;
            if (respose != null) {
                if (respose.getStatus().equals(CONSTANT.TRUE)) {
                    brokerlist.addAll(respose.getBroker());
                }
                for (int i = 0; i < brokerlist.size(); i++) {
                    String branch_name = brokerlist.get(i).getBrokerName();
                    String branch_code = brokerlist.get(i).getBrokerCode();

                    if (!branch_name.equals("")) {
                        branch_name = branch_name.split(" --")[0];
                    }
                    broker_list1.add(branch_name + " --" + branch_code);
                    broker_list2.add(branch_name + " --" + branch_code);
                    broker_list3.add(branch_name + " --" + branch_code);
                }
                spinner_broker_selection1.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this,
                        android.R.layout.simple_spinner_dropdown_item, broker_list1));
                spinner_broker_selection2.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this,
                        android.R.layout.simple_spinner_dropdown_item, broker_list2));
                spinner_broker_selection3.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this,
                        android.R.layout.simple_spinner_dropdown_item, broker_list3));
            }
        }
        else if (object instanceof BranchListResponse) {
            BranchListResponse response = (BranchListResponse) object;
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    branchlist.addAll(response.getBrnsearch());
                }
                for (int i = 0; i < branchlist.size(); i++) {

                    String branch = branchlist.get(i).getBranch_name();
                    list.add(branch);
                }
                spinner_to_branch.setAdapter(new ArrayAdapter<String>(AddVehicleReq.this,
                        android.R.layout.simple_spinner_dropdown_item, list));
            }
        } else if (object instanceof AddVehicleResponse) {
            AddVehicleResponse response = (AddVehicleResponse) object;
            if (response != null) {
                if (response.getStatus().equals(CONSTANT.TRUE)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ADD_VEHICLE_SUCCESSFULLY,
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.getStatus().equals(CONSTANT.already_exist)) {
                    Toast.makeText(getApplicationContext(), CONSTANT.ALREADY_EXISTS,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), CONSTANT.UNSUCCESSFULLY,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void showFailure(String error) {
        Toast.makeText(getApplicationContext(),CONSTANT.server_not_reachable,Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();

    }
}
