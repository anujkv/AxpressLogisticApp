package com.example.it2.axpresslogisticapp.acitvities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.example.it2.axpresslogisticapp.Utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.it2.axpresslogisticapp.Utilities.CONSTANT.URL;

public class EmpProfileActivity extends AppCompatActivity implements View.OnClickListener {
    String UPDATE_URL = URL + "HRMS/hr_approval";
    String EMPLOYEE_PROFILE_URL = URL + "HRMS/employee_info";
    Boolean FLAG = false;
    EditText edtName, edtEmpCode, edtContactNo, edtContactAltNo, edtDesignation, edtDept, edtBranch,
            edtBranchCode,
            edtEmail, edtFatherName, edtDOB, edtBOP, edtDOJ, edtQulification, edtAdharCard,
            edtAddress, edtPAN, edtUAN, edtESI, edtBankAccount, edtBankName, edtBankIFSC;
    TextView txtName, txtEmpId, txt_address_update_status, txt_bank_account_update_status,
            txt_bank_name_update_status,
            txt_bank_ifsc_update_status;
    String strName, strEmpCode, strContactNo, strContactAltNo, strDesignation, strDept, strBranch,
            strBranchCode, strEmail, strFatherName, strDOB, strBOP, strDOJ, strQulification, strAdharCard,
            strAddress, strPAN, strUAN, strESI, strBankAccount, strBankName, strBankIFSC;
    ImageButton edtAddressBtn, edtBankAccountBtn, edtBankNameBtn, edtIFSCBtn;
    Intent intent;
    String jsonString, title, method, str;
    JSONObject jObj;
    Dialog dialog;
    DialogFragment dialogFragment;
    LinearLayout contactAltLinearLayout;
    ImageButton backbtn_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView lable = findViewById(R.id.title_toolbar);
        lable.setText(CONSTANT.PROFILE);
        backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Set View with fields..
        setView();
        strEmpCode = Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID);
        employee_profileApi();

    }

    private void employee_profileApi() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String method = CONSTANT.EMPLOYEE_METHOD;
        final ApiKey apiKey = new ApiKey();
        final String apikey = apiKey.saltStr();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EMPLOYEE_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Log.e("Response : ", response);

                        try {
                            jObj = new JSONObject(response);
                            String status = jObj.optString(CONSTANT.STATUS);
                            String employee_id = jObj.optString(CONSTANT.EMPID);

                            if(status.equals(CONSTANT.TRUE) && employee_id.equals(strEmpCode)){
                                getValuesFromAPI();
                                setValuesInFields();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CONSTANT.METHOD,method);
                params.put(CONSTANT.KEY, apikey.trim());
                params.put(CONSTANT.EMPID, Preferences.getPreference(getApplicationContext(),CONSTANT.EMPID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setValuesInFields() {
        txtName.setText((strName.trim()));
        txtEmpId.setText(strEmpCode.trim());
        //put employee info value..
        edtName.setText(strName.trim());
        edtEmpCode.setText(strEmpCode.trim());
        edtContactNo.setText(strContactNo.trim());
//        edtContactAltNo.setText(strContactAltNo.trim());
        edtDesignation.setText(strDesignation.trim());
        edtDept.setText(strDept.trim());
        edtBranch.setText(strBranch.trim());
        edtBranchCode.setText(strBranchCode.trim());
        edtEmail.setText(strEmail.trim());
        //put employee ipersonal info..
        edtFatherName.setText(strFatherName.trim());
        edtDOB.setText(strDOB.trim());
        edtBOP.setText(strBOP.trim());
        edtDOJ.setText(strDOJ.trim());
        edtQulification.setText(strQulification.trim());
        edtAdharCard.setText(strAdharCard.trim());
        edtAddress.setText(strAddress.trim());
        edtPAN.setText(strPAN.trim());
        edtUAN.setText(strUAN.trim());
        edtESI.setText(strESI.trim());
        edtBankAccount.setText(strBankAccount.trim());
        edtBankName.setText(strBankName.trim());
        edtBankIFSC.setText(strBankIFSC.trim());
    }

    private void getValuesFromAPI() {
        strName = jObj.optString("Employee_Name");
        strContactNo = jObj.optString("Employee_Contact");
//        strContactAltNo = jObj.optString("Employee_Contact1");

        strDesignation = jObj.optString("Employee_Designation");
        strDept = jObj.optString("Employee_Department");
        strBranch = jObj.optString("Employee_Branch");
        strBranchCode = jObj.optString("Employee_Branch");
        strEmail = jObj.optString("Employee_Email");
        //get employee personal info..
        strFatherName = jObj.optString("Employee_Father_Name");
        strDOB = jObj.optString("Employee_DOB");
        strBOP = jObj.optString("Employee_birth_state");
        strDOJ = jObj.optString("Employee_DOJ");
        strQulification = jObj.optString("Employee_Qualification");
        strAdharCard = jObj.optString("Employee_AdhaarNo");
        strAddress = jObj.optString("Employee_Address");
        //get employee bank/imp info..
        strPAN = jObj.optString("Employee_PanNo");
        strUAN = jObj.optString("Employee_UanNO");
        strESI = jObj.optString("Employee_EsicNo");
        strBankAccount = jObj.optString("Employee_BanckAC");
        strBankName = jObj.optString("Employee_BankName");
        strBankIFSC = jObj.optString("Employee_IfscCode");
    }

    private void setView() {
        edtName = findViewById(R.id.edtname);
        edtEmpCode = findViewById(R.id.edtEmpCode);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtDesignation = findViewById(R.id.edtDesignation);
        edtDept = findViewById(R.id.edtDept);
        edtBranch = findViewById(R.id.edtBranch);
        edtBranchCode = findViewById(R.id.edtBranchCode);
        edtEmail = findViewById(R.id.edtEmail);
        // put employee personal id..
        edtFatherName = findViewById(R.id.edtFatherName);
        edtDOB = findViewById(R.id.edtDOB);
        edtBOP = findViewById(R.id.edtBOP);
        edtDOJ = findViewById(R.id.edtDOJ);
        edtQulification = findViewById(R.id.edtQulification);
        edtAdharCard = findViewById(R.id.edtAdharCard);
        edtAddress = findViewById(R.id.edtAddress);
        //put employee bank info..
        edtPAN = findViewById(R.id.edtPAN);
        edtUAN = findViewById(R.id.edtUAN);
        edtESI = findViewById(R.id.edtESI);
        edtBankAccount = findViewById(R.id.edtBankAccount);
        edtBankName = findViewById(R.id.edtBankName);
        edtBankIFSC = findViewById(R.id.edtBankIFSC);
        txtName = findViewById(R.id.user_nameId);
        txtEmpId = findViewById(R.id.user_empcodeId);


        //edit edittextfield on btn click..
        edtAddressBtn = findViewById(R.id.edtImageButtonAddress);
        edtBankAccountBtn = findViewById(R.id.edtImageButtonBankAccount);
        edtBankNameBtn = findViewById(R.id.edtImageButtonbankName);
        edtIFSCBtn = findViewById(R.id.edtImageButtonBankIFSC);
        edtAddressBtn.setOnClickListener(this);
        edtBankAccountBtn.setOnClickListener(this);
        edtBankNameBtn.setOnClickListener(this);
        edtIFSCBtn.setOnClickListener(this);

        //update status textview fields...
        txt_address_update_status = findViewById(R.id.txt_address_update_status);
        txt_bank_account_update_status = findViewById(R.id.txt_bank_account_update_status);
        txt_bank_name_update_status = findViewById(R.id.txt_bank_name_update_status);
        txt_bank_ifsc_update_status = findViewById(R.id.txt_bank_ifsc_update_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtImageButtonAddress:
                if (FLAG.equals(false)) {
                    String title = "Address";
                    method = "address";
                    showChangeProfileDialog(title, strAddress, method);
                }
                break;
            case R.id.edtImageButtonBankAccount:
                if (FLAG.equals(false)) {
                    title = "Bank Account";
                    method = "bank_account";
                    showChangeProfileDialog(title, strBankAccount, method);
                } else {
                    Toast.makeText(getApplicationContext(), "disable", Toast.LENGTH_SHORT).show();
                    edtBankAccount.setFocusable(false);
                    edtBankAccount.setFocusableInTouchMode(false);
                    edtBankAccount.setClickable(false);
                    FLAG = false;
                }
//
                break;
            case R.id.edtImageButtonbankName:
                String title = "Bank Name";
                method = "bank_name";
                showChangeProfileDialog(title, strBankName, method);
                break;
            case R.id.edtImageButtonBankIFSC:
                title = "Bank IFSC Code";
                method = "bank_ifsc";
                showChangeProfileDialog(title, strBankIFSC, method);
                break;
        }
    }

    public void showChangeProfileDialog(String title, final String field_detail, final String method) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_emp_profile, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (dialogView.findViewById(R.id.edtUpdateDialog));
        edt.setText(field_detail);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton(CONSTANT.DONE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                str = edt.getText().toString().trim();
                //do something with edt.getText().toString();
                Toast.makeText(getApplicationContext(), "Pending for approval : " + edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                postUpdateFunction(str, method);
                if (method.equals("address")) {
                    txt_address_update_status.setVisibility(View.VISIBLE);
                }else if(method.equals("bank_account")){
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }else if(method.equals("bank_name")){
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }else if(method.equals("bank_ifsc")){
                    txt_bank_account_update_status.setVisibility(View.VISIBLE);
                }
            }
        });
        dialogBuilder.setNegativeButton(CONSTANT.CANCEL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                edt.setText(field_detail);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void postUpdateFunction(final String str, final String method) {
        this.method = method;
        ApiKey apiKey = new ApiKey();
        this.str = str;
        final String apikey = apiKey.saltStr();
        final String arrlist[] = {"address", "bank_name", "bank_account", "bank_ifsc"};

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                        String status = object.optString(CONSTANT.RESPNOSE);
                    String apiKeyResponse = object.optString(CONSTANT.KEY);
                    String method = object.optString(CONSTANT.METHOD);

                    if (status.equals(CONSTANT.VERIFIED) && apiKeyResponse.equals(apikey)) {
//                        for (int i = 0; i < arrlist.length; i++) {
                            if (method.equals("address")) {
                                txt_address_update_status.setText("Verified");
                            }else if(method.equals("bank_account")){
                                txt_bank_account_update_status.setText("Verified");
                            }else if(method.equals("bank_name")){
                                txt_bank_account_update_status.setText("Verified");
                            }else if(method.equals("bank_ifsc")){
                                txt_bank_account_update_status.setText("Verified");
                            }
//                        }
                    } else if(status.equals(CONSTANT.NOT_VERIFIED) && apiKeyResponse.equals(apikey)){
                        txt_address_update_status.setText(CONSTANT.PENDING);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response======", "" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.RESPONSEERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.INTERNET_ERROR,
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(),
                            CONSTANT.TIMEOUT_ERROR,
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method",method);
                params.put("key",apikey);
                params.put("string",str);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
