package com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.VisitForm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor.VisitHistoryAdaptor;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.HistoryView;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.NewVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.UpdateVisitFormResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.VisitFormFetchResponse;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.VisitFormHistoryResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.NewVisitFormPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.UpdateVisitFormPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.VisitFormFetchPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.CRMPresenter.visitform.VisitFormHistoryPresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.DateTime;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.NewVisitFormView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.UpdateVisitFormView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormFetchView;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormHistoryView;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewVisitForm extends AppCompatActivity implements View.OnClickListener, NewVisitFormView,
        VisitFormHistoryView, VisitFormFetchView, UpdateVisitFormView {
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_customer_name, edt_visitdate, edtContactPerson, edtContactNo, edtEmail, edtAddress,
            edt_product_name, edtRemark, edt_other_employee_name, edt_visit_for, edt_scope,edt_visit_type,
            edt_status;
    String str_search, str_customer_name, str_visitdate, strContactPerson, strContactNo, strEmail, strAddress,
            str_product_name, strStatus, strRemark, str_other_employee_name, str_visit_for,
            str_visit_type, str_scope;
    TextView add_new_card, add_exist_card, txt_show_history;
    LinearLayout show_front_cardLayout, show_back_cardLayout, search_panalLayout;
    Spinner spinner_visit_for, spinner_visit_type, spinner_status;
    String saved = "Saved", notSaved = "Data Not Saved", dataNotFatched = "Data Not Found", method,
            input, NO_HISTORY_AVAILABLE = "No History Available";
    String businessType, compVisitID, methodF, s, strRefNo;
    Intent intent;
    Boolean FLAG = false;
    LinearLayout linearLayout;
    String companyUniqueIDF = null;
    String key;
    int MAX_LENGTH = 10;
    final String[] select_qualification = {"Select", "Air", "Cargo", "Road"};
    String[] scopelist;
    boolean[] checkedItems;
    ArrayList<Integer> scopeItems = new ArrayList<>();

    VisitHistoryAdaptor historyAdaptor;
    RecyclerView visitHistoryrecyclerview;
    List<HistoryView> visitHistoryModels;

    MainPresenter presenter;
    DateTime dateTime = new DateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit_form);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Customer Visit Form");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));
        ApiKey apiKey = new ApiKey();
        key = apiKey.saltStr();
        init();
        scopelist = getResources().getStringArray(R.array.scope_arrays);
        checkedItems = new boolean[scopelist.length];
        Intent intent = getIntent();
        companyUniqueIDF = intent.getStringExtra("ref_no");
        businessType = intent.getStringExtra("method");
        input = intent.getStringExtra("input");

        if (companyUniqueIDF != null && businessType.equals("customer_visit_search")) {
            customerVisitSearch();
        } else {
            businessType = "customer_visit_add";
            txt_show_history.setVisibility(View.GONE);
        }
        visitHistoryrecyclerview = findViewById(R.id.historyRecyclerView);
        visitHistoryrecyclerview.setHasFixedSize(true);
        visitHistoryrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        visitHistoryModels = new ArrayList<>();

    }

    private void customerVisitSearch() {
        Log.e("companyUniqueIDF",companyUniqueIDF);
        presenter = new VisitFormFetchPresenterImpl(this);
        presenter.init();
    }

    private void init() {
        linearLayout = findViewById(R.id.customerVisitLayout);
//        search_panalLayout = findViewById(R.id.layout_search);
        edt_customer_name = findViewById(R.id.edt_customer_name);
        edt_visitdate = findViewById(R.id.edtVisitDate);
        edt_visitdate.setOnClickListener(this);

        edtContactPerson = findViewById(R.id.edtContactPerson);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtContactNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_product_name = findViewById(R.id.edt_product_name);
        edtRemark = findViewById(R.id.edtRemark);
        edt_other_employee_name = findViewById(R.id.edt_other_employee_name);
        txt_show_history = findViewById(R.id.txt_show_history);
        txt_show_history.setOnClickListener(this);

        edt_scope = findViewById(R.id.edt_scope);
        edt_scope.setOnClickListener(this);

        spinner_visit_for = findViewById(R.id.spinner_visit_for);
        spinner_visit_type = findViewById(R.id.spinner_visit_type);
        edt_visit_type = findViewById(R.id.edt_visit_type);
        edt_visit_for = findViewById(R.id.edt_visit_for);
        edt_visit_for.setOnClickListener(this);
        edt_visit_type.setOnClickListener(this);

        spinner_status = findViewById(R.id.spinner_status);
        edt_status = findViewById(R.id.edt_status);
        edt_status.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                setEdtValues2Str();
                if (isValid()) {
                    save();
                }
                break;
            case R.id.show_more_click_link:
                break;
            case R.id.edtVisitDate:
                str_visitdate = dateTime.currentDateTime();
                edt_visitdate.setText(str_visitdate);
//                callDateDialogBox();
                break;
            case R.id.spinner_visit_for:
                spinner_visit_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_for = parent.getItemAtPosition(position).toString();
                        if (position > 0) {

                            spinner_visit_for.setBackgroundColor(Color.WHITE);
                            str_visit_for = spinner_visit_for.getSelectedItem().toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_visit_type:
                spinner_visit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_type = parent.getItemAtPosition(position).toString();
                        if (position > 0) {
                            spinner_visit_for.setBackgroundColor(Color.WHITE);
                            str_visit_type = spinner_visit_for.getSelectedItem().toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_status:
                spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        strStatus = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.txt_show_history:
                if (FLAG.equals(false)) {
                    customerHistory();
                    visitHistoryrecyclerview.setVisibility(View.VISIBLE);
                    txt_show_history.setText("Show History");
                    FLAG = true;
                } else if (FLAG.equals(true)) {
                    visitHistoryrecyclerview.setVisibility(View.GONE);
                    visitHistoryModels.clear();
                    txt_show_history.setText("Hide History");
                    linearLayout.invalidate();
                    FLAG = false;
                }
                break;
            case R.id.edt_scope:
                openDialogbox();
                break;

            case R.id.edt_visit_for:
                edt_visit_for.setVisibility(View.GONE);
                spinner_visit_for.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_visit_type:
                edt_visit_type.setVisibility(View.GONE);
                spinner_visit_type.setVisibility(View.VISIBLE);
                break;

            case R.id.edt_status:
                edt_status.setVisibility(View.GONE);
                spinner_status.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void openDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check Scope Types");
        builder.setMultiChoiceItems(scopelist, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    if (!scopeItems.contains(position)) {
                        scopeItems.add(position);
                    } else {
                        try {
                            scopeItems.remove(position);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < scopeItems.size(); i++) {
                    item = item + scopelist[scopeItems.get(i)];
                    if (i != scopeItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                edt_scope.setText("");
                edt_scope.setText(item);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    scopeItems.clear();
                    edt_scope.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setEdtValues2Str() {
        str_customer_name = edt_customer_name.getText().toString().trim();
//        str_visitdate = edt_visitdate.getText().toString().trim();
        try {
            Date now = new Date();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String s = df.format(now);
            str_visitdate = s.trim().replace("/", "-");
        } catch (NullPointerException e) {
            e.printStackTrace();
            str_visitdate = edt_visitdate.getText().toString().trim().replace("/", "-");
        }
        str_visit_for = spinner_visit_for.getSelectedItem().toString();
        str_visit_type = spinner_visit_type.getSelectedItem().toString();

        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        str_scope = edt_scope.getText().toString().trim();
        strStatus = spinner_status.getSelectedItem().toString();
        strRemark = edtRemark.getText().toString().trim();
        str_other_employee_name = edt_other_employee_name.getText().toString().trim();
    }

    private boolean isValid() {
        if (str_customer_name.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter customer name!", Toast.LENGTH_SHORT).show();
            edt_customer_name.setHint("Enter customer name!");
            edt_customer_name.setHintTextColor(Color.RED);
            return false;
        }
        if (str_visitdate.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter date!", Toast.LENGTH_SHORT).show();
            edt_visitdate.setHint("Enter date!");
            edt_visitdate.setHintTextColor(Color.RED);
            return false;
        }
        if (str_visit_for.equals("")) {
            Toast.makeText(getApplicationContext(), "select visit reason for!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (str_visit_type.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter visit type name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (strContactPerson.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter contact person name!", Toast.LENGTH_SHORT).show();
            edtContactPerson.setHint("Enter contact person name!");
            edtContactPerson.setHintTextColor(Color.RED);
            return false;
        }
        if (str_product_name.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter product name!", Toast.LENGTH_SHORT).show();
            edt_product_name.setHint("Enter product name!");
            edt_product_name.setHintTextColor(Color.RED);
            return false;
        }
        if (strStatus.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter status!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (str_scope.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter scope!", Toast.LENGTH_SHORT).show();
            edt_scope.setHint("Enter scope!");
            edt_scope.setHintTextColor(Color.RED);
            return false;
        } else {
            return true;
        }
    }

    private void customerHistory() {
        presenter = new VisitFormHistoryPresenterImpl(this);
        presenter.init();
    }

    private void save() {
        presenter = new NewVisitFormPresenterImpl(this);
        presenter.init();
    }

    @Override
    public String getRef_no() {
        return companyUniqueIDF;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getRef_No() {
        return companyUniqueIDF;
    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public String getRefNo() {
        return companyUniqueIDF;
    }

    @Override
    public String getAddress() {
        return strAddress;
    }

    @Override
    public String getVisitFor() {
        return str_visit_for;
    }

    @Override
    public String getScope() {
        return str_scope;
    }

    @Override
    public String getVisitDate() {
        return str_visitdate;
    }

    @Override
    public String getRemark() {
        return strRemark;
    }

    @Override
    public String getContactPerson() {
        return strContactPerson;
    }

    @Override
    public String getEmailid() {
        return strEmail;
    }

    @Override
    public String getContact() {
        return strContactNo;
    }

    @Override
    public String getVisitStatus() {
        return edt_status.getText().toString().trim();
    }

    @Override
    public String getStatus() {
        return strStatus;
    }

    @Override
    public String getVisitType() {
        return str_visit_type;
    }

    @Override
    public String getEmp_id() {
        return Preferences.getPreference(getApplicationContext(), CONSTANT.EMPID);
    }

    @Override
    public String getOtherEmpName() {
        return str_other_employee_name;
    }

    @Override
    public String getCustomer() {
        return str_customer_name;
    }

    @Override
    public String getProduct() {
        return str_product_name;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(NewVisitForm.this,CONSTANT.DB_NAME);
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof NewVisitFormResponse) {
            NewVisitFormResponse response = (NewVisitFormResponse) object;
            Log.e("ResponseNew", new Gson().toJson(response));

            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                Toast.makeText(getApplicationContext(), saved, Toast.LENGTH_SHORT).show();
                //activity finished for return back to CustomervisitListActivity after saving details...
                finish();
            } else {
                Toast.makeText(getApplicationContext(), notSaved, Toast.LENGTH_SHORT).show();
            }
        } else if (object instanceof VisitFormHistoryResponse) {
            VisitFormHistoryResponse response = (VisitFormHistoryResponse) object;
            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                String response_ref_no = response.getRefNo();
                visitHistoryModels.addAll(response.getHistoryView());
                historyAdaptor = new VisitHistoryAdaptor(NewVisitForm.this, visitHistoryModels, response_ref_no);
                visitHistoryrecyclerview.setAdapter(historyAdaptor);
            } else {
                Toast.makeText(getApplicationContext(), NO_HISTORY_AVAILABLE, Toast.LENGTH_SHORT).show();
            }
        }
        else if (object instanceof VisitFormFetchResponse) {
            VisitFormFetchResponse response = (VisitFormFetchResponse) object;
            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                str_visit_for = response.getVisitFor();
                spinner_visit_for.setPrompt(str_visit_for);
                spinner_visit_for.setVisibility(View.GONE);
                edt_visit_for.setVisibility(View.VISIBLE);
                edt_visit_for.setText(str_visit_for);

                str_visit_type = response.getVisitType();
                spinner_visit_type.setPrompt(str_visit_type);
                spinner_visit_type.setVisibility(View.GONE);
                edt_visit_type.setVisibility(View.VISIBLE);
                edt_visit_type.setText(str_visit_type);

                strStatus = response.getFollowupStatus();
                spinner_status.setPrompt(strStatus);
                spinner_status.setVisibility(View.GONE);
                edt_status.setVisibility(View.VISIBLE);
                edt_status.setText(strStatus);
                companyUniqueIDF = response.getRefNo();
                str_scope = response.getScope();
                edt_scope.setText(str_scope);
                edt_customer_name.setText(response.getCustomer());
                edt_visitdate.setText(response.getVisitDate());
                edtContactPerson.setText(response.getContactPerson());
                edtContactNo.setText(response.getContact());
                edtEmail.setText(response.getEmailId());
                edtAddress.setText(response.getAddress());
                edt_product_name.setText(response.getProduct());
                edtRemark.setText(response.getRemark());
                edt_other_employee_name.setText(response.getOtherEmployeeName());
                savebtn_toolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            getdata(compVisitID, businessType);
                            if (isValid()) {
                                modified();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), dataNotFatched, Toast.LENGTH_SHORT).show();
            }

        }
        else if(object instanceof UpdateVisitFormResponse){
            UpdateVisitFormResponse response = (UpdateVisitFormResponse) object;
            if (response != null && response.getStatus().equals(CONSTANT.TRUE)) {
                Toast.makeText(getApplicationContext(), CONSTANT.updated, Toast.LENGTH_SHORT).show();
                //activity finished for return back to CustomervisitListActivity after saving details...
                finish();
            } else {
                Toast.makeText(getApplicationContext(), CONSTANT.notUpdated, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void modified() {
        presenter = new UpdateVisitFormPresenterImpl(this);
        presenter.init();
    }

    private void getdata(String compVisitID, String businessType) {
        method = businessType;
        this.compVisitID = compVisitID;
        str_customer_name = edt_customer_name.getText().toString().trim();
        try {
            Date now = new Date();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String s = df.format(now);
            str_visitdate = s.trim().replace("/", "-");
        } catch (NullPointerException e) {
            e.printStackTrace();
            str_visitdate = edt_visitdate.getText().toString().trim().replace("/", "-");
        }
        str_visit_for = spinner_visit_for.getSelectedItem().toString();
        Log.e("str_visit_for==>", str_visit_for);

        str_visit_type = spinner_visit_type.getSelectedItem().toString();
        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        strStatus = spinner_status.getSelectedItem().toString();
        str_scope = edt_scope.getText().toString().trim();
        strRemark = edtRemark.getText().toString().trim();
        str_other_employee_name = edt_other_employee_name.getText().toString().trim();
    }

    @Override
    public void showFailure(String error) {

    }
}
