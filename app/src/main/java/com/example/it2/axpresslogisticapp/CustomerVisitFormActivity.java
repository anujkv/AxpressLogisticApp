package com.example.it2.axpresslogisticapp;

import android.preference.CheckBoxPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerVisitFormActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton backbtn_toolbar, savebtn_toolbar;
    EditText edt_search_panal, edt_customer_name, edtContactPerson, edtContactNo, edtEmail, edtAddress,
            edt_product_name, edtStatus, edtRemark, edt_other_employee_name;
    String str_search,str_customer_name, strContactPerson, strContactNo, strEmail, strAddress,
            str_product_name, strStatus, strRemark, str_other_employee_name,str_visit_for,
            str_visit_type,str_scope;
    TextView add_new_card, add_exist_card;
    LinearLayout show_front_cardLayout, show_back_cardLayout;
    Spinner spinner_visit_for, spinner_visit_type, spinner_scope,spinner_status;
    CheckBox check_new,check_followUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_form);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText("Customer Visit Form");
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        savebtn_toolbar = findViewById(R.id.mapbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setOnClickListener(this);
        savebtn_toolbar.setImageDrawable(getResources().getDrawable(R.drawable.icon_save));

        init();
        getdata();
    }

    private void init() {
        edt_search_panal = findViewById(R.id.edt_search_panal);
        edt_customer_name = findViewById(R.id.edt_customer_name);
        edtContactPerson = findViewById(R.id.edtContactPerson);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edt_product_name = findViewById(R.id.edt_product_name);
        edtRemark = findViewById(R.id.edtRemark);
        edt_other_employee_name = findViewById(R.id.edt_other_employee_name);

        add_new_card = findViewById(R.id.add_new_card);
        add_exist_card = findViewById(R.id.add_exist_card);

        show_front_cardLayout = findViewById(R.id.show_front_cardLayout);
        show_back_cardLayout = findViewById(R.id.show_back_cardLayout);
        show_front_cardLayout.setOnClickListener(this);
        show_back_cardLayout.setOnClickListener(this);

        spinner_visit_for = findViewById(R.id.spinner_visit_for);
        spinner_visit_type = findViewById(R.id.spinner_visit_type);
        spinner_scope = findViewById(R.id.spinner_scope);
        spinner_status = findViewById(R.id.spinner_status);

        check_new = findViewById(R.id.check_new);
        check_followUp = findViewById(R.id.check_followUp);
        check_new.setOnClickListener(this);
        check_followUp.setOnClickListener(this);
        }

    private void getdata() {
        str_customer_name = edt_customer_name.getText().toString().trim();
        strContactPerson = edtContactPerson.getText().toString().trim();
        strContactNo = edtContactNo.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strAddress = edtAddress.getText().toString().trim();
        str_product_name = edt_product_name.getText().toString().trim();
        strRemark = edtRemark.getText().toString().trim();
        str_other_employee_name = edt_other_employee_name.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.mapbtn_toolbar:
                save();
                break;
            case R.id.spinner_visit_for:
                spinner_visit_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_visit_for = parent.getItemAtPosition(position).toString();
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spinner_scope:
                spinner_scope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_scope = parent.getItemAtPosition(position).toString();
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
            case R.id.check_new:
                Toast.makeText(getApplicationContext(),"new",Toast.LENGTH_SHORT).show();
                if(check_new.isChecked()){
                    check_followUp.setChecked(false);
                }
                break;
            case R.id.check_followUp:
                Toast.makeText(getApplicationContext(),"follow",Toast.LENGTH_SHORT).show();
                if(check_followUp.isChecked()){
                    check_new.setChecked(false);
                    edt_search_panal.setFocusable(true);
                }
                break;

            case R.id.add_new_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.add_exist_card:
                show_front_cardLayout.setVisibility(View.VISIBLE);
                show_back_cardLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void save() {
        if(check_new.isChecked() || check_followUp.isChecked()){
            if(check_new.isChecked()){
                String businessType = "new";
                Toast.makeText(getApplicationContext(),
                        "Saved",Toast.LENGTH_SHORT).show();
            } else {
                String businessType = "follow";
                Toast.makeText(getApplicationContext(),
                        "Saved",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Kindly choose the business type new/follow",
                    Toast.LENGTH_SHORT).show();
        }



    }
}
